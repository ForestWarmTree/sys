package com.zt.sys.authority.mapper;

import com.zt.sys.authority.entity.SysOrginfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 组织基础信息表 Mapper 接口
 * </p>
 *
 * @author jobob
 * @since 2020-02-17
 */
public interface SysOrginfoMapper extends BaseMapper<SysOrginfo> {

    /**
     * 编辑部门信息
     * @param sysOrginfo
     */
    void editOrgInfo(SysOrginfo sysOrginfo);

    /**
     * 新增部门信息
     * @param sysOrginfo
     */
    void saveOrgInfo(SysOrginfo sysOrginfo);

    /**
     * 验证组织ID是否存在
     * @param orgId
     * @return
     */
    int validataOrgId(String orgId);

    /**
     * 根据检索条件查询所有组织信息
     * @param sysOrginfo
     * @return
     */
    List<SysOrginfo> selectAll(SysOrginfo sysOrginfo);

    /**
     * 根据当前登陆人的组织，查询该组织与该组织下的子级组织
     * @param sysOrginfo
     * @return
     */
    List<SysOrginfo> selectByUserOrgId(SysOrginfo sysOrginfo);
    /**
     * 删除组织信息
     * @param orgId
     */
    void deleteOrgs(String orgId);
}
