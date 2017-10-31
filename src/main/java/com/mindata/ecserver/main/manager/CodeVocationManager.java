package com.mindata.ecserver.main.manager;

import com.mindata.ecserver.main.model.CodeVocationTagEntity;
import com.mindata.ecserver.main.repository.CodeVocationTagRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wuweifeng wrote on 2017/10/26.
 */
@Service
public class CodeVocationManager {
    @Resource
    private CodeVocationTagRepository repository;

    public List<CodeVocationTagEntity> findAll() {
        return repository.findAll();
    }
}
