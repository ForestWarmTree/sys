package com.zt.sys.authority.service.impl;

import com.zt.sys.authority.entity.*;
import com.zt.sys.authority.mapper.*;
import com.zt.sys.authority.service.ISysRoleinfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zt.sys.authority.utils.ParamUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * <p>
 * 角色基础信息表 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2020-02-17
 */
@Service
public class SysRoleinfoServiceImpl extends ServiceImpl<SysRoleinfoMapper, SysRoleinfo> implements ISysRoleinfoService {

    @Resource
    private SysRoleinfoMapper roleinfoMapper;

    @Resource
    private SysRolelogMapper sysRolelogMapper;

    @Resource
    private SysUserRoleMapper sysUserRoleMapper;

    @Resource
    private SysRoleResourceMapper sysRoleResourceMapper;

    @Resource
    private SysResourceinfoMapper sysResourceinfoMapper;

    /**
     * 验证角色ID是否存在
     * @param roleId
     * @return
     */
    @Override
    public int validataRoleId(String roleId) {
        return roleinfoMapper.validataRoleId(roleId);
    }

    /**
     * 新增角色信息
     * @param roleinfo
     */
    @Transactional
    @Override
    public void saveRole(SysRoleinfo roleinfo) {
        SysRolelog sysRolelog = new SysRolelog();
        sysRolelog.setRoleId(roleinfo.getRoleId());//新增角色ID
        sysRolelog.setRoleName(roleinfo.getRoleName());//新增角色名称
        sysRolelog.setUpdateType(ParamUtil.INSERT);//变更类型
        sysRolelog.setUpdateTypeTips(ParamUtil.LogSaveRole);//变更类型业务描述
        sysRolelog.setSysTime(new Date());//创建时间
        sysRolelog.setSysUser(roleinfo.getCreateUser());//创建人id
        sysRolelog.setSysUserName(roleinfo.getCreateUserName());//创建人姓名
        //保存日志
        sysRolelogMapper.saveLog(sysRolelog);
        //保存角色
        roleinfoMapper.saveRole(roleinfo);
    }

    /**
     * 删除角色信息
     * @param roleinfo
     */
    @Transactional
    @Override
    public void deleteRoles(SysRoleinfo roleinfo) {

        for(String roleId:roleinfo.getRoleIds()) {
            //记录log日志
            SysRolelog sysRolelog = new SysRolelog();
            sysRolelog.setRoleId(roleId);//新增角色ID
            sysRolelog.setUpdateType(ParamUtil.DELETE);//变更类型
            sysRolelog.setUpdateTypeTips(ParamUtil.LogSaveRole);//变更类型业务描述
            sysRolelog.setSysTime(roleinfo.getCreateTime());//创建时间
            sysRolelog.setSysUser(roleinfo.getCreateUser());//创建人id
            sysRolelog.setSysUserName(roleinfo.getCreateUserName());//创建人姓名
            //保存日志
            sysRolelogMapper.saveLog(sysRolelog);
            //按角色ID 删除用户角色对应关系
            sysUserRoleMapper.deleteUserRoleByRoleId(roleId);
            //按角色ID 删除角色资源对应关系
            sysRoleResourceMapper.deleteByRoleId(roleId);
            //删除角色基础信息
            roleinfoMapper.deleteRoles(roleId);
        }


    }

    /**
     * 编辑角色信息
     * @param roleinfo
     */
    @Transactional
    @Override
    public void editRole(SysRoleinfo roleinfo) {
        SysRolelog sysRolelog = new SysRolelog();
        sysRolelog.setRoleId(roleinfo.getRoleId());//新增角色ID
        sysRolelog.setRoleName(roleinfo.getRoleName());//新增角色名称
        sysRolelog.setUpdateType(ParamUtil.UPDATE);//变更类型
        sysRolelog.setUpdateTypeTips(ParamUtil.LogSaveRole);//变更类型业务描述
        sysRolelog.setSysTime(new Date());//创建时间
        sysRolelog.setSysUser(roleinfo.getUpdateUser());//创建人id
        sysRolelog.setSysUserName(roleinfo.getCreateUserName());//创建人姓名
        //保存日志
        sysRolelogMapper.saveLog(sysRolelog);

        //修改角色
        roleinfoMapper.editRole(roleinfo);
    }

    /**
     * 根据 用户权限 查询可用角色
     * @param sysUsers
     * @return
     */
    @Override
    public List<SysRoleinfo> selectRoleByUserId(SysUsers sysUsers) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId",sysUsers.getUserId());
        map.put("orgId",sysUsers.getSysUserinfo().getOrgId());
        return roleinfoMapper.selectRoleByUserId(map);
    }

    /**
     * 根据当前登陆人ID获取角色信息，并且与前台传入得用户所拥有的角色进行去重
     * @param sysUserinfo
     * @return
     */
    @Override
    public List<SysRoleinfo> selectAuthRoleByUser(SysUserinfo sysUserinfo) {
        return roleinfoMapper.selectAuthRoleByUser(sysUserinfo);
    }

    /**
     * 根据前台传入得用户ID ，查询已被分配得角色
     * @param sysUsers
     * @return
     */
    @Override
    public List<SysRoleinfo> selectChooseRoleList(SysUsers sysUsers) {
        return roleinfoMapper.selectChooseRoleList(sysUsers);
    }

    /**
     * 查询所有角色信息
     * @return
     */
    @Override
    public List<SysRoleinfo> selectAll() {
        return roleinfoMapper.selectAll();
    }

    /**
     * 根据组织查询所属组织角色
     * @param sysRoleinfo
     * @return
     */
    @Override
    public List<SysRoleinfo> selectRoleByOrgId(SysRoleinfo sysRoleinfo) {
        return roleinfoMapper.selectRoleByOrgId(sysRoleinfo);
    }

    /**
     * 根据资源ID查询对应角色信息
     * @param resourceId
     * @return
     */
    @Override
    public List<SysRoleinfo> selectRoleByResourceId(String resourceId) {
        return roleinfoMapper.selectRoleByResourceId(resourceId);
    }

    /**
     * 根据角色ID查询角色基础信息及所有可用资源
     * @param roleinfo
     * @return
     */
    @Override
    public SysRoleinfo selectRoleInfoByRoleId(SysRoleinfo roleinfo) {
        //获取角色基础信息
        SysRoleinfo sysRoleinfo = roleinfoMapper.selectRoleInfoByRoleId(roleinfo);
        //获取该角色下的所有资源信息
        List<SysResourceinfo> resourceinfos = sysResourceinfoMapper.selectResourceByRoleId(roleinfo);
        if(resourceinfos!=null && resourceinfos.size() > 0) {
            //菜单ID集合
            List<String> menusIds = new ArrayList<>();

            for(SysResourceinfo resourceinfo:resourceinfos) {
                menusIds.add(resourceinfo.getResourceId());
            }
            //根据菜单ID查询按钮信息
            List<SysResourceinfo> btns = sysResourceinfoMapper.selectBtnByMenus(menusIds);

            for(SysResourceinfo resourceinfo:resourceinfos) {
                List<SysResourceinfo> btnInfos = new ArrayList<>();
                for(SysResourceinfo btn:btns) {
                    if(btn.getParentId().equals(resourceinfo.getResourceId())) {
                        btnInfos.add(btn);
                    }
                }
                resourceinfo.setActions(btnInfos);
            }
            sysRoleinfo.setPermissions(resourceinfos);
        }
        return sysRoleinfo;
    }

}
