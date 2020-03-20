package com.zt.sys.authority.service.impl;

import com.zt.sys.authority.entity.SysGroupinfo;
import com.zt.sys.authority.entity.SysRolelog;
import com.zt.sys.authority.entity.SysUsers;
import com.zt.sys.authority.mapper.SysGroupinfoMapper;
import com.zt.sys.authority.mapper.SysRolelogMapper;
import com.zt.sys.authority.service.ISysGroupinfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zt.sys.authority.utils.ParamUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jobob
 * @since 2020-02-17
 */
@Service
public class SysGroupinfoServiceImpl extends ServiceImpl<SysGroupinfoMapper, SysGroupinfo> implements ISysGroupinfoService {

    @Resource
    private SysGroupinfoMapper sysGroupinfoMapper;

    @Resource
    private SysRolelogMapper sysRolelogMapper;

    /**
     * 新增用户组信息
     * @param sysGroupinfo
     */
    @Transactional
    @Override
    public void saveGroupInfo(SysGroupinfo sysGroupinfo) {
        SysRolelog sysRolelog = new SysRolelog();
        sysRolelog.setResourceId(sysGroupinfo.getGroupId());//被变更组织编码
        sysRolelog.setSourceName(sysGroupinfo.getGroupName());//被变更组织名称
        sysRolelog.setUpdateType(ParamUtil.INSERT);//变更类型
        sysRolelog.setUpdateTypeTips(ParamUtil.LogSaveGroup);//变更类型业务描述
        sysRolelog.setSysUser(sysGroupinfo.getCreateUser());//创建人
        sysRolelog.setSysTime(new Date());//创建时间
        sysRolelog.setSysUserName(sysGroupinfo.getCreateUserName());//创建人姓名
        //保存日志
        sysRolelogMapper.saveLog(sysRolelog);
        //保存用户组
        sysGroupinfoMapper.saveGroupInfo(sysGroupinfo);
    }

    /**
     * 编辑用户组信息
     * @param sysGroupinfo
     */
    @Transactional
    @Override
    public void editGroupInfo(SysGroupinfo sysGroupinfo) {
        SysRolelog sysRolelog = new SysRolelog();
        sysRolelog.setResourceId(sysGroupinfo.getGroupId());//被变更组织编码
        sysRolelog.setSourceName(sysGroupinfo.getGroupName());//被变更组织名称
        sysRolelog.setUpdateType(ParamUtil.UPDATE);//变更类型
        sysRolelog.setUpdateTypeTips(ParamUtil.LogSaveGroup);//变更类型业务描述
        sysRolelog.setSysUser(sysGroupinfo.getCreateUser());//创建人
        sysRolelog.setSysTime(new Date());//创建时间
        sysRolelog.setSysUserName(sysGroupinfo.getCreateUserName());//创建人姓名
        //保存日志
        sysRolelogMapper.saveLog(sysRolelog);
        //修改用户组
        sysGroupinfoMapper.editGroupInfo(sysGroupinfo);
    }

    /**
     * 验证组号是否存在
     * @param groupId
     * @return
     */
    @Override
    public int validataGroupId(String groupId) {
        return sysGroupinfoMapper.validataGroupId(groupId);
    }

    /**
     * 用户组列表查询
     * @param sysGroupinfo
     * @return
     */
    @Override
    public List<SysGroupinfo> selectGroupList(SysGroupinfo sysGroupinfo) {
        return sysGroupinfoMapper.selectGroupList(sysGroupinfo);
    }

    /**
     * 删除用户组
     * @param sysGroupinfos
     * @param sessionUser
     */
    @Transactional
    @Override
    public void deleteGroupInfo(List<SysGroupinfo> sysGroupinfos, SysUsers sessionUser) {
        if(sysGroupinfos!=null && sysGroupinfos.size()>0) {
            for(SysGroupinfo groupinfo:sysGroupinfos) {
                SysRolelog sysRolelog = new SysRolelog();
                sysRolelog.setResourceId(groupinfo.getGroupId());//被操作组编码
                sysRolelog.setUpdateType(ParamUtil.DELETE);//变更类型
                sysRolelog.setUpdateTypeTips(ParamUtil.LogSaveGroup);//变更类型业务描述
                sysRolelog.setSysUser(sessionUser.getCreateUser());//创建人
                sysRolelog.setSysTime(new Date());//创建时间
                sysRolelog.setSysUserName(sessionUser.getName());//创建人姓名
                //保存日志
                sysRolelogMapper.saveLog(sysRolelog);
                //删除
                sysGroupinfoMapper.deleteGroupInfo(groupinfo);
            }
        }
    }
}
