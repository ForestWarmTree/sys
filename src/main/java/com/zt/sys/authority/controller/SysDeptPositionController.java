package com.zt.sys.authority.controller;


import com.zt.sys.authority.core.RetResponse;
import com.zt.sys.authority.core.RetResult;
import com.zt.sys.authority.entity.SysDeptPosition;
import com.zt.sys.authority.entity.SysGroupinfo;
import com.zt.sys.authority.entity.SysUsers;
import com.zt.sys.authority.service.ISysDeptPositionService;
import com.zt.sys.authority.utils.HttpSessionValue;
import com.zt.sys.authority.utils.ParamUtil;
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
@RequestMapping("/authority/sys-dept-position")
public class SysDeptPositionController {

    @Resource
    private HttpSessionValue sessionValue;

    @Resource
    private ISysDeptPositionService sysDeptPositionService;

    /**
     * 新增部门与岗位对应关系
     * @param sysDeptPosition
     * @param request
     * @return
     */
    @PostMapping("/saveDeptPosition")
    @ResponseBody
    public RetResult<Map> saveGroupInfo(@RequestBody SysDeptPosition sysDeptPosition,
                                        HttpServletRequest request) {
        try {
            // 获取当前登陆人信息
            SysUsers sessionUser = sessionValue.getSessionUserAuth(request);
            if(sessionUser!=null && sessionUser.getUserId() != null &&
                    !sessionUser.getUserId().equals("")) {

                sysDeptPosition.setCreateUser(sessionUser.getCreateUser());
                sysDeptPosition.setCreateTime(new Date());
                sysDeptPosition.setCreateUserName(sessionUser.getName());
                // 保存
                sysDeptPositionService.saveDeptPosition(sysDeptPosition);

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
