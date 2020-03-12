package com.zt.sys.authority.service;

import com.zt.sys.authority.entity.SysOrginfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 组织基础信息表 服务类
 * </p>
 *
 * @author jobob
 * @since 2020-02-17
 */
public interface ISysOrginfoService extends IService<SysOrginfo> {

    /**
     * 新增部门信息
     * @param sysOrginfo
     */
    void editOrgInfo(SysOrginfo sysOrginfo);

    /**
     * 编辑部门信息
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
