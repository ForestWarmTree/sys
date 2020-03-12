package com.zt.sys.authority.service.impl;

import com.zt.sys.authority.entity.SysRolelog;
import com.zt.sys.authority.entity.SysUserinfo;
import com.zt.sys.authority.mapper.SysRolelogMapper;
import com.zt.sys.authority.mapper.SysUserinfoMapper;
import com.zt.sys.authority.service.ISysUserinfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zt.sys.authority.utils.ParamUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

/**
 * <p>
 * 用户基础信息表 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2020-02-17
 */
@Service
public class SysUserinfoServiceImpl extends ServiceImpl<SysUserinfoMapper, SysUserinfo> implements ISysUserinfoService {

    @Resource
    private SysUserinfoMapper sysUserinfoMapper;

    @Resource
    private SysRolelogMapper sysRolelogMapper;

    /**
     * 新增用户基础信息
     * @param userinfo
     */
    @Transactional
    @Override
    public void saveUserInfo(SysUserinfo userinfo) {
        //保存日志信息
        SysRolelog sysRolelog = new SysRolelog();
        sysRolelog.setUserId(userinfo.getUserId());//被创建人
        sysRolelog.setSysUserName(userinfo.getName());//被创建人姓名
        sysRolelog.setUpdateType(ParamUtil.INSERT);//变更类型
        sysRolelog.setUpdateTypeTips(ParamUtil.LogSaveUserInfo);//变更类型业务描述
        sysRolelog.setSysUser(userinfo.getCreateUser());//创建人id
        sysRolelog.setSysUserName(userinfo.getCreateUserName());//创建人姓名
        sysRolelog.setSysTime(new Date());//创建时间
        sysRolelogMapper.saveLog(sysRolelog);

        //保存用户基础信息
        sysUserinfoMapper.saveUserInfo(userinfo);
    }

    /**
     * 修改用户基础信息
     * @param userinfo
     */
    @Transactional
    @Override
    public void updateUserInfo(SysUserinfo userinfo) {
        //保存日志信息
        SysRolelog sysRolelog = new SysRolelog();
        sysRolelog.setUserId(userinfo.getUserId());//被创建人
        sysRolelog.setSysUserName(userinfo.getName());//被创建人姓名
        sysRolelog.setUpdateType(ParamUtil.UPDATE);//变更类型
        sysRolelog.setUpdateTypeTips(ParamUtil.LogSaveUserInfo);//变更类型业务描述
        sysRolelog.setSysUser(userinfo.getUpdateUser());//创建人id
        sysRolelog.setSysUserName(userinfo.getCreateUserName());//创建人姓名
        sysRolelog.setSysTime(new Date());//创建时间
        sysRolelogMapper.saveLog(sysRolelog);

        //修改用户基础信息
        sysUserinfoMapper.updateUserInfo(userinfo);
    }

    /**
     * 根据用户ID获取用户基础信息
     * @param userId
     * @return
     */
    @Override
    public SysUserinfo getUserInfoByUserId(String userId) {
        return sysUserinfoMapper.getUserInfoByUserId(userId);
    }
}