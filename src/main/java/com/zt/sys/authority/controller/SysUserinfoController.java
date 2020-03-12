package com.zt.sys.authority.controller;


import com.zt.sys.authority.core.RetResponse;
import com.zt.sys.authority.core.RetResult;
import com.zt.sys.authority.entity.SysUserinfo;
import com.zt.sys.authority.entity.SysUsers;
import com.zt.sys.authority.service.ISysUserinfoService;
import com.zt.sys.authority.utils.HttpSessionValue;
import com.zt.sys.authority.utils.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 用户基础信息表 前端控制器
 * </p>
 *
 * @author jobob
 * @since 2020-02-17
 */
@RestController
@RequestMapping("/user")
public class SysUserinfoController {
    private final Logger logger=LoggerFactory.getLogger(SysUserinfoController.class);

    @Resource
    private HttpSessionValue sessionValue;

    @Resource
    private ISysUserinfoService sysUserinfoService;

    /**
     * 新增用户基础信息
     * @param userinfo
     * @return
     */
    @PostMapping("/saveUserInfo")
    @ResponseBody
    public RetResult<Map> saveUserInfo(@RequestBody SysUserinfo userinfo, HttpServletRequest request) {
        try {
            // 获取当前登陆人信息
            SysUsers sessionUser = sessionValue.getSessionUser(request);
            userinfo.setCreateUser(sessionUser.getUserId());//创建人
            userinfo.setCreateTime(new Date());// 创建时间
            userinfo.setCreateUserName(sessionUser.getName());//创建人姓名
            userinfo.setUserId(UUID.UU32());
            // 保存
            sysUserinfoService.saveUserInfo(userinfo);
        } catch (Exception e) {
            logger.error(e.toString());
            return RetResponse.makeSysErrRsp();
        }
        return RetResponse.makeOKRsp();
    }


    /**
     * 修改用户基础信息
     * @param userinfo
     * @return
     */
    @PostMapping("/updateUserInfo")
    @ResponseBody
    public RetResult<Map> updateUserInfo(@RequestBody SysUserinfo userinfo, HttpServletRequest request) {
        try {
            // 获取当前登陆人信息
            SysUsers sessionUser = sessionValue.getSessionUser(request);
            userinfo.setUpdateUser(sessionUser.getUserId());//创建人
            userinfo.setUpdateTime(new Date());// 创建时间
            // 修改
            sysUserinfoService.updateUserInfo(userinfo);
        } catch (Exception e) {
            logger.error(e.toString());
            return RetResponse.makeSysErrRsp();
        }
        return RetResponse.makeOKRsp();
    }

    /**
     * 根据用户ID获取用户基础信息
     * @return
     */
    @PostMapping("/getUserInfo")
    @ResponseBody
    public RetResult<Map> getUserInfoByUserId(HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        try {
            // 获取当前登陆人信息
            SysUsers sessionUser = sessionValue.getSessionUser(request);
            if(sessionUser!=null && sessionUser.getUserId() != null &&
                    !sessionUser.getUserId().equals("")) {
                SysUserinfo sysUserinfo = sysUserinfoService.getUserInfoByUserId(sessionUser.getUserId());

                result.put("data",sysUserinfo);
            } else {
                return RetResponse.makeErrRsp("登陆时间过期!请重新登陆");
            }
        } catch (Exception e) {
            logger.error(e.toString());
            return RetResponse.makeSysErrRsp();
        }
        return RetResponse.makeOKRsp(result);
    }

}
