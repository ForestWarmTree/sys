package com.zt.sys.authority.service.impl;

import com.zt.sys.authority.entity.SysColumnscontroller;
import com.zt.sys.authority.mapper.SysColumnscontrollerMapper;
import com.zt.sys.authority.mapper.SysRolelogMapper;
import com.zt.sys.authority.service.ISysColumnscontrollerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 数据权限控制表 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2020-02-14
 */
@Service
public class SysColumnscontrollerServiceImpl extends ServiceImpl<SysColumnscontrollerMapper, SysColumnscontroller> implements ISysColumnscontrollerService {

    @Resource
    private SysColumnscontrollerMapper sysColumnscontrollerMapper;

    @Resource
    private SysRolelogMapper logMapper;

    /**
     * 保存
     * @param sysColumnscontroller
     */
    @Override
    public void saveColumn(SysColumnscontroller sysColumnscontroller) {

        //保存
        sysColumnscontrollerMapper.saveColumn(sysColumnscontroller);
    }
}
