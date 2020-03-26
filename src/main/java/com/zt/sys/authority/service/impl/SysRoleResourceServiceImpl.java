package com.zt.sys.authority.service.impl;

import com.zt.sys.authority.entity.*;
import com.zt.sys.authority.mapper.SysColumnscontrollerMapper;
import com.zt.sys.authority.mapper.SysDatacontrollerMapper;
import com.zt.sys.authority.mapper.SysRoleResourceMapper;
import com.zt.sys.authority.mapper.SysRolelogMapper;
import com.zt.sys.authority.service.ISysRoleResourceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zt.sys.authority.utils.ParamUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

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

    @Resource
    private SysDatacontrollerMapper datacontrollerMapper;

    @Resource
    private SysColumnscontrollerMapper sysColumnscontrollerMapper;

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
        if(sysRoleResource.getResourceIds()!=null && sysRoleResource.getResourceIds().size()>0) {
            sysRoleResourceMapper.saveRoleResource(map);
        }

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
        if(sysRoleResource.getRoleIds()!=null && sysRoleResource.getRoleIds().size()>0) {
            sysRoleResourceMapper.saveResourceRole(map);
        }

    }

    /**
     * 复制功能数据保存
     * @param sysRoleinfo
     */
    @Transactional
    @Override
    public void copyRoleSave(SysRoleinfo sysRoleinfo) {
        //查询出要被复制的角色下的资源权限
        List<String> result1 = sysRoleResourceMapper.selectResourceIdByRoleId(sysRoleinfo.getRoleId());
        Map<String, Boolean> menuMap = new HashMap<>();
        //根据被复制的角色，与该角色下的资源ID，查询出数据权限
        for(String resourceId:result1) {
            //将被复制的角色下的资源存入map
            menuMap.put(resourceId,true);

            Map<String, String> map = new HashMap<>();
            map.put("roleId", sysRoleinfo.getRoleId());
            map.put("resourceId", resourceId);
            //数据权限
            List<SysDataModel> dataResult = datacontrollerMapper.selectByRoleIdAndResourceId(map);
            //字段权限
            List<SysColumnsModel> columnResult = sysColumnscontrollerMapper.selectByRoleIdAndResourceId(map);

            //循环要赋值的角色ID集合,将数据权限进行保存
            for(String param: sysRoleinfo.getRoleIds()) {
                Map<String, String> map1 = new HashMap<>();
                map1.put("roleId", param);
                map1.put("resourceId", resourceId);

                //查询出需要被赋权的角色下的该资源的数据权限
                List<SysDataModel> dataResult1 = datacontrollerMapper.selectByRoleIdAndResourceId(map1);
                //查询出需要被赋权的角色下的该资源的字段权限
                List<SysColumnsModel> columnResult1 = sysColumnscontrollerMapper.selectByRoleIdAndResourceId(map1);
                //将需要被赋权的角色数据权限，放入map中
                Map<String, Boolean> dataMap = new HashMap<>();
                for(SysDataModel dataModel:dataResult1) {
                    String key = dataModel.getResourceId()+dataModel.getDataType()+
                            dataModel.getDataValue()+dataModel.getName();
                    dataMap.put(key,true);
                }
                //将需要被赋权的角色字段权限，放入map中
                Map<String, Boolean> columnMap = new HashMap<>();
                for(SysColumnsModel columnsModel:columnResult1) {
                    String key = columnsModel.getSourceId()+ columnsModel.getDataName()+columnsModel.getCloumnName();
                    columnMap.put(key,true);
                }

                //循环要被赋值的角色下的资源的数据权限，
                //如果与此次保存的数据权限相同，说明被赋权的角色的权限中，
                //已有本次循环的数据权限。如果有则不做操作，否则进行保存
                for(SysDataModel dataModel:dataResult) {
                    String key = dataModel.getResourceId()+dataModel.getDataType()+
                            dataModel.getDataValue()+dataModel.getName();
                    if(dataMap!=null && dataMap.size()>0) {
                        boolean flag = dataMap.get(key);
                        if(!flag) {
                            String sourceName = dataModel.getDataValue()+"&"+dataModel.getDataType()+"&"+dataModel.getName();
                            SysRolelog sysRolelog = new SysRolelog();
                            sysRolelog.setRoleId(param);//角色ID
                            sysRolelog.setSourceName(sourceName);
                            sysRolelog.setResourceId(dataModel.getResourceId());//资源ID
                            sysRolelog.setUpdateType(ParamUtil.INSERT);//变更类型描述
                            sysRolelog.setUpdateTypeTips(ParamUtil.LogData);//变更类型描述
                            sysRolelog.setSysUser(sysRoleinfo.getCreateUser());//创建人
                            sysRolelog.setSysTime(sysRoleinfo.getCreateTime());//创建时间
                            sysRolelog.setSysUserName(sysRoleinfo.getCreateUserName());//创建人姓名
                            logMapper.saveLog(sysRolelog);// 保存日志
                            //保存数据权限
                            dataModel.setRoleId(param);
                            datacontrollerMapper.saveData(dataModel);
                        }
                    } else {
                        String sourceName = dataModel.getDataValue()+"&"+dataModel.getDataType()+"&"+dataModel.getName();
                        SysRolelog sysRolelog = new SysRolelog();
                        sysRolelog.setRoleId(param);//角色ID
                        sysRolelog.setSourceName(sourceName);
                        sysRolelog.setResourceId(dataModel.getResourceId());//资源ID
                        sysRolelog.setUpdateType(ParamUtil.INSERT);//变更类型描述
                        sysRolelog.setUpdateTypeTips(ParamUtil.LogData);//变更类型描述
                        sysRolelog.setSysUser(sysRoleinfo.getCreateUser());//创建人
                        sysRolelog.setSysTime(sysRoleinfo.getCreateTime());//创建时间
                        sysRolelog.setSysUserName(sysRoleinfo.getCreateUserName());//创建人姓名
                        logMapper.saveLog(sysRolelog);// 保存日志
                        //保存数据权限
                        dataModel.setRoleId(param);
                        datacontrollerMapper.saveData(dataModel);
                    }

                }

                //循环要被赋值的角色下的资源的字段权限，
                //如果与此次保存的字段权限相同，说明被赋权的角色的权限中，
                //已有本次循环的字段权限。如果有则不做操作，否则进行保存
                for(SysColumnsModel columnsModel: columnResult) {
                    if(columnMap!=null && columnMap.size()>0) {
                        String key = columnsModel.getSourceId()+ columnsModel.getDataName()+columnsModel.getCloumnName();
                        boolean flag = columnMap.get(key);
                        if(!flag) {
                            SysRolelog sysRolelog = new SysRolelog();
                            sysRolelog.setSourceName(columnsModel.getDataName()+"&"+columnsModel.getCloumnName());
                            sysRolelog.setResourceId(columnsModel.getSourceId());//资源ID
                            sysRolelog.setRoleId(param);//角色ID
                            sysRolelog.setUpdateType(ParamUtil.INSERT);//变更类型描述
                            sysRolelog.setUpdateTypeTips(ParamUtil.LogColunm);//变更类型描述
                            sysRolelog.setSysUser(sysRoleinfo.getCreateUser());//创建人
                            sysRolelog.setSysTime(sysRoleinfo.getCreateTime());//创建时间
                            sysRolelog.setSysUserName(sysRoleinfo.getCreateUserName());//创建人姓名
                            logMapper.saveLog(sysRolelog);// 保存日志
                            //保存字段权限
                            columnsModel.setRoleId(param);
                            sysColumnscontrollerMapper.saveColumn(columnsModel);
                        }
                    } else {
                        SysRolelog sysRolelog = new SysRolelog();
                        sysRolelog.setSourceName(columnsModel.getDataName()+"&"+columnsModel.getCloumnName());
                        sysRolelog.setResourceId(columnsModel.getSourceId());//资源ID
                        sysRolelog.setRoleId(param);//角色ID
                        sysRolelog.setUpdateType(ParamUtil.INSERT);//变更类型描述
                        sysRolelog.setUpdateTypeTips(ParamUtil.LogColunm);//变更类型描述
                        sysRolelog.setSysUser(sysRoleinfo.getCreateUser());//创建人
                        sysRolelog.setSysTime(sysRoleinfo.getCreateTime());//创建时间
                        sysRolelog.setSysUserName(sysRoleinfo.getCreateUserName());//创建人姓名
                        logMapper.saveLog(sysRolelog);// 保存日志
                        //保存字段权限
                        columnsModel.setRoleId(param);
                        sysColumnscontrollerMapper.saveColumn(columnsModel);
                    }
                }

            }
        }

        //循环要被赋值的角色ID
        for(String roleId: sysRoleinfo.getRoleIds()) {
            //根据要被赋值的角色ID查询出已有的资源
            Map<String, Object> map = new HashMap<>();
            List<String> resourceIds = new ArrayList<>();
            List<String> result2 = sysRoleResourceMapper.selectResourceIdByRoleId(roleId);
            if(result2!=null && result2.size()>0) {
                for(String resourceId:result2) {
                    boolean flag = menuMap.get(resourceId);
                    if(!flag) {
                        resourceIds.add(resourceId);
                    }
                }
                for(String resourceId: resourceIds) {
                    SysRolelog sysRolelog = new SysRolelog();
                    sysRolelog.setResourceId(resourceId);//资源ID
                    sysRolelog.setRoleId(roleId);//角色ID
                    sysRolelog.setUpdateType(ParamUtil.INSERT);//变更类型
                    sysRolelog.setUpdateTypeTips(ParamUtil.LogSaveRoleResource);//变更类型业务描述
                    sysRolelog.setSysUser(sysRoleinfo.getCreateUser());//创建人
                    sysRolelog.setSysTime(sysRoleinfo.getCreateTime());//创建时间
                    sysRolelog.setSysUserName(sysRoleinfo.getCreateUserName());//创建人姓名
                    logMapper.saveLog(sysRolelog);//保存日志
                }
            } else {
                resourceIds = result1;
            }

            //保存资源权限
            SysRoleResource sysRoleResource = new SysRoleResource();
            sysRoleResource.setRoleId(roleId);//角色ID
            sysRoleResource.setResourceIds(resourceIds);
            sysRoleResource.setCreateTime(sysRoleinfo.getCreateTime());
            sysRoleResource.setCreateUser(sysRoleinfo.getCreateUser());
            map.put("sysRoleResource",sysRoleResource);
            map.put("resourceIds",resourceIds);
            if(map!=null && map.size()>0) {
                sysRoleResourceMapper.saveRoleResource(map);
            }
        }

    }
}
