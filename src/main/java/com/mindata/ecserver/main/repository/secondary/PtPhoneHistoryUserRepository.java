package com.mindata.ecserver.main.repository.secondary;

import com.mindata.ecserver.main.model.secondary.PtPhoneHistoryUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

/**
 * @author wuweifeng wrote on 2017/10/26.
 */
public interface PtPhoneHistoryUserRepository extends JpaRepository<PtPhoneHistoryUser, Integer> {
    /**
     * 查询某个用户一段时间内的通话历史统计
     *
     * @param ecUserId
     *         ecUserId
     * @param begin
     *         开始时间
     * @param end
     *         结束时间
     * @param pageable
     *         分页
     * @return page
     */
    Page<PtPhoneHistoryUser> findByEcUserIdAndStartTimeBetween(Long ecUserId, Date begin, Date end, Pageable pageable);

    List<PtPhoneHistoryUser> findByEcUserIdAndStartTimeBetween(Long ecUserId, Date begin, Date end);
}
