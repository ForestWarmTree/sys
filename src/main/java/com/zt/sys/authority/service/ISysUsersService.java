package com.zt.sys.authority.service;

import com.zt.sys.authority.entity.SysUsers;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jobob
 * @since 2020-02-17
 */
public interface ISysUsersService extends IService<SysUsers> {
    // 根据用户名查询密码
    SysUsers selectPassWordByUserName(String userName);
    // 验证用户名是否重复
    int validataUserName(String userName);

    // 新增用户
    void saveUser(SysUsers sysUsers);

    // 根据组织编码查询用户
    List<SysUsers> selectUserByOrgId(String orgId);

    //根据部门编码查询用户
    List<SysUsers> selectUserByDeptId(String deptId);

    // 根据角色编码查询用户
    List<SysUsers> selectUserByRoleId(String roleId);

    // 根据资源编码
    List<SysUsers> selectUserByResourceId(String resourceId);

    // 根据用户ID获取用户权限
    SysUsers selectUsersResource(SysUsers sysUsers);
}