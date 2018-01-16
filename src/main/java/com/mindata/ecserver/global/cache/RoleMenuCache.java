package com.mindata.ecserver.global.cache;

import com.mindata.ecserver.main.event.RoleMenuChangeEvent;
import com.mindata.ecserver.main.model.secondary.PtMenu;
import com.xiaoleilu.hutool.json.JSONArray;
import com.xiaoleilu.hutool.json.JSONObject;
import com.xiaoleilu.hutool.json.JSONUtil;
import com.xiaoleilu.hutool.util.CollectionUtil;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static com.mindata.ecserver.global.constant.CacheConstant.CACHE_ROLE_MENU_KEY;

/**
 * @author wuweifeng wrote on 2017/11/3.
 * 角色和menu对应关系的缓存
 */
@Component
public class RoleMenuCache extends BaseCache {

    /**
     * 根据角色查询menu集合的缓存
     *
     * @param roleId
     *         roleId
     * @return 菜单集合
     */
    public List<PtMenu> findMenuByRoleId(Long roleId) {
        Object object = stringRedisTemplate.opsForValue().get(keyOfRole(roleId));
        if (object == null) {
            return null;
        }
        JSONArray jsonArray = JSONUtil.parseArray(object.toString());
        return jsonArray.stream().map(json -> JSONUtil.toBean((JSONObject) json, PtMenu.class)).collect(Collectors
                .toList());
    }

    /**
     * 缓存role的menu信息
     *
     * @param roleId
     *         roleId
     * @param menus
     *         菜单集合
     */
    public void saveMenusByRoleId(Long roleId, List<PtMenu> menus) {
        if (CollectionUtil.isEmpty(menus)) {
            return;
        }
        stringRedisTemplate.opsForValue().set(keyOfRole(roleId), JSONUtil.toJsonStr(menus));
    }

    /**
     * 菜单的增删改查，和RoleMenu表的增加、删除会导致该缓存被清空
     *
     * @param roleMenuChangeEvent
     *         菜单事件
     */
    @SuppressWarnings("unchecked")
    @EventListener
    @Order(0)
    public void roleMenuChange(RoleMenuChangeEvent roleMenuChangeEvent) {
        List<Long> roleIds = (List<Long>) roleMenuChangeEvent.getSource();
        for (Long roleId : roleIds) {
            remove(roleId);
        }
    }

    private void remove(Long roleId) {
        stringRedisTemplate.delete(keyOfRole(roleId));
    }

    private String keyOfRole(Long roleId) {
        return CACHE_ROLE_MENU_KEY + "_" + roleId;
    }
}
