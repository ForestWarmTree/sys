package com.zt.sys.authority.service;

import com.zt.sys.authority.entity.SysResourceinfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zt.sys.authority.entity.SysRoleinfo;
import com.zt.sys.authority.entity.SysUserRole;
import com.zt.sys.authority.entity.SysUsers;

import java.util.List;

/**
 * <p>
 * 资源基础信息表 服务类
 * </p>
 *
 * @author jobob
 * @since 2020-02-17
 */
public interface ISysResourceinfoService extends IService<SysResourceinfo> {

    /**
     * 保存资源信息
     * @param sysResourceinfo
     */
    void saveResource(SysResourceinfo sysResourceinfo);

    /**
     * 编辑资源信息
     * @param sysResourceinfo
     */
    void editResource(SysResourceinfo sysResourceinfo);

    /**
     * 根据用户ID获取所有资源
     * @param sysUsers
     * @return
     */
    List<SysResourceinfo> selectResourceByUserId(SysUsers sysUsers);

    /**
     * 根据用户ID 查询资源列表
     * @param sysResourceinfo
     * @return
     */
    List<SysResourceinfo> selectResourceList(SysResourceinfo sysResourceinfo);

    /**
     * 根据角色ID返回对应资源信息
     * @param sysRoleinfo
     * @return
     */
    List<SysResourceinfo> selectResourceByRoleId(SysRoleinfo sysRoleinfo);

    /**
     * 验证资源编码是否重复
     * @param resourceId
     * @return
     */
    int validataResourceId(String resourceId);

    /**
     * 列表查询--资源信息
     * @param sysResourceinfo
     * @return
     */
    List<SysResourceinfo> selectAll(SysResourceinfo sysResourceinfo);

    /**
     * 批量删除
     * @param sysResourceinfos
     * @param sessionUser
     */
    void deleteResources(List<SysResourceinfo> sysResourceinfos, SysUsers sessionUser);

    /**
     * 根据资源ID查询资源详情
     * @param sysResourceinfo
     * @return
     */
    SysResourceinfo selectResourceInfoByResourceId(SysResourceinfo sysResourceinfo);

}
