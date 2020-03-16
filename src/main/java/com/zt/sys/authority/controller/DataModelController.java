package com.zt.sys.authority.controller;

import com.zt.sys.authority.entity.DataCommonModel;
import com.zt.sys.authority.entity.SysColumnsModel;
import com.zt.sys.authority.entity.SysDataModel;
import com.zt.sys.authority.entity.SysRoleinfo;
import com.zt.sys.authority.service.ISysColumnscontrollerService;
import com.zt.sys.authority.service.ISysDatacontrollerService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ  IDEA
 * User: 王传威
 * Date: 2020/3/13
 * Time: 16:09
 */
@RestController
@RequestMapping("/dataModel")
public class DataModelController {

    @Resource
    private ISysColumnscontrollerService sysColumnscontrollerService;

    @Resource
    private ISysDatacontrollerService sysDatacontrollerService;

    /**
     * 根据角色ID、资源ID查询数据权限与字段权限
     * @param sysRoleinfo
     * @return
     */
    @PostMapping("/selectDataAuth")
    @ResponseBody
    public Map<String, Object> selectDataAuth(@RequestBody SysRoleinfo sysRoleinfo) {
        Map<String, Object> result = new HashMap<>();
        try {
            //获取字段权限信息
            List<SysColumnsModel> columnsModels =  sysColumnscontrollerService.selectByRoleIdAndResourceId(sysRoleinfo);
            //获取数据权限信息
            List<SysDataModel> dataModels = sysDatacontrollerService.selectByRoleIdAndResourceId(sysRoleinfo);
            DataCommonModel dataCommonModel = new DataCommonModel();
            dataCommonModel.setColumnscontrollerList(columnsModels);
            dataCommonModel.setDatacontrollerList(dataModels);
            sysRoleinfo.setDataCommonModel(dataCommonModel);
            result.put("code",200);
            result.put("message", "");
            result.put("timestemp", "");
            result.put("result",sysRoleinfo);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("code",400);
            return result;
        }
        return result;
    }
}
