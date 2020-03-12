package com.zt.sys.authority.controller;


import com.zt.sys.authority.core.RetResponse;
import com.zt.sys.authority.core.RetResult;
import com.zt.sys.authority.entity.SysGroupinfo;
import com.zt.sys.authority.entity.SysOrginfo;
import com.zt.sys.authority.entity.SysUsers;
import com.zt.sys.authority.service.ISysGroupinfoService;
import com.zt.sys.authority.service.ISysOrginfoService;
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
 * 组织基础信息表 前端控制器
 * </p>
 *
 * @author jobob
 * @since 2020-02-17
 */
@RestController
@RequestMapping("/authority/sys-orginfo")
public class SysOrginfoController {

    @Resource
    private HttpSessionValue sessionValue;

    @Resource
    private ISysOrginfoService orginfoService;
    /**
     * 新增组织信息
     * @return
     */
    @PostMapping("/saveOrgInfo")
    @ResponseBody
    public RetResult<Map> saveOrgInfo(@RequestBody SysOrginfo orginfo, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        try {
            // 获取当前登陆人信息
            SysUsers sessionUser = sessionValue.getSessionUser(request);
            if(sessionUser!=null && sessionUser.getUserId() != null &&
                    !sessionUser.getUserId().equals("")) {
                // 验证组织编码是否存在
                orginfo.setOrgId(ParamUtil.ORG + orginfo.getOrgId());
                int flag = orginfoService.validataOrgId(orginfo.getOrgId());
                if(flag<=0) {
                    orginfo.setCreateTime(new Date());//创建时间
                    orginfo.setCreateUser(sessionUser.getUserId());//创建人
                    orginfo.setCreateUserName(sessionUser.getName());//创建人姓名
                    orginfoService.saveOrgInfo(orginfo);
                } else {
                    return RetResponse.makeErrRsp("组织编码重复!");
                }
            } else {
                return RetResponse.makeErrRsp("登陆时间过期!请重新登陆");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return RetResponse.makeSysErrRsp();
        }
        return RetResponse.makeOKRsp(result);
    }

    /**
     * 编辑组织信息
     * @param orginfo
     * @param request
     * @return
     */
    @PostMapping("/editOrgInfo")
    @ResponseBody
    public RetResult<Map> editOrgInfo(@RequestBody SysOrginfo orginfo, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        try {
            // 获取当前登陆人信息
            SysUsers sessionUser = sessionValue.getSessionUser(request);
            if(sessionUser!=null && sessionUser.getUserId() != null &&
                    !sessionUser.getUserId().equals("")) {

                    orginfo.setUpdateTime(new Date());//修改时间
                    orginfo.setUpdateUser(sessionUser.getUserId());//修改人

                    orginfoService.editOrgInfo(orginfo);
            } else {
                return RetResponse.makeErrRsp("登陆时间过期!请重新登陆");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return RetResponse.makeSysErrRsp();
        }
        return RetResponse.makeOKRsp(result);
    }
}
