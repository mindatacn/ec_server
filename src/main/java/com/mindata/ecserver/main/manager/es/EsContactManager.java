package com.mindata.ecserver.main.manager.es;

import com.mindata.ecserver.global.bean.SimplePage;
import com.mindata.ecserver.global.constant.Constant;
import com.mindata.ecserver.main.manager.EcVocationCodeManager;
import com.mindata.ecserver.main.model.es.EsContact;
import com.mindata.ecserver.main.repository.es.EsContactRepository;
import com.mindata.ecserver.main.requestbody.ContactRequestBody;
import com.mindata.ecserver.main.service.base.BaseService;
import com.mindata.ecserver.main.vo.ContactVO;
import com.xiaoleilu.hutool.util.CollectionUtil;
import com.xiaoleilu.hutool.util.StrUtil;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.matchQuery;

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

    /**
     * 模糊查询某列
     *
     * @param contactRequestBody
     *         body
     * @return 结果
     */
    public SimplePage<ContactVO> findByRequestBody(ContactRequestBody contactRequestBody) {

        EsContact esContact1 = esContactRepository.findOne(223262L);
        System.out.println(esContact1.getCompany());

        BoolQueryBuilder boolQuery = boolQuery();

        //boolQuery.must(matchQuery("state", 0));
        //公司名                                                      、
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
        //企业简介
        if (!StrUtil.isEmpty(contactRequestBody.getComintro())) {
            boolQuery.must(matchQuery("comintro", contactRequestBody.getComintro()));
        }
        //来源
        if (!CollectionUtil.isEmpty(contactRequestBody.getWebsiteIds())) {
            boolQuery.must(matchQuery("websiteId", contactRequestBody.getWebsiteIds()));
        }
        //规模
        if (!CollectionUtil.isEmpty(contactRequestBody.getMemberSizeTags())) {
            boolQuery.must(matchQuery("memberSizeTag", contactRequestBody.getMemberSizeTags()));
        }
        //行业
        if (!CollectionUtil.isEmpty(contactRequestBody.getVocations())) {
            boolQuery.must(matchQuery("vocation", getVocations(contactRequestBody.getVocations())));
        }
        //区域
        if (!CollectionUtil.isEmpty(contactRequestBody.getProvinces())) {
            boolQuery.must(matchQuery("province", contactRequestBody.getProvinces()));
            List<String> cities = contactRequestBody.getCities();

            //如果勾了多个市，则用in
            if (!CollectionUtil.isEmpty(cities)) {
                cities = getCities(contactRequestBody.getProvinces(), cities);
                boolQuery.must(matchQuery("city", cities));
            }
        }

        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder().withQuery(boolQuery);
        long totalCount = elasticsearchTemplate.count(builder.build(), EsContact.class);

        int page = Constant.PAGE_NUM;
        int size = Constant.PAGE_SIZE;
        Sort.Direction direction = Constant.DIRECTION;
        String orderBy = "id";
        Pageable pageable = new PageRequest(page, size, direction, orderBy);

        SearchQuery searchQuery = builder.withPageable(pageable).build();
        List<EsContact> esContacts = elasticsearchTemplate.queryForList(searchQuery, EsContact.class);

        List<ContactVO> contactVOS = new ArrayList<>();
        for (EsContact esContact : esContacts) {
            ContactVO vo = new ContactVO();
            vo.setCompany(esContact.getCompany());
            vo.setId(esContact.getId().intValue());
            vo.setMobile(esContact.getMobile());
            vo.setName(esContact.getName());
            vo.setVocation(ecVocationCodeManager.findNameByCode(esContact.getVocation()));
            vo.setProvince(ecCodeAreaManager.findById(esContact.getProvince()));
            contactVOS.add(vo);
        }
        return new SimplePage<>((int) totalCount / size + 1, totalCount, contactVOS);
    }
}