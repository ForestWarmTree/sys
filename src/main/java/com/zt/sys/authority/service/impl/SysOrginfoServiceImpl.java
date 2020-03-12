package com.zt.sys.authority.service.impl;

import com.zt.sys.authority.entity.SysOrginfo;
import com.zt.sys.authority.entity.SysRolelog;
import com.zt.sys.authority.mapper.SysOrginfoMapper;
import com.zt.sys.authority.mapper.SysRolelogMapper;
import com.zt.sys.authority.service.ISysOrginfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zt.sys.authority.utils.ParamUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

/**
 * <p>
 * 组织基础信息表 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2020-02-17
 */
@Service
public class SysOrginfoServiceImpl extends ServiceImpl<SysOrginfoMapper, SysOrginfo> implements ISysOrginfoService {

    @Resource
    private SysOrginfoMapper orginfoMapper;

    @Resource
    private SysRolelogMapper logMapper;

    /**
     * 编辑部门信息
     * @param sysOrginfo
     */
    @Transactional
    @Override
    public void editOrgInfo(SysOrginfo sysOrginfo) {
        SysRolelog sysRolelog = new SysRolelog();
        sysRolelog.setResourceId(sysOrginfo.getOrgId());//
        sysRolelog.setSourceName(sysOrginfo.getOrgName());
        sysRolelog.setUpdateType(ParamUtil.UPDATE);//变更类型
        sysRolelog.setUpdateTypeTips(ParamUtil.LogSaveOrg);//变更类型业务描述
        sysRolelog.setSysUser(sysOrginfo.getUpdateUser());
        sysRolelog.setSysTime(new Date());
        sysRolelog.setSysUserName(sysOrginfo.getCreateUserName());//创建人姓名
        //保存日志
        logMapper.saveLog(sysRolelog);
        //修改组织
        orginfoMapper.editOrgInfo(sysOrginfo);
    }

    /**
     * 新增部门信息
     * @param sysOrginfo
     */
    @Transactional
    @Override
    public void saveOrgInfo(SysOrginfo sysOrginfo) {
        SysRolelog sysRolelog = new SysRolelog();
        sysRolelog.setResourceId(sysOrginfo.getOrgId());//被变更组织编码
        sysRolelog.setSourceName(sysOrginfo.getOrgName());//被变更组织名称
        sysRolelog.setUpdateType(ParamUtil.INSERT);//变更类型
        sysRolelog.setUpdateTypeTips(ParamUtil.LogSaveOrg);//变更类型业务描述
        sysRolelog.setSysUser(sysOrginfo.getCreateUser());//创建人
        sysRolelog.setSysTime(new Date());//创建时间
        sysRolelog.setSysUserName(sysOrginfo.getCreateUserName());//创建人姓名
        //保存日志
        logMapper.saveLog(sysRolelog);
        //新增组织
        orginfoMapper.saveOrgInfo(sysOrginfo);
    }

    /**
     * 验证组织ID是否存在
     * @param orgId
     * @return
     */
    @Override
    public int validataOrgId(String orgId) {
        return orginfoMapper.validataOrgId(orgId);
    }
}
