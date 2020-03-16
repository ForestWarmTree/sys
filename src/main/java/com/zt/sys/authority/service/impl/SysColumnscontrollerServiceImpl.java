package com.zt.sys.authority.service.impl;

import com.zt.sys.authority.entity.SysColumnsModel;
import com.zt.sys.authority.entity.SysRoleinfo;
import com.zt.sys.authority.mapper.SysColumnscontrollerMapper;
import com.zt.sys.authority.mapper.SysRolelogMapper;
import com.zt.sys.authority.service.ISysColumnscontrollerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 数据权限控制表 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2020-02-14
 */
@Service
public class SysColumnscontrollerServiceImpl extends ServiceImpl<SysColumnscontrollerMapper, SysColumnsModel> implements ISysColumnscontrollerService {

    @Resource
    private SysColumnscontrollerMapper sysColumnscontrollerMapper;

    @Resource
    private SysRolelogMapper logMapper;

    /**
     * 保存
     * @param sysColumnscontroller
     */
    @Override
    public void saveColumn(SysColumnsModel sysColumnscontroller) {

        //保存
        sysColumnscontrollerMapper.saveColumn(sysColumnscontroller);
    }

    /**
     * 根据角色编码与资源编码查询对应的字段权限信息
     * @param sysRoleinfo
     * @return
     */
    @Override
    public List<SysColumnsModel> selectByRoleIdAndResourceId(SysRoleinfo sysRoleinfo) {
        Map<String, String> map = new HashMap<>();
        map.put("roleId",sysRoleinfo.getRoleId());
        map.put("resourceId",sysRoleinfo.getResourceId());
        return sysColumnscontrollerMapper.selectByRoleIdAndResourceId(map);
    }
}
