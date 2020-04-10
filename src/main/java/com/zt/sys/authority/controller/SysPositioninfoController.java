package com.zt.sys.authority.controller;


import com.github.pagehelper.PageInfo;
import com.zt.sys.authority.core.RetResponse;
import com.zt.sys.authority.core.RetResult;
import com.zt.sys.authority.entity.SysDeptinfo;
import com.zt.sys.authority.entity.SysOrginfo;
import com.zt.sys.authority.entity.SysPositioninfo;
import com.zt.sys.authority.entity.SysUsers;
import com.zt.sys.authority.logutil.BaseLogger;
import com.zt.sys.authority.service.ISysOrginfoService;
import com.zt.sys.authority.service.ISysPositioninfoService;
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
 *
 * @author jobob
 * @since 2020-02-17
 */
@RestController
@RequestMapping("/position")
public class SysPositioninfoController extends BaseLogger {

    @Resource
    private HttpSessionValue sessionValue;

    @Resource
    private ISysPositioninfoService sysPositioninfoService;
    /**
     * 新增岗位信息
     * @return
     */
    @PostMapping("/savePositionInfo")
    @ResponseBody
    public RetResult<Map> savePositionInfo(@RequestBody SysPositioninfo positioninfo,
                                           HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        try {
            // 获取当前登陆人信息
            SysUsers sessionUser = sessionValue.getSessionUserAuth(request);
            if(sessionUser!=null && sessionUser.getUserId() != null &&
                    !sessionUser.getUserId().equals("")) {
                // 验证岗位编码是否存在
                positioninfo.setPositionNo(ParamUtil.POSITION + positioninfo.getPositionNo());
                int flag = sysPositioninfoService.validataPositionId(positioninfo.getPositionNo());
                if(flag<=0) {
                    positioninfo.setCreateTime(new Date());//创建时间
                    positioninfo.setCreateUser(sessionUser.getUserId());//创建人
                    positioninfo.setCreateUserName(sessionUser.getName());//创建人姓名

                    sysPositioninfoService.savePositionInfo(positioninfo);
                } else {
                    return RetResponse.makeErrRsp("岗位编码重复!");
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
     * 编辑岗位信息
     * @param positioninfo
     * @param request
     * @return
     */
    @PostMapping("/editPosition")
    @ResponseBody
    public RetResult<Map> editPosition(@RequestBody SysPositioninfo positioninfo, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        try {
            // 获取当前登陆人信息
            SysUsers sessionUser = sessionValue.getSessionUserAuth(request);
            if(sessionUser!=null && sessionUser.getUserId() != null &&
                    !sessionUser.getUserId().equals("")) {

                positioninfo.setUpdateTime(new Date());//修改时间
                positioninfo.setUpdateUser(sessionUser.getUserId());//修改人
                positioninfo.setCreateUserName(sessionUser.getName());//修改人姓名

                sysPositioninfoService.editPositionInfo(positioninfo);
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
     * 岗位列表查询
     * @param sysPositioninfo
     * @param request
     * @return
     */
    @PostMapping("/selectPositionList")
    @ResponseBody
    public Map<String, Object> selectPositionList(@RequestBody SysPositioninfo sysPositioninfo,
                                              HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        List<SysPositioninfo> positioninfoList = null;
        try {
            // 获取当前登陆人信息
            SysUsers sessionUser = sessionValue.getSessionUserAuth(request);
            if(sessionUser!=null && sessionUser.getUserId() != null &&
                    !sessionUser.getUserId().equals("")) {

                //列表查询
                positioninfoList = sysPositioninfoService.selectPositionList(sysPositioninfo);

                //返回结果集
                PageInfo<SysPositioninfo> pageInfo = new PageInfo<>(positioninfoList);
                result.put("size",sysPositioninfo.getPageSize());
                result.put("current",sysPositioninfo.getCurrent());
                result.put("total",pageInfo.getTotal());
                result.put("pages",pageInfo.getPages());
                result.put("records",positioninfoList);
            }
        } catch (Exception e) {
            logerror("错误信息："+e.toString());
        }
        return result;
    }

    /**
     * 删除岗位信息
     * @param positioninfos
     * @param request
     * @return
     */
    @PostMapping("/deletePosition")
    @ResponseBody
    public RetResult<Map> deletePosition(@RequestBody List<SysPositioninfo> positioninfos,
                                     HttpServletRequest request) {
        try {
            // 获取当前登陆人信息
            SysUsers sessionUser = sessionValue.getSessionUserAuth(request);
            if(sessionUser!=null && sessionUser.getUserId()!=null && !sessionUser.getUserId().equals("")) {
                //删除
                sysPositioninfoService.deletePosition(positioninfos, sessionUser);
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
