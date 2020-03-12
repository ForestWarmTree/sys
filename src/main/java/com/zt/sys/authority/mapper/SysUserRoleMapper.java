package com.zt.sys.authority.mapper;

import com.zt.sys.authority.entity.SysUserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户基础信息表 Mapper 接口
 * </p>
 *
 * @author jobob
 * @since 2020-02-17
 */
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {

    /**
     * 根据用户ID 删除用户角色对应关系
     * @param userId
     */
    void deleteUserRoleByUserId(String userId);
    /**
     * 保存用户角色对应关系-按用户分配
     * @param map
     */
    void saveUserRole(Map<String, Object> map);
    /**
     * 根据角色ID 删除用户角色对应关系
     * @param roleId
     */
    void deleteUserRoleByRoleId(String roleId);

    /**
     * 保存用户角色对应关系-按角色分配
     * @param map
     */
    void saveRoleUser(Map<String, Object> map);
    /**
     * 把A用户的权限复制B用户
     * @param sysUserRole
     */
    void saveUserRoleByAB(List<SysUserRole> sysUserRole);

    /**
     * 根据用户ID查询对应角色ID
     * @param userId
     * @return
     */
    List<SysUserRole> selectAllByUserId(String userId);

    /**
     * 根据角色ID查询对应用户
     * @param roleId
     * @return
     */
    List<SysUserRole> selectAllByRoleId(String roleId);

}
