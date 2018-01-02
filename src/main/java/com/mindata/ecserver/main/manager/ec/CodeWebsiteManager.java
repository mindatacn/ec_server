package com.mindata.ecserver.main.manager.ec;

import com.mindata.ecserver.main.model.primary.CodeWebsiteEntity;
import com.mindata.ecserver.main.repository.primary.CodeWebsiteRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author hanliqiang wrote on 2017/12/14
 */
@Service
public class CodeWebsiteManager {
    @Resource
    private CodeWebsiteRepository codeWebsiteRepository;

    /**
     * 获取所有来源
     *
     * @return 结果
     */
    public List<CodeWebsiteEntity> findAll() {
        return codeWebsiteRepository.findAll();
    }
}
