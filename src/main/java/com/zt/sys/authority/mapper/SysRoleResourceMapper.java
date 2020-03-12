package com.zt.sys.authority.mapper;

import com.zt.sys.authority.entity.SysRoleResource;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 角色资源关系对应表 Mapper 接口
 * </p>
 *
 * @author jobob
 * @since 2020-02-21
 */
public interface SysRoleResourceMapper extends BaseMapper<SysRoleResource> {
    /**
     * 根据角色编码删除 角色资源对应关系
     * @param roleId
     */
    void deleteByRoleId(String roleId);

    /**
     * 新增角色资源对应关系 - 按角色分配
     * @param map
     */
    void saveRoleResource(Map<String, Object> map);

    /**
     * 根据资源编码删除 角色资源对应关系
     * @param roleId
     */
    void deleteByResourceId(String roleId);

    /**
     * 新增角色资源对应关系 - 按资源分配
     * @param map
     */
    void saveResourceRole(Map<String, Object> map);

    /**
     * 根据角色ID查询对应资源ID
     * @param roleIds
     * @return
     */
    List<SysRoleResource> selectResourceIdByRoleIds(List<String> roleIds);

    /**
     * 根据资源ID查询对应角色ID
     * @param resourceId
     * @return
     */
    List<String> selectRoleIdByResourceId(String resourceId);

    /**
     * 根据角色ID查询对应资源ID
     * @param roleId
     * @return
     */
    List<String> selectResourceIdByRoleId(String roleId);
}
