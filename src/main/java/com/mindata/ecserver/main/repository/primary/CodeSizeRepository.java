package com.mindata.ecserver.main.repository.primary;

import com.mindata.ecserver.main.model.primary.CodeSizeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author wuweifeng wrote on 2017/10/25.
 */
public interface CodeSizeRepository extends JpaRepository<CodeSizeEntity, Integer> {
}
