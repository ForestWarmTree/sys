package com.zt.sys.authority.controller;


import com.github.pagehelper.PageInfo;
import com.zt.sys.authority.core.RetResponse;
import com.zt.sys.authority.core.RetResult;
import com.zt.sys.authority.entity.SysGroupinfo;
import com.zt.sys.authority.entity.SysOrginfo;
import com.zt.sys.authority.entity.SysRoleinfo;
import com.zt.sys.authority.entity.SysUsers;
import com.zt.sys.authority.logutil.BaseLogger;
import com.zt.sys.authority.service.ISysGroupinfoService;
import com.zt.sys.authority.service.ISysOrginfoService;
import com.zt.sys.authority.utils.HttpSessionValue;
import com.zt.sys.authority.utils.ParamUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * <p>
 * 组织基础信息表 前端控制器
 * </p>
 *
 * @author jobob
 * @since 2020-02-17
 */
@RestController
@RequestMapping("/orgInfo")
public class SysOrginfoController extends BaseLogger {

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
            SysUsers sessionUser = sessionValue.getSessionUserAuth(request);
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
            logerror("错误信息："+e.toString());
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
            SysUsers sessionUser = sessionValue.getSessionUserAuth(request);
            if(sessionUser!=null && sessionUser.getUserId() != null &&
                    !sessionUser.getUserId().equals("")) {

                    orginfo.setUpdateTime(new Date());//修改时间
                    orginfo.setUpdateUser(sessionUser.getUserId());//修改人

                    orginfoService.editOrgInfo(orginfo);
            } else {
                return RetResponse.makeErrRsp("登陆时间过期!请重新登陆");
            }
        } catch (Exception e) {
            logerror("错误信息："+e.toString());
            return RetResponse.makeSysErrRsp();
        }
        return RetResponse.makeOKRsp(result);
    }


    /**
     * 组织列表查询
     * @param orginfo
     * @param request
     * @return
     */
    @PostMapping("/selectOrgList")
    @ResponseBody
    public Map<String, Object> selectOrgList(@RequestBody SysOrginfo orginfo, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        List<SysOrginfo> orginfoList = null;
        try {
            // 获取当前登陆人信息
            SysUsers sessionUser = sessionValue.getSessionUserAuth(request);
            if(sessionUser!=null && sessionUser.getUserId() != null &&
                    !sessionUser.getUserId().equals("")) {
                // 是超级管理员
                if(sessionUser.getIsSupper().equals("1")) {
                    orginfoList = orginfoService.selectAll(orginfo);
                } else {
                    orginfo.setUserId(sessionUser.getUserId());
                    orginfoList = orginfoService.selectByUserOrgId(orginfo);
                }
                //返回结果集
                PageInfo<SysOrginfo> pageInfo = new PageInfo<>(orginfoList);
                result.put("size",orginfo.getPageSize());
                result.put("current",orginfo.getCurrent());
                result.put("total",pageInfo.getTotal());
                result.put("pages",pageInfo.getPages());
                result.put("records",orginfoList);
            }
        } catch (Exception e) {
            logerror("错误信息："+e.toString());
        }
        return result;
    }



    /**
     * 删除组织信息-物理删除
     * @param orgIds
     * @param request
     * @return
     */
    @PostMapping("/deleteOrg")
    @ResponseBody
    public RetResult<Map> deleteOrg(@RequestBody List<String> orgIds, HttpServletRequest request) {
        try {
            // 获取当前登陆人信息
            SysUsers sessionUser = sessionValue.getSessionUserAuth(request);
            if(sessionUser!=null && sessionUser.getUserId()!=null &&
                    !sessionUser.getUserId().equals("")) {
                SysOrginfo sysOrginfo = new SysOrginfo();
                sysOrginfo.setCreateUser(sessionUser.getUserId());//操作人
                sysOrginfo.setCreateTime(new Date());//操作时间
                sysOrginfo.setCreateUserName(sessionUser.getName());//操作人姓名
                sysOrginfo.setOrgIds(orgIds);
                orginfoService.deleteOrgs(sysOrginfo);
            } else {
                return RetResponse.makeErrRsp("登陆已过期!请重新登陆");
            }
        } catch (Exception e) {
            logerror("错误信息："+e.toString());
            return  RetResponse.makeSysErrRsp();
        }
        return RetResponse.makeRsp(200,"操作成功!");
    }

}
