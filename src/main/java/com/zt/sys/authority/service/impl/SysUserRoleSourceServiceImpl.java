package com.zt.sys.authority.service.impl;

import com.zt.sys.authority.entity.SysRoleResource;
import com.zt.sys.authority.entity.SysUserRoleSource;
import com.zt.sys.authority.mapper.SysRoleResourceMapper;
import com.zt.sys.authority.mapper.SysUserRoleSourceMapper;
import com.zt.sys.authority.service.ISysUserRoleSourceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户角色资源关系关联表 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2020-02-17
 */
@Service
public class SysUserRoleSourceServiceImpl extends ServiceImpl<SysUserRoleSourceMapper, SysUserRoleSource> implements ISysUserRoleSourceService {

    @Resource
    private SysUserRoleSourceMapper sysUserRoleSourceMapper;

    @Resource
    private SysRoleResourceMapper sysRoleResourceMapper;

    /**
     * 新增用户角色权限对应关系
     * @param sysUserRoleSource
     */
    @Override
    public void saveUserRoleResource(SysUserRoleSource sysUserRoleSource) {
        List<String> roleIds = sysUserRoleSource.getRoleIds();
        // 根据角色ID 获取角色资源对应信息
        List<SysRoleResource> roleResourceList = sysRoleResourceMapper.selectResourceIdByRoleIds(roleIds);

        Map<String, Object> map = new HashMap<>();
        map.put("sysUserRoleSource",sysUserRoleSource);
        map.put("roleResourceList",roleResourceList);

        sysUserRoleSourceMapper.saveUserRoleResource(map);
    }
}
