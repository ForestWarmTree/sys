package com.zt.sys.authority.controller;


import com.zt.sys.authority.core.RetResponse;
import com.zt.sys.authority.core.RetResult;
import com.zt.sys.authority.entity.SysColumnsModel;
import com.zt.sys.authority.entity.SysUsers;
import com.zt.sys.authority.service.ISysColumnscontrollerService;

import com.zt.sys.authority.utils.HttpSessionValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

/**
 * <p>
 * 数据权限控制表 前端控制器
 * </p>
 *
 * @author jobob
 * @since 2020-02-14
 */
@RestController
@RequestMapping("/auth")
public class SysColumnscontrollerController {
    private final Logger logger=LoggerFactory.getLogger(SysColumnscontrollerController.class);
    @Resource
    private HttpSessionValue sessionValue;

    @Resource
    private ISysColumnscontrollerService sysColumnscontrollerService;

    /**
     * 新增字段权限
     * @param sysColumnscontroller
     * @return
     */
    @PostMapping("/saveColumn")
    @ResponseBody
    public RetResult<Map> saveColumn(@RequestBody SysColumnsModel sysColumnscontroller, HttpServletRequest request) {
        try {
            // 获取当前登陆人信息
            SysUsers sessionUser = sessionValue.getSessionUser(request);
            sysColumnscontroller.setCreateUser(sessionUser.getUserId());//创建人
            sysColumnscontroller.setCreateTime(new Date());// 创建时间
            sysColumnscontroller.setCreateUserName(sessionUser.getName());
            // 保存
            sysColumnscontrollerService.saveColumn(sysColumnscontroller);
        } catch (Exception e) {
            logger.error(e.toString());
            return RetResponse.makeSysErrRsp();
        }
        return RetResponse.makeOKRsp();
    }
}
