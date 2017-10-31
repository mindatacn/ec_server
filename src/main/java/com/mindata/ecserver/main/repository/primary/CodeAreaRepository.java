package com.mindata.ecserver.main.repository.primary;

import com.mindata.ecserver.main.model.primary.CodeAreaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author wuweifeng wrote on 2017/10/25.
 */
public interface CodeAreaRepository extends JpaRepository<CodeAreaEntity, String> {
    /**
     * 查找某个省的城市
     *
     * @param begin
     *         开始
     * @param end
     *         结束
     * @return 城市集合
     */
    List<CodeAreaEntity> findByIdBetween(String begin, String end);
}
