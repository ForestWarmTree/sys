package com.zt.sys.authority.service;

import com.zt.sys.authority.entity.SysUsersgroup;
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
public interface ISysUsersgroupService extends IService<SysUsersgroup> {

    /**
     * 新增用户组与用户对应关系
     */
    void saveUsersGroup(SysUsersgroup sysUsersgroup);

}
