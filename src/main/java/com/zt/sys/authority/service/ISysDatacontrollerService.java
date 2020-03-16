package com.zt.sys.authority.service;

import com.zt.sys.authority.entity.SysDataModel;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zt.sys.authority.entity.SysRoleinfo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jobob
 * @since 2020-02-17
 */
public interface ISysDatacontrollerService extends IService<SysDataModel> {

    /**
     * 保存
     * @param sysDatacontroller
     */
    void saveData(SysDataModel sysDatacontroller);

    /**
     * 根据角色编码与资源编码查询数据权限信息
     * @param sysRoleinfo
     * @return
     */
    List<SysDataModel> selectByRoleIdAndResourceId(SysRoleinfo sysRoleinfo);
}
