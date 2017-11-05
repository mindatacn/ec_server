package com.mindata.ecserver.global.cache;

import com.mindata.ecserver.main.event.MenuCrudEvent;
import com.mindata.ecserver.main.manager.PtRoleManager;
import com.mindata.ecserver.main.model.secondary.PtMenu;
import com.mindata.ecserver.main.model.secondary.PtRole;
import com.xiaoleilu.hutool.json.JSONArray;
import com.xiaoleilu.hutool.json.JSONObject;
import com.xiaoleilu.hutool.json.JSONUtil;
import com.xiaoleilu.hutool.util.CollectionUtil;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

import static com.mindata.ecserver.global.constant.CacheConstant.CACHE_ROLE_MENU_KEY;

/**
 * @author wuweifeng wrote on 2017/11/3.
 * 角色和menu对应关系的缓存
 */
@Component
public class RoleMenuCache extends BaseCache {
    @Resource
    private PtRoleManager ptRoleManager;

    /**
     * 根据角色查询menu集合的缓存
     *
     * @param roleId
     *         roleId
     * @return 菜单集合
     */
    public List<PtMenu> findMenuByRoleId(int roleId) {
        Object object = stringRedisTemplate.opsForValue().get(keyOfRole(roleId));
        if (object == null) {
            return null;
        }
        JSONArray jsonArray = JSONUtil.parseArray(object);
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
    public void setMenuByRoleId(int roleId, List<PtMenu> menus) {
        if (CollectionUtil.isEmpty(menus)) {
            return;
        }
        JSONArray jsonArray = JSONUtil.parseArray(menus);
        stringRedisTemplate.opsForValue().set(keyOfRole(roleId), JSONUtil.toJsonStr(jsonArray));
    }

    /**
     * 菜单的增删改查，会导致该缓存被清空
     *
     * @param menuCrudEvent
     *         菜单事件
     */
    @EventListener
    @Order(0)
    public void menuCrud(MenuCrudEvent menuCrudEvent) {
        removeAll();
    }

    public void removeAll() {
        List<PtRole> roles = ptRoleManager.findAll();
        roles.forEach(role -> stringRedisTemplate.delete(keyOfRole(role.getId())));
    }

    private String keyOfRole(int roleId) {
        return CACHE_ROLE_MENU_KEY + "_" + roleId;
    }
}
