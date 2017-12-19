package com.mindata.ecserver.main.manager.es;

import com.mindata.ecserver.global.bean.SimplePage;
import com.mindata.ecserver.global.constant.Constant;
import com.mindata.ecserver.main.manager.CodeSizeManager;
import com.mindata.ecserver.main.manager.EcVocationCodeManager;
import com.mindata.ecserver.main.model.es.EsContact;
import com.mindata.ecserver.main.repository.es.EsContactRepository;
import com.mindata.ecserver.main.requestbody.ContactRequestBody;
import com.mindata.ecserver.main.service.base.BaseService;
import com.mindata.ecserver.main.vo.ContactVO;
import com.mindata.ecserver.util.CommonUtil;
import com.xiaoleilu.hutool.util.CollectionUtil;
import com.xiaoleilu.hutool.util.StrUtil;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * elasticsearch的管理类
 *
 * @author wuweifeng wrote on 2017/11/9.
 */
@Service
public class EsContactManager extends BaseService {
    @Resource
    private ElasticsearchTemplate elasticsearchTemplate;
    @Resource
    private EcVocationCodeManager ecVocationCodeManager;
    @Resource
    private EsContactRepository esContactRepository;
    @Resource
    private CodeSizeManager codeSizeManager;

    private Logger logger = LoggerFactory.getLogger(getClass().getName());

    @PostConstruct
    public void initES() {
        if (!elasticsearchTemplate.indexExists(Constant.ES_INDEX_NAME)) {
            logger.info("ES index不存在，开始创建index");
            elasticsearchTemplate.createIndex(Constant.ES_INDEX_NAME);
            logger.info("创建index完毕");
        }
    }

    public EsContact findById(Long id) {
        return esContactRepository.findOne(id);
    }

    /**
     * 模糊查询某列
     *
     * @param contactRequestBody
     *         body
     * @return 结果
     */
    public SimplePage<ContactVO> findByRequestBody(ContactRequestBody contactRequestBody) {
        BoolQueryBuilder boolQuery = boolQuery();

        //全文检索
        if (!StrUtil.isEmpty(contactRequestBody.getExtra())) {
            boolQuery.must(queryStringQuery(contactRequestBody.getExtra()));
        }
        boolQuery.must(matchQuery("state", 0));
        //公司名
        if (!StrUtil.isEmpty(contactRequestBody.getCompanyName())) {
            boolQuery.must(matchQuery("company", contactRequestBody.getCompanyName()));
        }
        //详细地址
        if (!StrUtil.isEmpty(contactRequestBody.getAddress())) {
            boolQuery.must(matchQuery("address", contactRequestBody.getAddress()));
        }
        //职位名称
        if (!StrUtil.isEmpty(contactRequestBody.getJobName())) {
            boolQuery.must(matchQuery("jobName", contactRequestBody.getJobName()));
        }
        //有手机号
        if (contactRequestBody.getHasMobile() != null && contactRequestBody.getHasMobile()) {
            boolQuery.must(rangeQuery("mobile").gt(10000));
        }
        //招聘信息
        if (contactRequestBody.getNeedSale() != null && contactRequestBody.getNeedSale()) {
            boolQuery.must(matchQuery("needSale", 1));
        }
        //企业简介
        if (!StrUtil.isEmpty(contactRequestBody.getComintro())) {
            boolQuery.must(matchQuery("comintro", contactRequestBody.getComintro()));
        }
        //来源
        if (!CollectionUtil.isEmpty(contactRequestBody.getWebsiteIds())) {
            boolQuery.filter(termsQuery("websiteId", contactRequestBody.getWebsiteIds()));
        }
        //规模
        if (!CollectionUtil.isEmpty(contactRequestBody.getMemberSizeTags())) {
            boolQuery.filter(termsQuery("memberSizeTag", contactRequestBody.getMemberSizeTags()));
        }
        //行业
        if (!CollectionUtil.isEmpty(contactRequestBody.getVocations())) {
            boolQuery.filter(termsQuery("vocation", contactRequestBody.getVocations()));
        }
        //区域
        if (!CollectionUtil.isEmpty(contactRequestBody.getProvinces())) {
            boolQuery.filter(termsQuery("province", contactRequestBody.getProvinces()));
            List<String> cities = contactRequestBody.getCities();

            //如果勾了多个市，则用in
            if (!CollectionUtil.isEmpty(cities)) {
                boolQuery.filter(termsQuery("city", cities));
            }
        }

        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder().withQuery(boolQuery);
        long totalCount = elasticsearchTemplate.count(builder.build(), EsContact.class);

        int page = Constant.PAGE_NUM;
        int size = Constant.PAGE_SIZE;
        if (contactRequestBody.getPage() != null) {
            page = contactRequestBody.getPage();
        }
        if (contactRequestBody.getSize() != null) {
            size = contactRequestBody.getSize();
        }
        Pageable pageable = new PageRequest(page, size);

        SearchQuery searchQuery = builder.withPageable(pageable).build();
        List<EsContact> esContacts = elasticsearchTemplate.queryForList(searchQuery, EsContact.class);
        List<ContactVO> contactVOS = parse(esContacts);

        return new SimplePage<>((int) totalCount / size + 1, totalCount, contactVOS);
    }

    @SuppressWarnings("Duplicates")
    private List<ContactVO> parse(List<EsContact> esContacts) {
        List<ContactVO> contactVOS = new ArrayList<>();
        for (EsContact esContact : esContacts) {
            ContactVO vo = new ContactVO();
            vo.setCompany(esContact.getCompany());
            vo.setId(esContact.getId());
            if (StrUtil.isEmpty(esContact.getMobile())) {
                vo.setMobile(esContact.getPhone());
            } else {
                vo.setMobile(esContact.getMobile());
            }
            vo.setName(esContact.getName());
            vo.setAddress(esContact.getAddress());
            vo.setCompanyScore(CommonUtil.cutDouble2(esContact.getCompanyScore()));
            vo.setVocation(ecVocationCodeManager.findNameByCode(esContact.getVocation()));
            vo.setProvince(ecCodeAreaManager.findById(esContact.getProvince() + ""));
            vo.setMemberSizeTag(codeSizeManager.findNameById(esContact.getMemberSizeTag()));
            contactVOS.add(vo);
        }
        return contactVOS;
    }
}
