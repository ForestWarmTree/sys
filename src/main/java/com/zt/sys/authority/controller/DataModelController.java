package com.zt.sys.authority.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zt.sys.authority.core.RetResponse;
import com.zt.sys.authority.core.RetResult;
import com.zt.sys.authority.entity.*;
import com.zt.sys.authority.logutil.BaseLogger;
import com.zt.sys.authority.service.ISysColumnscontrollerService;
import com.zt.sys.authority.service.ISysDatacontrollerService;
import com.zt.sys.authority.utils.HttpSessionValue;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
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
public class DataModelController extends BaseLogger {

    @Resource
    private ISysColumnscontrollerService sysColumnscontrollerService;

    @Resource
    private ISysDatacontrollerService sysDatacontrollerService;

    @Resource
    private HttpSessionValue sessionValue;

    @GetMapping("/testlog")
    @ResponseBody
    public void testlog() {
        loginfo("info信息描述");
        logerror("错误信息：123");
    }


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
            logerror("错误信息：" + e.toString());
            result.put("code",400);
            return result;
        }
        return result;
    }


    /**
     * 保存数据及字段权限
     * @param dataCommonModel
     * @param request
     * @return
     */
    @PostMapping("/save")
    @ResponseBody
    public RetResult<Map> save(@RequestBody DataCommonModel dataCommonModel,
                               HttpServletRequest request) {
        try {
            // 获取当前登陆人信息
            SysUsers sessionUser = sessionValue.getSessionUserAuth(request);
            dataCommonModel.setCreateUser(sessionUser.getUserId());//创建人
            dataCommonModel.setCreateTime(new Date());// 创建时间
            dataCommonModel.setCreateUserName(sessionUser.getName());
            //数据权限
            sysDatacontrollerService.saveData(dataCommonModel);
            //字段权限
            sysColumnscontrollerService.saveColumn(dataCommonModel);

        } catch (Exception e) {
            logerror("错误信息：" + e.toString());
        }
        return RetResponse.makeOKRsp();
    }


}
