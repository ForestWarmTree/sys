package com.zt.sys.authority.controller;


import com.zt.sys.authority.core.RetResponse;
import com.zt.sys.authority.core.RetResult;
import com.zt.sys.authority.entity.SysDeptinfo;
import com.zt.sys.authority.entity.SysOrginfo;
import com.zt.sys.authority.entity.SysUsers;
import com.zt.sys.authority.service.ISysDeptinfoService;
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
 *  前端控制器
 * </p>
 * 部门信息模块
 * @author jobob
 * @since 2020-02-17
 */
@RestController
@RequestMapping("/authority/sys-deptinfo")
public class SysDeptinfoController {

    @Resource
    private HttpSessionValue sessionValue;

    @Resource
    private ISysDeptinfoService sysDeptinfoService;
    /**
     * 新增部门信息
     * @return
     */
    @PostMapping("/saveDeptInfo")
    @ResponseBody
    public RetResult<Map> saveDeptInfo(@RequestBody SysDeptinfo deptinfo, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        try {
            // 获取当前登陆人信息
            SysUsers sessionUser = sessionValue.getSessionUser(request);
            if(sessionUser!=null && sessionUser.getUserId() != null &&
                    !sessionUser.getUserId().equals("")) {

                // 验证组织编码是否存在
                deptinfo.setDeptId(ParamUtil.DEPT + deptinfo.getDeptId());
                int flag = sysDeptinfoService.validataDeptId(deptinfo.getDeptId());
                if(flag<=0) {
                    deptinfo.setCreateTime(new Date());//创建时间
                    deptinfo.setCreateUser(sessionUser.getUserId());//创建人
                    deptinfo.setCreateUserName(sessionUser.getName());// 创建人姓名

                    sysDeptinfoService.saveDeptInfo(deptinfo);
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
     * 编辑部门信息
     * @param deptinfo
     * @param request
     * @return
     */
    @PostMapping("/editDeptInfo")
    @ResponseBody
    public RetResult<Map> editDeptInfo(@RequestBody SysDeptinfo deptinfo, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        try {
            // 获取当前登陆人信息
            SysUsers sessionUser = sessionValue.getSessionUser(request);
            if(sessionUser!=null && sessionUser.getUserId() != null &&
                    !sessionUser.getUserId().equals("")) {

                deptinfo.setUpdateTime(new Date());//修改时间
                deptinfo.setUpdateUser(sessionUser.getUserId());//修改人
                deptinfo.setCreateUserName(sessionUser.getName());// 修改人姓名

                sysDeptinfoService.editDeptInfo(deptinfo);
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
