package com.mindata.ecserver.main.repository.secondary;

import com.mindata.ecserver.main.model.secondary.PtCustomerState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * @author wuweifeng wrote on 2017/10/25.
 */
public interface PtCustomerStateRepository extends JpaRepository<PtCustomerState, Long>,
        JpaSpecificationExecutor<PtCustomerState> {

    @Query("select count(distinct crmId) from PtCustomerState where operateTime > ?1 and operateTime < ?2" +
            " and oldData = 0 and sourceFrom in ?3 and saleState in ?4")
    Integer countDistinctAndOperateTimeBetween(Date begin, Date end, List<Integer> sourceFrom, List<Integer> saleState);

    @Query("select count(distinct crmId) from PtCustomerState where operateTime > ?1 and operateTime < ?2" +
            " and oldData = 0 and sourceFrom in ?3 and operateType = ?4")
    Integer countDistinctByOperateType(Date begin, Date end, List<Integer> sourceFrom, String operateType);
}
