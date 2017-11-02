package com.mindata.ecserver.main.manager;

import com.mindata.ecserver.main.model.primary.CodeVocationTagEntity;
import com.mindata.ecserver.main.repository.primary.CodeVocationTagRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wuweifeng wrote on 2017/10/26.
 */
@Service
public class EcCodeVocationTagManager {
    @Resource
    private CodeVocationTagRepository repository;

    public List<CodeVocationTagEntity> findAll() {
        return repository.findAll();
    }

    public String findNameByCode(int code) {
        return repository.findOne(code).getName();
    }
}
