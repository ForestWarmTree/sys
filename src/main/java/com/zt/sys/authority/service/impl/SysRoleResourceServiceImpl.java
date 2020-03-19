package com.zt.sys.authority.service.impl;

import com.zt.sys.authority.entity.SysRoleResource;
import com.zt.sys.authority.entity.SysRolelog;
import com.zt.sys.authority.mapper.SysRoleResourceMapper;
import com.zt.sys.authority.mapper.SysRolelogMapper;
import com.zt.sys.authority.service.ISysRoleResourceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zt.sys.authority.utils.ParamUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 角色资源关系对应表 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2020-02-21
 */
@Service
public class SysRoleResourceServiceImpl extends ServiceImpl<SysRoleResourceMapper, SysRoleResource> implements ISysRoleResourceService {


    @Resource
    private SysRoleResourceMapper sysRoleResourceMapper;

    @Resource
    private SysRolelogMapper logMapper;

    /**
     * 新增角色资源关系 - 按角色分配
     * @param sysRoleResource
     */
    @Transactional
    @Override
    public void saveRoleResource(SysRoleResource sysRoleResource) {
        Map<String, Object> map = new HashMap<>();
        map.put("sysRoleResource",sysRoleResource);
        map.put("resourceIds",sysRoleResource.getResourceIds());
        List<SysRolelog> sysResourcelogList = new ArrayList<>();//log记录集合

        //查询该角色下已有资源ID集合
        List<String> logresourecList = sysRoleResourceMapper.selectResourceIdByRoleId(sysRoleResource.getRoleId());

        Map<String, String> logmap = new HashMap<>();//已有资源ID集合
        for(String logresourceid:logresourecList) {
            logmap.put(logresourceid,logresourceid);
        }

        Map<String, String> savemap = new HashMap<>();//本次需要保存的资源ID集合
        for(String resourceId:sysRoleResource.getResourceIds()) {
            savemap.put(resourceId, resourceId);
        }

        /**
         * 循环这次要保存的资源 ID，并与已有资源ID集合进行逐条匹配，
         * 如果未匹配上，说明是给角色新加的资源。
         * 新增log记录-->insert
         */
        for(String resourceId:sysRoleResource.getResourceIds()) {
            String flag = logmap.get(resourceId);
            if(flag == null || flag.equals("")) {
                SysRolelog sysRolelog = new SysRolelog();
                sysRolelog.setResourceId(resourceId);//资源ID
                sysRolelog.setRoleId(sysRoleResource.getRoleId());//角色ID
                sysRolelog.setUpdateType(ParamUtil.INSERT);//变更类型
                sysRolelog.setUpdateTypeTips(ParamUtil.LogSaveRoleResource);//变更类型业务描述
                sysRolelog.setSysUser(sysRoleResource.getCreateUser());//创建人
                sysRolelog.setSysTime(sysRoleResource.getCreateTime());//创建时间
                sysRolelog.setSysUserName(sysRoleResource.getCreateUserName());//创建人姓名
                sysResourcelogList.add(sysRolelog);
            }
        }

        /**
         * 循环该角色下已有的资源集合，并于本次需要保存的资源集合进行逐条匹配,
         * 如果未匹配上，说明是从该角色下删除了本次循环的资源。
         * 增加log记录-->delete
         */
        for(String resourceId:logresourecList) {
            String flag = logmap.get(resourceId);
            if(flag == null || flag.equals("")) {
                SysRolelog sysRolelog = new SysRolelog();
                sysRolelog.setResourceId(resourceId);//资源ID
                sysRolelog.setRoleId(sysRoleResource.getRoleId());//角色ID
                sysRolelog.setUpdateType(ParamUtil.DELETE);//变更类型
                sysRolelog.setUpdateTypeTips(ParamUtil.LogSaveRoleResource);//变更类型业务描述
                sysRolelog.setSysUser(sysRoleResource.getCreateUser());//创建人
                sysRolelog.setSysTime(sysRoleResource.getCreateTime());//创建时间
                sysRolelog.setSysUserName(sysRoleResource.getCreateUserName());//创建人姓名
                sysResourcelogList.add(sysRolelog);
            }
        }
        if(sysResourcelogList!=null && sysResourcelogList.size()>0) {
            //保存log记录
            logMapper.saveLogList(sysResourcelogList);
        }
        //删除原有关系
        sysRoleResourceMapper.deleteByResourceId(sysRoleResource.getRoleId());
        //新增
        sysRoleResourceMapper.saveRoleResource(map);
    }

    /**
     * 新增角色资源关系 - 按资源分配
     * @param sysRoleResource
     */
    @Transactional
    @Override
    public void saveResourceRole(SysRoleResource sysRoleResource) {
        Map<String, Object> map = new HashMap<>();
        map.put("sysRoleResource",sysRoleResource);
        map.put("roleIds",sysRoleResource.getRoleIds());
        List<SysRolelog> sysRolelogList = new ArrayList<>();

        List<String> logRoleList = logMapper.getRoleIdByResourceId(sysRoleResource.getResourceId());
        Map<String, String> logmap = new HashMap<>();//已有角色关系map
        for(String logroleid:logRoleList) {
            logmap.put(logroleid,logroleid);
        }

        Map<String, String> savemap = new HashMap<>();//本次要保存的角色map
        for(String roleid:sysRoleResource.getRoleIds()) {
            savemap.put(roleid, roleid);
        }
        /**
         * 循环这次要保存的角色ID，并与log表中的集合进行匹配，
         * 如果未匹配上，说明是给资源新分配得角色。
         * 进行log记录-->insert
         */
        for(String roleId:sysRoleResource.getRoleIds()) {
            String flag = logmap.get(roleId);
            if(flag == null || flag.equals("")) {
                SysRolelog sysRolelog = new SysRolelog();
                sysRolelog.setResourceId(sysRoleResource.getResourceId());//资源ID
                sysRolelog.setRoleId(roleId);//角色ID
                sysRolelog.setUpdateType(ParamUtil.INSERT);//变更类型
                sysRolelog.setUpdateTypeTips(ParamUtil.LogSaveRoleResource);//变更类型业务描述
                sysRolelog.setSysUser(sysRoleResource.getCreateUser());//创建人
                sysRolelog.setSysTime(sysRoleResource.getCreateTime());//创建时间
                sysRolelog.setSysUserName(sysRoleResource.getCreateUserName());//创建人姓名
                sysRolelogList.add(sysRolelog);
            }
        }

        /**
         * 循环已有角色集合，并与本次要保存的角色进行匹配
         * 如果未匹配到，说明本次循环的角色已被删除
         * 进行log记录-->delete
         */
        for(String roleId:logRoleList) {
            String flag = savemap.get(roleId);
            if(flag == null || flag.equals("")) {
                SysRolelog sysRolelog = new SysRolelog();
                sysRolelog.setResourceId(sysRoleResource.getResourceId());//资源ID
                sysRolelog.setRoleId(roleId);//角色ID
                sysRolelog.setUpdateType(ParamUtil.DELETE);//变更类型
                sysRolelog.setUpdateTypeTips(ParamUtil.LogSaveRoleResource);//变更类型业务描述
                sysRolelog.setSysUser(sysRoleResource.getCreateUser());//创建人
                sysRolelog.setSysTime(sysRoleResource.getCreateTime());//创建时间
                sysRolelog.setSysUserName(sysRoleResource.getCreateUserName());//创建人姓名
                sysRolelogList.add(sysRolelog);
            }
        }
        //保存log记录
        logMapper.saveLogList(sysRolelogList);
        //删除原有记录
        sysRoleResourceMapper.deleteByResourceId(sysRoleResource.getResourceId());
        //新增
        sysRoleResourceMapper.saveResourceRole(map);
    }
}
