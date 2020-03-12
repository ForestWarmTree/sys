package com.zt.sys.authority.controller;


import com.zt.sys.authority.core.RetResponse;
import com.zt.sys.authority.core.RetResult;
import com.zt.sys.authority.entity.SysGroupinfo;
import com.zt.sys.authority.entity.SysUserinfo;
import com.zt.sys.authority.entity.SysUsers;
import com.zt.sys.authority.service.ISysGroupinfoService;
import com.zt.sys.authority.service.ISysUsersgroupService;
import com.zt.sys.authority.utils.HttpSessionValue;
import com.zt.sys.authority.utils.ParamUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
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
@RequestMapping("/authority/sys-groupinfo")
public class SysGroupinfoController {

    @Resource
    private HttpSessionValue sessionValue;

    @Resource
    private ISysGroupinfoService groupinfoService;
    /**
     * 新增用户组 信息
     * @return
     */
    @PostMapping("/saveGroupInfo")
    @ResponseBody
    public RetResult<Map> saveGroupInfo(@RequestBody SysGroupinfo sysGroupinfo, HttpServletRequest request) {
        try {
            // 获取当前登陆人信息
            SysUsers sessionUser = sessionValue.getSessionUser(request);
            if(sessionUser!=null && sessionUser.getUserId() != null &&
                    !sessionUser.getUserId().equals("")) {

                // 验证用户组编码是否存在
                sysGroupinfo.setGroupId(ParamUtil.GROUP + sysGroupinfo.getGroupId());
                int flag = groupinfoService.validataGroupId(sysGroupinfo.getGroupId());
                if(flag<=0) {
                    sysGroupinfo.setCreateTime(new Date());//创建时间
                    sysGroupinfo.setCreateUser(sessionUser.getUserId());//创建人
                    sysGroupinfo.setCreateUserName(sessionUser.getName());
                    groupinfoService.saveGroupInfo(sysGroupinfo);
                } else {
                    return RetResponse.makeErrRsp("组号重复!");
                }
            } else {
                return RetResponse.makeErrRsp("登陆时间过期!请重新登陆");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return RetResponse.makeSysErrRsp();
        }
        return RetResponse.makeOKRsp();
    }


    /**
     * 编辑用户组 信息
     * @return
     */
    @PostMapping("/updateGroupInfo")
    @ResponseBody
    public RetResult<Map> updateGroupInfo(@RequestBody SysGroupinfo sysGroupinfo, HttpServletRequest request) {
        try {
            // 获取当前登陆人信息
            SysUsers sessionUser = sessionValue.getSessionUser(request);
            if(sessionUser!=null && sessionUser.getUserId() != null &&
                    !sessionUser.getUserId().equals("")) {

                    sysGroupinfo.setUpdateTime(new Date());//修改时间
                    sysGroupinfo.setUpdateUser(sessionUser.getUserId());//修改人

                    groupinfoService.editGroupInfo(sysGroupinfo);
            } else {
                return RetResponse.makeErrRsp("登陆时间过期!请重新登陆");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return RetResponse.makeSysErrRsp();
        }
        return RetResponse.makeOKRsp();
    }
}
