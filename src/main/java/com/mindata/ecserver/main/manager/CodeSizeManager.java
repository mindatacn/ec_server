package com.mindata.ecserver.main.manager;

import com.mindata.ecserver.main.model.primary.CodeSizeEntity;
import com.mindata.ecserver.main.repository.primary.CodeSizeRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author hanliqiang wrote on 2017/12/14
 */
@Service
public class CodeSizeManager {
    @Resource
    private CodeSizeRepository codeSizeRepository;

    /**
     * 获取所有规模
     *
     * @return 结果
     */
    public List<CodeSizeEntity> findAll() {
        return codeSizeRepository.findAll();
    }

    public String findNameById(Integer id) {
        CodeSizeEntity entity = codeSizeRepository.findOne(id);
        if (entity == null) {
            return "";
        }
        return entity.getName();
    }
}
