package com.mindata.ecserver.main.repository.secondary;

import com.mindata.ecserver.main.model.secondary.PtPushFailureResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

/**
 * @author wuweifeng wrote on 2017/10/26.
 */
public interface PtPushFailureResultRepository extends JpaRepository<PtPushFailureResult, Integer> {
    int countByCreateTimeBetween(Date begin, Date end);
}
