package com.zt.sys.authority.controller;


import com.github.pagehelper.PageInfo;
import com.zt.sys.authority.core.RetResponse;
import com.zt.sys.authority.core.RetResult;
import com.zt.sys.authority.entity.SysDeptinfo;
import com.zt.sys.authority.entity.SysOrginfo;
import com.zt.sys.authority.entity.SysRoleinfo;
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
import java.util.List;
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
@RequestMapping("/dept")
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
                    return RetResponse.makeErrRsp("部门编码重复!");
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


    /**
     * 部门列表查询
     * @param sysDeptinfo
     * @param request
     * @return
     */
    @PostMapping("/selectDeptList")
    @ResponseBody
    public Map<String, Object> selectDeptList(@RequestBody SysDeptinfo sysDeptinfo,
                                                  HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        List<SysDeptinfo> deptinfoList = null;
        try {
            // 获取当前登陆人信息
            SysUsers sessionUser = sessionValue.getSessionUser(request);
            if(sessionUser!=null && sessionUser.getUserId() != null &&
                    !sessionUser.getUserId().equals("")) {

                //是超级管理员
                if(sessionUser.getIsSupper().equals("1")) {
                    deptinfoList = sysDeptinfoService.selectDept(sysDeptinfo);
                } else {
                    // 根据当前登陆人的所属组织，查询部门信息
                    sysDeptinfo.setOrgId(sessionUser.getSysUserinfo().getOrgId());
                    deptinfoList = sysDeptinfoService.selectDept(sysDeptinfo);
                }
                //返回结果集
                PageInfo<SysDeptinfo> pageInfo = new PageInfo<>(deptinfoList);
                result.put("size",sysDeptinfo.getPageSize());
                result.put("current",sysDeptinfo.getCurrent());
                result.put("total",pageInfo.getTotal());
                result.put("pages",pageInfo.getPages());
                result.put("records",deptinfoList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }



    /**
     * 删除部门信息
     * @param sysDeptinfos
     * @param request
     * @return
     */
    @PostMapping("/deleteDept")
    @ResponseBody
    public RetResult<Map> deleteDept(@RequestBody List<SysDeptinfo> sysDeptinfos,
                                      HttpServletRequest request) {
        try {
            // 获取当前登陆人信息
            SysUsers sessionUser = sessionValue.getSessionUser(request);
            if(sessionUser!=null && sessionUser.getUserId()!=null && !sessionUser.getUserId().equals("")) {
                //删除
                sysDeptinfoService.deleteByDeptId(sysDeptinfos, sessionUser);
            } else {
                return RetResponse.makeErrRsp("登陆已过期!请重新登陆");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return  RetResponse.makeSysErrRsp();
        }
        return RetResponse.makeRsp(200,"操作成功!");
    }


}
