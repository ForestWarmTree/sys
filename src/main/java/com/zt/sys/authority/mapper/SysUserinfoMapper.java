package com.zt.sys.authority.mapper;

import com.zt.sys.authority.entity.SysUserinfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

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

}
