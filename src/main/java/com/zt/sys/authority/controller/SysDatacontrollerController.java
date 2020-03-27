package com.zt.sys.authority.controller;


import com.zt.sys.authority.core.RetResponse;
import com.zt.sys.authority.core.RetResult;
import com.zt.sys.authority.entity.DataCommonModel;
import com.zt.sys.authority.entity.SysDataModel;
import com.zt.sys.authority.entity.SysUsers;
import com.zt.sys.authority.service.ISysDatacontrollerService;
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
 *  前端控制器
 * </p>
 *
 * @author jobob
 * @since 2020-02-17
 */
@RestController
@RequestMapping("/auth")
public class SysDatacontrollerController {
    private final Logger logger=LoggerFactory.getLogger(SysColumnscontrollerController.class);
    @Resource
    private HttpSessionValue sessionValue;

    @Resource
    private ISysDatacontrollerService sysDatacontrollerService;
    /**
     * 新增数据权限
     * @param datacontroller
     * @return
     */
    @PostMapping("/saveData")
    @ResponseBody
    public RetResult<Map> saveData(@RequestBody SysDataModel datacontroller,
                                     HttpServletRequest request) {
        try {
            // 获取当前登陆人信息
            SysUsers sessionUser = sessionValue.getSessionUserAuth(request);
            datacontroller.setCreateUser(sessionUser.getUserId());//创建人
            datacontroller.setCreateTime(new Date());// 创建时间
            datacontroller.setCreateUserName(sessionUser.getName());
            // 保存
            sysDatacontrollerService.saveData(new DataCommonModel());
        } catch (Exception e) {
            logger.error(e.toString());
            return RetResponse.makeSysErrRsp();
        }
        return RetResponse.makeOKRsp();
    }






}
