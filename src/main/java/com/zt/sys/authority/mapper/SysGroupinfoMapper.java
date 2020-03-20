package com.zt.sys.authority.mapper;

import com.zt.sys.authority.entity.SysGroupinfo;
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
    /**
     * 用户组列表查询
     * @param sysGroupinfo
     * @return
     */
    List<SysGroupinfo> selectGroupList(SysGroupinfo sysGroupinfo);
    /**
     * 删除用户组
     * @param sysGroupinfo
     */
    void deleteGroupInfo(SysGroupinfo sysGroupinfo);
}
