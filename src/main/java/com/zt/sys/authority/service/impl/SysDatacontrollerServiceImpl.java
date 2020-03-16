package com.zt.sys.authority.service.impl;

import com.zt.sys.authority.entity.SysDataModel;
import com.zt.sys.authority.entity.SysRoleinfo;
import com.zt.sys.authority.mapper.SysDatacontrollerMapper;
import com.zt.sys.authority.service.ISysDatacontrollerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jobob
 * @since 2020-02-17
 */
@Service
public class SysDatacontrollerServiceImpl extends ServiceImpl<SysDatacontrollerMapper, SysDataModel> implements ISysDatacontrollerService {

    @Resource
    private SysDatacontrollerMapper sysDatacontrollerMapper;

    /**
     * 保存
     * @param sysDatacontroller
     */
    @Override
    public void saveData(SysDataModel sysDatacontroller) {
        sysDatacontrollerMapper.saveData(sysDatacontroller);
    }

    /**
     * 根据角色编码与资源编码查询数据权限信息
     * @param sysRoleinfo
     * @return
     */
    @Override
    public List<SysDataModel> selectByRoleIdAndResourceId(SysRoleinfo sysRoleinfo) {
        Map<String, String> map = new HashMap<>();
        map.put("roleId", sysRoleinfo.getRoleId());
        map.put("resourceId", sysRoleinfo.getResourceId());
        return sysDatacontrollerMapper.selectByRoleIdAndResourceId(map);
    }
}
