package com.zt.sys.authority.service;

import com.zt.sys.authority.entity.SysGroupinfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zt.sys.authority.entity.SysUsers;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jobob
 * @since 2020-02-17
 */
public interface ISysGroupinfoService extends IService<SysGroupinfo> {
    /**
     * 新增用户组信息
     * @param sysGroupinfo
     */
    void saveGroupInfo(SysGroupinfo sysGroupinfo);

    /**
     * 编辑用户组信息
     * @param sysGroupinfo
     */
    void editGroupInfo(SysGroupinfo sysGroupinfo);

    /**
     * 验证组号是否存在
     * @param groupId
     */
    int validataGroupId(String groupId);

    /**
     * 用户组列表查询
     * @param sysGroupinfo
     * @return
     */
    List<SysGroupinfo> selectGroupList(SysGroupinfo sysGroupinfo);

    /**
     * 批量删除用户组
     * @param sysGroupinfos
     */
    void deleteGroupInfo(List<SysGroupinfo> sysGroupinfos, SysUsers sessionUser);

}
