package com.zt.sys.authority.service;

import com.zt.sys.authority.entity.SysUserinfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户基础信息表 服务类
 * </p>
 *
 * @author jobob
 * @since 2020-02-17
 */
public interface ISysUserinfoService extends IService<SysUserinfo> {

    /**
     * 新增用户基础信息
     * @param userinfo
     */
    void saveUserInfo(SysUserinfo userinfo);

    /**
     * 修改用户基础信息
     * @param userinfo
     */
    void updateUserInfo(SysUserinfo userinfo);

    /**
     * 根据用户ID获取用户基础信息
     * @param userId
     * @return
     */
    SysUserinfo getUserInfoByUserId(String userId);
}
