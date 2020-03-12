package com.zt.sys.authority.mapper;

import com.zt.sys.authority.entity.SysDeptinfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jobob
 * @since 2020-02-17
 */
public interface SysDeptinfoMapper extends BaseMapper<SysDeptinfo> {
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
