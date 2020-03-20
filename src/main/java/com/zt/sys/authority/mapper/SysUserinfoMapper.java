package com.zt.sys.authority.mapper;

import com.zt.sys.authority.entity.SysUserinfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zt.sys.authority.entity.SysUsers;

import java.util.List;

/**
 * <p>
 * 用户基础信息表 Mapper 接口
 * </p>
 *
 * @author jobob
 * @since 2020-02-17
 */
public interface SysUserinfoMapper extends BaseMapper<SysUserinfo> {
    /**
     * 新增用户基础信息
     * @param userinfo
     */
    void saveUserInfo(SysUserinfo userinfo);
    /**
     * 根据用户ID获取用户基础信息
     * @param userId
     * @return
     */
    SysUserinfo getUserInfoByUserId(String userId);

    /**
     * 修改用户基础信息
     * @param userinfo
     */
    void updateUserInfo(SysUserinfo userinfo);
    /**
     * 人员列表查询
     * @param sysUserinfo
     * @return
     */
    List<SysUserinfo> selectUserInfoList(SysUserinfo sysUserinfo);

    /**
     * 删除用户信息
     * @param sysUserinfo
     */
    void deleteUserInfo(SysUserinfo sysUserinfo);
    /**
     * 根据组ID查询用户
     * @param groupId
     * @return
     */
    List<SysUserinfo> selectUserInfoListByGroupId(String groupId);

    /**
     * 根据角色编码查询用户
     * @param roleId
     * @return
     */
    List<SysUserinfo> selectUserInfoListByRoleId(String roleId);

}
