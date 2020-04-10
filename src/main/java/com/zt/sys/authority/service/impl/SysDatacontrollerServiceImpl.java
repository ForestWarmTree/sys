package com.zt.sys.authority.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zt.sys.authority.entity.DataCommonModel;
import com.zt.sys.authority.entity.SysDataModel;
import com.zt.sys.authority.entity.SysRoleinfo;
import com.zt.sys.authority.entity.SysRolelog;
import com.zt.sys.authority.logutil.BaseServiceLogger;
import com.zt.sys.authority.mapper.SysDatacontrollerMapper;
import com.zt.sys.authority.mapper.SysRolelogMapper;
import com.zt.sys.authority.service.ISysDatacontrollerService;
import com.zt.sys.authority.utils.ParamUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
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

    @Resource
    private SysRolelogMapper logMapper;
    /**
     * 保存
     * @param dataCommonModel
     */
    @Override
    public void saveData(DataCommonModel dataCommonModel) {
        Map<String, String> map = new HashMap<>();
        map.put("roleId",dataCommonModel.getRoleId());
        map.put("resourceId",dataCommonModel.getResourceId());
        List<SysRolelog> sysRolelogList = new ArrayList<>();//保存日志集合
        //获取已有数据集合
        List<SysDataModel> sysDataModelList = sysDatacontrollerMapper.selectByRoleIdAndResourceId(map);
        Map<String, String> logMap = new HashMap<>(); //已有数据集合map
        for(SysDataModel dataModel:sysDataModelList) {
            String value = dataModel.getDataValue()+dataModel.getDataType()+dataModel.getName();
            logMap.put(value, value);
        }

        //本次需保存数据map
        Map<String,String> saveMap = new HashMap<>();
        for(SysDataModel dataModel:dataCommonModel.getDatacontrollerList()) {
            String value = dataModel.getDataValue()+dataModel.getDataType()+dataModel.getName();
            saveMap.put(value,value);
        }


        //循环本次需要保存的数据集合
        for(SysDataModel dataModel:dataCommonModel.getDatacontrollerList()) {
            String value = dataModel.getDataValue()+dataModel.getDataType()+dataModel.getName();
            /**
             * 用本次需要保存的数据集合与 已有数据的集合，进行匹配。
             * 找出本次需要保存集合中 不存在已有数据集合中的数据。
             * 说明本次循环的数据是新增数据
             * 进行log记录
             */
            String flag = logMap.get(value);
            //如果本次保存的第N个角色ID，不存在已有角色集合中
            //则-->说明是新增权限
            if(flag == null || "".equals(flag)) {
                String sourceName = dataModel.getDataValue()+"&"+dataModel.getDataType()+"&"+dataModel.getName();
                SysRolelog sysRolelog = new SysRolelog();
                sysRolelog.setSourceName(sourceName);//变更资源名称
                sysRolelog.setRoleId(dataModel.getRoleId());//角色ID
                sysRolelog.setResourceId(dataModel.getResourceId());//资源ID
                sysRolelog.setUpdateType(ParamUtil.INSERT);//变更类型描述
                sysRolelog.setUpdateTypeTips(ParamUtil.LogData);//变更类型描述
                sysRolelog.setSysUser(dataCommonModel.getCreateUser());//创建人
                sysRolelog.setSysTime(dataCommonModel.getCreateTime());//创建时间
                sysRolelog.setSysUserName(dataCommonModel.getCreateUserName());//创建人姓名
                sysRolelogList.add(sysRolelog);
            }
        }


        //循环已有数据集合
        for(SysDataModel dataModel:sysDataModelList) {
            String value = dataModel.getDataValue()+dataModel.getDataType()+dataModel.getName();
            /**
             * 用已有的数据集合与 本次保存的数据集合，进行匹配。
             * 找出已有数据中，不存在本次需要保存数据集合中的数据
             * 说明本次循环的数据已被删除
             * 进行log记录
             */
            String flag = saveMap.get(value);
            //如果本次保存的第N个角色ID，不存在已有角色集合中
            //则-->说明是新增权限
            if(flag == null || "".equals(flag)) {
                String sourceName = dataModel.getDataValue()+"&"+dataModel.getDataType()+"&"+dataModel.getName();
                SysRolelog sysRolelog = new SysRolelog();
                sysRolelog.setSourceName(sourceName);//变更资源名称
                sysRolelog.setRoleId(dataModel.getRoleId());//角色ID
                sysRolelog.setResourceId(dataModel.getResourceId());//资源ID
                sysRolelog.setUpdateType(ParamUtil.DELETE);//变更类型描述
                sysRolelog.setUpdateTypeTips(ParamUtil.LogData);//变更类型描述
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
        //删除原有数据
        sysDatacontrollerMapper.deleteData(dataCommonModel);

        //保存
        for(SysDataModel dataModel:dataCommonModel.getDatacontrollerList()) {
            dataModel.setRoleId(dataCommonModel.getRoleId());
            dataModel.setResourceId(dataCommonModel.getResourceId());
            dataModel.setCreateTime(dataCommonModel.getCreateTime());
            dataModel.setCreateUser(dataCommonModel.getCreateUser());
            dataModel.setCreateUserName(dataCommonModel.getCreateUserName());
            sysDatacontrollerMapper.saveData(dataModel);
        }

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
