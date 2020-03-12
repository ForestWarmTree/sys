package com.zt.sys.authority.mapper;

import com.zt.sys.authority.entity.SysRoleinfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zt.sys.authority.entity.SysUsers;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 角色基础信息表 Mapper 接口
 * </p>
 *
 * @author jobob
 * @since 2020-02-17
 */
public interface SysRoleinfoMapper extends BaseMapper<SysRoleinfo> {
    /**
     * 删除角色信息
     * @param roleId
     */
    void deleteRoles(String roleId);
    /**
     * 根据 用户权限 查询可用角色
     * @return
     */
    List<SysRoleinfo> selectRoleByUserId(Map<String, Object> map);

    /**
     * 查询所有角色信息
     * @return
     */
    List<SysRoleinfo> selectAll();

    /**
     * 验证角色ID是否存在
     * @param roleId
     * @return
     */
    int validataRoleId(String roleId);

    /**
     * 新增角色信息
     * @param roleinfo
     */
    void saveRole(SysRoleinfo roleinfo);

    /**
     * 编辑角色信息
     * @param roleinfo
     */
    void editRole(SysRoleinfo roleinfo);
    /**
     * 根据组织查询所属组织角色
     * @param sysRoleinfo
     * @return
     */
    List<SysRoleinfo> selectRoleByOrgId(SysRoleinfo sysRoleinfo);
    /**
     * 根据资源ID查询对应角色信息
     * @param resourceId
     * @return
     */
    List<SysRoleinfo> selectRoleByResourceId(String resourceId);
    /**
     * 根据角色ID查询角色详情
     * @param roleinfo
     * @return
     */
    SysRoleinfo selectRoleInfoByRoleId(SysRoleinfo roleinfo);

}
