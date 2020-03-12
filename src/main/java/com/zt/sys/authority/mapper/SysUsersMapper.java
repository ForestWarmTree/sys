package com.zt.sys.authority.mapper;

import com.zt.sys.authority.entity.SysUsers;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jobob
 * @since 2020-02-17
 */
public interface SysUsersMapper extends BaseMapper<SysUsers> {
    // 验证用户名是否重复
    int validataUserName(String userName);

    // 新增用户
    void saveUser(SysUsers sysUsers);

    // 根据组织编码查询用户
    List<SysUsers> selectUserByOrgId(String orgId);

    // 根据部门编码查询用户
    List<SysUsers> selectUserByDeptId(String deptId);

    // 根据角色编码查询用户
    List<SysUsers> selectUserByRoleId(String roleId);

    // 根据资源编码查询用户
    List<SysUsers> selectUserByResourceId(String resourceId);

    // 根据用户名查询密码
    SysUsers selectPassWordByUserName(String userName);

    // 根据用户ID获取用户权限
    SysUsers selectUsersResource(SysUsers sysUsers);
}
