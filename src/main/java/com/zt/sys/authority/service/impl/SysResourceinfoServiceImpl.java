package com.zt.sys.authority.service.impl;

import com.zt.sys.authority.entity.*;
import com.zt.sys.authority.mapper.SysResourceinfoMapper;
import com.zt.sys.authority.mapper.SysRoleinfoMapper;
import com.zt.sys.authority.mapper.SysRolelogMapper;
import com.zt.sys.authority.service.ISysResourceinfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zt.sys.authority.utils.ParamUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 资源基础信息表 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2020-02-17
 */
@Service
public class SysResourceinfoServiceImpl extends ServiceImpl<SysResourceinfoMapper, SysResourceinfo> implements ISysResourceinfoService {

    @Resource
    private SysResourceinfoMapper sysResourceinfoMapper;

    @Resource
    private SysRolelogMapper sysRolelogMapper;

    @Resource
    private SysRoleinfoMapper sysRoleinfoMapper;

    /**
     * 保存资源信息
     * @param sysResourceinfo
     */
    @Transactional
    @Override
    public void saveResource(SysResourceinfo sysResourceinfo) {
        SysRolelog sysRolelog = new SysRolelog();
        sysRolelog.setResourceId(sysResourceinfo.getResourceId());//被创建资源ID
        sysRolelog.setSourceName(sysResourceinfo.getPermissionName());//被创建资源名称
        sysRolelog.setSysUser(sysResourceinfo.getCreateUser());//创建人ID
        sysRolelog.setSysTime(new Date());//创建时间
        sysRolelog.setSysUserName(sysResourceinfo.getCreateUserName());//创建人姓名
        sysRolelog.setUpdateType(ParamUtil.INSERT);//变更类型
        sysRolelog.setUpdateTypeTips(ParamUtil.LogSaveResource);//变更类型业务描述
        //保存日志
        sysRolelogMapper.saveLog(sysRolelog);
        //保存资源
        sysResourceinfoMapper.saveResource(sysResourceinfo);
    }

    /**
     * 编辑资源信息
     * @param sysResourceinfo
     */
    @Transactional
    @Override
    public void editResource(SysResourceinfo sysResourceinfo) {
        SysRolelog sysRolelog = new SysRolelog();
        sysRolelog.setResourceId(sysResourceinfo.getResourceId());//被创建资源ID
        sysRolelog.setSourceName(sysResourceinfo.getPermissionName());//被创建资源名称
        sysRolelog.setSysUser(sysResourceinfo.getUpdateUser());//创建人ID
        sysRolelog.setSysTime(new Date());//创建时间
        sysRolelog.setSysUserName(sysResourceinfo.getCreateUserName());//创建人姓名
        sysRolelog.setUpdateType(ParamUtil.UPDATE);//变更类型
        sysRolelog.setUpdateTypeTips(ParamUtil.LogSaveResource);//变更类型业务描述
        //保存日志
        sysRolelogMapper.saveLog(sysRolelog);
        //修改资源
        sysResourceinfoMapper.editResource(sysResourceinfo);
    }

    /**
     * 根据用户ID获取所有资源
     * @param sysUsers
     * @return
     */
    @Override
    public List<SysResourceinfo> selectResourceByUserId(SysUsers sysUsers) {
        return sysResourceinfoMapper.selectResourceByUserId(sysUsers);
    }

    /**
     * 资源列表查询
     * @param sysResourceinfo
     * @return
     */
    @Override
    public List<SysResourceinfo> selectResourceList(SysResourceinfo sysResourceinfo) {
        return sysResourceinfoMapper.selectResourceList(sysResourceinfo);
    }

    /**
     * 根据角色ID返回该角色对应的资源列表
     * @param sysRoleinfo
     * @return
     */
    @Override
    public List<SysResourceinfo> selectResourceByRoleId(SysRoleinfo sysRoleinfo) {
        return sysResourceinfoMapper.selectResourceByRoleId(sysRoleinfo);
    }

    /**
     * 验证资源编码是否重复
     * @param resourceId
     * @return
     */
    @Override
    public int validataResourceId(String resourceId) {
        return sysResourceinfoMapper.validataResourceId(resourceId);
    }

    /**
     * 资源列表查询
     * @param sysResourceinfo
     * @return
     */
    @Override
    public List<SysResourceinfo> selectAll(SysResourceinfo sysResourceinfo) {
        return sysResourceinfoMapper.selectAll(sysResourceinfo);
    }

    @Override
    public void deleteResources(List<SysResourceinfo> sysResourceinfos, SysUsers sessionUser) {
        for(SysResourceinfo resourceInfo:sysResourceinfos) {
            SysRolelog sysRolelog = new SysRolelog();
            sysRolelog.setResourceId(resourceInfo.getResourceId());//被操作资源ID
            sysRolelog.setSourceName(resourceInfo.getPermissionName());//被操作资源名称
            sysRolelog.setSysUser(sessionUser.getUserId());//创建人ID
            sysRolelog.setSysTime(new Date());//创建时间
            sysRolelog.setSysUserName(sessionUser.getName());//创建人姓名
            sysRolelog.setUpdateType(ParamUtil.DELETE);//变更类型
            sysRolelog.setUpdateTypeTips(ParamUtil.LogSaveResource);//变更类型业务描述
            //保存日志
            sysRolelogMapper.saveLog(sysRolelog);
            //删除
            sysResourceinfoMapper.deleteResources(resourceInfo);
        }
    }

    /**
     * 根据资源ID查询资源详情
     * @param sysResourceinfo
     * @return
     */
    @Override
    public SysResourceinfo selectResourceInfoByResourceId(SysResourceinfo sysResourceinfo) {
        // 查询资源详情
        SysResourceinfo resInfo = sysResourceinfoMapper.selectResourceInfoByResourceId(sysResourceinfo.getResourceId());
        //根据资源ID查询已分配得角色
        List<SysRoleinfo> roles = sysRoleinfoMapper.selectRoleByResourceId(sysResourceinfo.getResourceId());
        resInfo.setRoleinfos(roles);
        return resInfo;
    }


}
