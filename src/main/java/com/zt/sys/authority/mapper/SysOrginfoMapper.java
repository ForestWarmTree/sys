package com.zt.sys.authority.mapper;

import com.zt.sys.authority.entity.SysOrginfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

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
}
