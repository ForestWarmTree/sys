package com.zt.sys.authority.service.impl;

import com.zt.sys.authority.entity.*;
import com.zt.sys.authority.mapper.SysColumnscontrollerMapper;
import com.zt.sys.authority.mapper.SysRolelogMapper;
import com.zt.sys.authority.service.ISysColumnscontrollerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zt.sys.authority.utils.ParamUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
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
     * @param dataCommonModel
     */
    @Override
    public void saveColumn(DataCommonModel dataCommonModel) {

        Map<String, String> map = new HashMap<>();
        map.put("roleId", dataCommonModel.getRoleId());
        map.put("resourceId", dataCommonModel.getResourceId());
        List<SysRolelog> sysRolelogList = new ArrayList<>();//保存日志集合
        //DB已有数据集合
        List<SysColumnsModel> columnsModelList = sysColumnscontrollerMapper.selectByRoleIdAndResourceId(map);
        Map<String, String> logMap = new HashMap<>();//已有数据map
        for(SysColumnsModel columnsModel:columnsModelList) {
            String value = columnsModel.getDataName()+columnsModel.getCloumnName();
            logMap.put(value,value);
        }

        Map<String, String> saveMap = new HashMap<>();//本次保存数据map
        for(SysColumnsModel columnsModel:dataCommonModel.getColumnscontrollerList()) {
            String value = columnsModel.getDataName()+columnsModel.getCloumnName();
            saveMap.put(value,value);
        }

        /**
         * 循环已有字段权限集合，与本次新增权限集合进行匹配
         */
        for(SysColumnsModel columnsModel:columnsModelList) {
            String key = columnsModel.getDataName()+columnsModel.getCloumnName();
            String flag = saveMap.get(key);
            /**
             * 如果在本次循环的权限集合中，未找到已有的权限
             * 则说明当前循环的权限已被删除。
             * 进行log记录
             */
            if(flag == null || "".equals(flag)) {
                SysRolelog sysRolelog = new SysRolelog();
                sysRolelog.setSourceName(columnsModel.getDataName()+"&"+columnsModel.getCloumnName());
                sysRolelog.setResourceId(columnsModel.getSourceId());//资源ID
                sysRolelog.setRoleId(columnsModel.getRoleId());//角色ID
                sysRolelog.setUpdateType(ParamUtil.DELETE);//变更类型描述
                sysRolelog.setUpdateTypeTips(ParamUtil.LogColunm);//变更类型描述
                sysRolelog.setSysUser(dataCommonModel.getCreateUser());//创建人
                sysRolelog.setSysTime(dataCommonModel.getCreateTime());//创建时间
                sysRolelog.setSysUserName(dataCommonModel.getCreateUserName());//创建人姓名
                sysRolelogList.add(sysRolelog);
            }
        }

        /**
         * 循环本次需要保存字段权限集合，与已有权限集合进行匹配
         */
        for(SysColumnsModel columnsModel:dataCommonModel.getColumnscontrollerList()) {
            String key = columnsModel.getDataName()+columnsModel.getCloumnName();
            String flag = logMap.get(key);
            /**
             * 如果本次循环的权限，在已有权限中未找
             * 则说明当前循环的权限是新增的。
             * 进行log记录
             */
            if(flag == null || "".equals(flag)) {
                SysRolelog sysRolelog = new SysRolelog();
                sysRolelog.setSourceName(columnsModel.getDataName()+"&"+columnsModel.getCloumnName());
                sysRolelog.setResourceId(columnsModel.getSourceId());//资源ID
                sysRolelog.setRoleId(columnsModel.getRoleId());//角色ID
                sysRolelog.setUpdateType(ParamUtil.INSERT);//变更类型描述
                sysRolelog.setUpdateTypeTips(ParamUtil.LogColunm);//变更类型描述
                sysRolelog.setSysUser(dataCommonModel.getCreateUser());//创建人
                sysRolelog.setSysTime(dataCommonModel.getCreateTime());//创建时间
                sysRolelog.setSysUserName(dataCommonModel.getCreateUserName());//创建人姓名
                sysRolelogList.add(sysRolelog);
            }
        }
        if(sysRolelogList!=null && sysRolelogList.size()>0) {
            //保存log记录
            logMapper.saveLogList(sysRolelogList);
        }
        //删除原有记录
        sysColumnscontrollerMapper.deleteColumn(dataCommonModel);

        for(SysColumnsModel columnsModel:dataCommonModel.getColumnscontrollerList()) {
            columnsModel.setRoleId(dataCommonModel.getRoleId());
            columnsModel.setSourceId(dataCommonModel.getResourceId());
            columnsModel.setCreateTime(dataCommonModel.getCreateTime());
            columnsModel.setCreateUser(dataCommonModel.getCreateUser());
            columnsModel.setCreateUserName(dataCommonModel.getCreateUserName());
            //保存
            sysColumnscontrollerMapper.saveColumn(columnsModel);
        }
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
