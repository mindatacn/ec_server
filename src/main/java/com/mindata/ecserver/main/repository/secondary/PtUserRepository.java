package com.mindata.ecserver.main.repository.secondary;

import com.mindata.ecserver.main.model.secondary.PtUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author wuweifeng wrote on 2017/10/26.
 */
public interface PtUserRepository extends JpaRepository<PtUser, Integer> {
    PtUser findByAccount(String account);

    /**
     * 根据ecUserId查询
     *
     * @param ecUserId
     *         ecuserId
     * @return 本地user
     */
    PtUser findByEcUserId(Long ecUserId);
}
