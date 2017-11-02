package com.mindata.ecserver.main.service.base;

import com.mindata.ecserver.main.manager.EcCodeAreaManager;
import com.mindata.ecserver.main.manager.EcVocationCodeManager;
import com.mindata.ecserver.main.model.primary.EcVocationCodeEntity;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author wuweifeng wrote on 2017/10/30.
 */
@Service
public class BaseService {
    @Resource
    protected EcCodeAreaManager ecCodeAreaManager;
    @Resource
    private EcVocationCodeManager ecVocationCodeManager;
    @Resource
    protected ApplicationEventPublisher eventPublisher;

    protected List<String> getCities(List<String> provinces, List<String> cities) {
        //如果勾了某省，但是没勾它对应的市，则默认选中它所有的市
        for (String province : provinces) {
            if (!checkCityInProvice(cities, province)) {
                cities.addAll(ecCodeAreaManager.findCitiesByProvince(province));
            }
        }
        return cities;
    }

    /**
     * 根据用户选择的行业，计算最终应该检索的行业
     *
     * @param vocations
     *         用户传来的行业
     * @return 最终检索的行业
     */
    protected Set<Integer> getVocations(List<Integer> vocations) {
        List<EcVocationCodeEntity> list = new ArrayList<>();
        for (Integer code : vocations) {
            list.addAll(ecVocationCodeManager.findAllRelated(code));
        }
        return list.stream().map(EcVocationCodeEntity::getVocationCode).collect(Collectors.toSet());
    }

    /**
     * 判断城市列表里是否包含该省
     *
     * @param cities
     *         城市集合
     * @param province
     *         省
     * @return 是否包含
     */
    private boolean checkCityInProvice(List<String> cities, String province) {
        String provinceHead = province.substring(0, 2);
        for (String city : cities) {
            if (city.substring(0, 2).equals(provinceHead)) {
                return true;
            }
        }
        return false;
    }
}
