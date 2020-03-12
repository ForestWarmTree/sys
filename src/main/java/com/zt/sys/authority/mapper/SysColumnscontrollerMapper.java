package com.zt.sys.authority.mapper;

import com.zt.sys.authority.entity.SysColumnscontroller;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 数据权限控制表 Mapper 接口
 * </p>
 *
 * @author jobob
 * @since 2020-02-14
 */
@Mapper
public interface SysColumnscontrollerMapper extends BaseMapper<SysColumnscontroller> {
    /**
     * 保存
     * @param sysColumnscontroller
     */
    void saveColumn(SysColumnscontroller sysColumnscontroller);
}
