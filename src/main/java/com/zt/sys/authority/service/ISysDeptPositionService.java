package com.zt.sys.authority.service;

import com.zt.sys.authority.entity.SysDeptPosition;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jobob
 * @since 2020-02-17
 */
public interface ISysDeptPositionService extends IService<SysDeptPosition> {
    /**
     * 新增部门岗位关系
     * @param sysDeptPosition
     */
    void saveDeptPosition(SysDeptPosition sysDeptPosition);

}
