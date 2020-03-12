package com.zt.sys.authority.mapper;

import com.zt.sys.authority.entity.SysDatacontroller;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jobob
 * @since 2020-02-17
 */
public interface SysDatacontrollerMapper extends BaseMapper<SysDatacontroller> {
    /**
     * 保存
     * @param sysDatacontroller
     */
    void saveData(SysDatacontroller sysDatacontroller);
}
