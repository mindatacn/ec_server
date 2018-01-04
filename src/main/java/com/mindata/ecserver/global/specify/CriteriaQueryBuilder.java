package com.mindata.ecserver.global.specify;

import javax.persistence.criteria.*;
import java.util.List;

/**
 * 适用于对单表做sum、avg、count等运算时使用，并且查询条件不固定，需要动态生成predicate</p>
 * 如select sum(a), count(b), count distinct(c) from table where a = ? & b = ?
 *
 * @author wuweifeng wrote on 2018/1/3.
 */
public interface CriteriaQueryBuilder<T> {
    List<Selection<T>> buildSelections(CriteriaBuilder builder, Root<T> root);

    Predicate buildPredicate(CriteriaBuilder builder, Root<T> root);
}
