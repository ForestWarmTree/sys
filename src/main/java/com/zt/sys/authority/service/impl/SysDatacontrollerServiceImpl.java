package com.zt.sys.authority.service.impl;

import com.zt.sys.authority.entity.SysDatacontroller;
import com.zt.sys.authority.mapper.SysDatacontrollerMapper;
import com.zt.sys.authority.service.ISysDatacontrollerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jobob
 * @since 2020-02-17
 */
@Service
public class SysDatacontrollerServiceImpl extends ServiceImpl<SysDatacontrollerMapper, SysDatacontroller> implements ISysDatacontrollerService {

    @Resource
    private SysDatacontrollerMapper sysDatacontrollerMapper;

    /**
     * 保存
     * @param sysDatacontroller
     */
    @Override
    public void saveData(SysDatacontroller sysDatacontroller) {
        sysDatacontrollerMapper.saveData(sysDatacontroller);
    }
}
