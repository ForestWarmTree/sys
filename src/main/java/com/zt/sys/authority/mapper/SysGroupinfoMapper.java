package com.zt.sys.authority.mapper;

import com.zt.sys.authority.entity.SysGroupinfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jobob
 * @since 2020-02-17
 */
public interface SysGroupinfoMapper extends BaseMapper<SysGroupinfo> {

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
}
