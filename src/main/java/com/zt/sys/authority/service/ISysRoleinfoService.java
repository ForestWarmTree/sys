package com.zt.sys.authority.service;

import com.zt.sys.authority.entity.SysRoleinfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zt.sys.authority.entity.SysUserinfo;
import com.zt.sys.authority.entity.SysUsers;

import java.util.List;

/**
 * <p>
 * 角色基础信息表 服务类
 * </p>
 *
 * @author jobob
 * @since 2020-02-17
 */
public interface ISysRoleinfoService extends IService<SysRoleinfo> {

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
     * 删除角色信息
     * @param roleinfo
     */
    void deleteRoles(SysRoleinfo roleinfo);

    /**
     * 编辑角色信息
     * @param roleinfo
     */
    void editRole(SysRoleinfo roleinfo);

    /**
     * 根据 用户权限 查询可用角色
     * @return
     */
    List<SysRoleinfo> selectRoleByUserId(SysUsers sysUsers);

    /**
     * 根据当前登陆人ID获取角色信息，并且与前台传入得用户所拥有的角色进行去重
     * @param sysUserinfo
     * @return
     */
    List<SysRoleinfo> selectAuthRoleByUser(SysUserinfo sysUserinfo);
    /**
     * 根据用户ID查询已分配角色
     * @param sysUsers
     * @return
     */
    List<SysRoleinfo> selectChooseRoleList(SysUsers sysUsers);

    /**
     * 查询所有角色信息
     * @return
     */
    List<SysRoleinfo> selectAll();

    /**
     * 根据组织查询所属组织角色
     * @param roleinfo
     * @return
     */
    List<SysRoleinfo> selectRoleByOrgId(SysRoleinfo roleinfo);

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
