package com.mindata.ecserver.main.manager;

import com.mindata.ecserver.main.model.primary.CodeAreaEntity;
import com.mindata.ecserver.main.repository.primary.CodeAreaRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wuweifeng wrote on 2017/10/26.
 */
@Service
public class EcCodeAreaManager {
    @Resource
    private CodeAreaRepository codeAreaRepository;

    /**
     * 根据省，查询所有的城市
     *
     * @param provinceCode
     *         省的code
     * @return 城市集合
     */
    public List<String> findCitiesByProvince(String provinceCode) {
        int province = Integer.valueOf(provinceCode);
        List<CodeAreaEntity> entities = codeAreaRepository.findByIdBetween(province + 1 + "", province + 9999 + "");
        return entities.stream().map(CodeAreaEntity::getId).collect(Collectors.toList());
    }

    /**
     * 根据id查询
     *
     * @param id
     *         id
     * @return 城市名
     */
    public String findById(String id) {
        return codeAreaRepository.findOne(id).getName();
    }
}
