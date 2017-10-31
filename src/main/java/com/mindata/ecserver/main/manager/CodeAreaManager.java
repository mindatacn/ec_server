package com.mindata.ecserver.main.manager;

import com.mindata.ecserver.main.model.CodeAreaEntity;
import com.mindata.ecserver.main.repository.CodeAreaRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wuweifeng wrote on 2017/10/26.
 */
@Service
public class CodeAreaManager {
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
}
