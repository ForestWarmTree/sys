package com.zt.sys.authority.mapper;

import com.zt.sys.authority.entity.SysRolelog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 操作记录表 Mapper 接口
 * </p>
 *
 * @author jobob
 * @since 2020-02-17
 */
public interface SysRolelogMapper extends BaseMapper<SysRolelog> {
    /**
     * 根据用户ID查询角色ID
     * @param userId
     * @return
     */
    List<String> getRoleIdByUserId(String userId);

    /**
     * 根据角色ID查询用户ID
     * @param roleId
     * @return
     */
    List<String> getUserIdByRoleId(String roleId);

    /**
     * 根据资源ID查询角色ID
     * @param resourceId
     * @return
     */
    List<String> getRoleIdByResourceId(String resourceId);

    /**
     * 根据角色ID查询资源ID
     * @param roleId
     * @return
     */
    List<String> getResourceIdByRoleId(String roleId);

    /**
     * 新增
     * @param sysRolelogs
     */
    void saveLogList(List<SysRolelog> sysRolelogs);

    /**
     * 新增
     * @param sysRolelog
     */
    void saveLog(SysRolelog sysRolelog);
}
