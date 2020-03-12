package com.zt.sys.authority.service;

import com.zt.sys.authority.entity.SysDatacontroller;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jobob
 * @since 2020-02-17
 */
public interface ISysDatacontrollerService extends IService<SysDatacontroller> {

    /**
     * 保存
     * @param sysDatacontroller
     */
    void saveData(SysDatacontroller sysDatacontroller);
}
