package com.zt.sys.authority.mapper;

import com.zt.sys.authority.entity.SysResourceinfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zt.sys.authority.entity.SysRoleinfo;
import com.zt.sys.authority.entity.SysUserRole;
import com.zt.sys.authority.entity.SysUsers;

import java.util.List;

/**
 * <p>
 * 资源基础信息表 Mapper 接口
 * </p>
 *
 * @author jobob
 * @since 2020-02-17
 */
public interface SysResourceinfoMapper extends BaseMapper<SysResourceinfo> {

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
     * 根据资源ID查询资源详情
     * @param resourceId
     * @return
     */
    SysResourceinfo selectResourceInfoByResourceId(String resourceId);

    /**
     * 根据用户ID获取所有资源菜单
     * @param sysUsers
     * @return
     */
    List<SysResourceinfo> selectResourceByUserId(SysUsers sysUsers);

    /**
     * 集合传参
     * 根据菜单ID获取按钮信息
     * @param menuIds
     * @return
     */
    List<SysResourceinfo> selectBtn(List<String> menuIds);

    /**
     *  单个传参
     * 根据菜单ID获取按钮信息
     * @param menuId
     * @return
     */
    List<SysResourceinfo> selectBtnByResourceId(String menuId);

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
     * 删除
     * @param sysResourceinfo
     */
    void deleteResources(SysResourceinfo sysResourceinfo);
}
