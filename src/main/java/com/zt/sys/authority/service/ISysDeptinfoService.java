package com.zt.sys.authority.service;

import com.zt.sys.authority.entity.SysDeptinfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jobob
 * @since 2020-02-17
 */
public interface ISysDeptinfoService extends IService<SysDeptinfo> {

    /**
     * 验证部门编码是否存在
     * @param deptId
     * @return
     */
    int validataDeptId(String deptId);

    /**
     * 新增部门信息
     * @param sysDeptinfo
     */
    void saveDeptInfo(SysDeptinfo sysDeptinfo);

    /**
     * 编辑部门信息
     * @param sysDeptinfo
     */
    void editDeptInfo(SysDeptinfo sysDeptinfo);
}
