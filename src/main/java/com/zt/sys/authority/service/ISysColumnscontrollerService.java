package com.zt.sys.authority.service;

import com.zt.sys.authority.entity.SysColumnscontroller;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 数据权限控制表 服务类
 * </p>
 *
 * @author jobob
 * @since 2020-02-14
 */
public interface ISysColumnscontrollerService extends IService<SysColumnscontroller> {
    /**
     * 保存
     * @param sysColumnscontroller
     */
    void saveColumn(SysColumnscontroller sysColumnscontroller);
}
