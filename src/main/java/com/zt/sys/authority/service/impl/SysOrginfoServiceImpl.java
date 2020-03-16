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
import java.util.List;

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


    @Resource
    private SysRolelogMapper sysRolelogMapper;

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

    /**
     * 根据检索条件查询所有组织信息
     * @param sysOrginfo
     * @return
     */
    @Override
    public List<SysOrginfo> selectAll(SysOrginfo sysOrginfo) {
        return orginfoMapper.selectAll(sysOrginfo);
    }

    /**
     * 根据当前登陆人的组织，查询该组织与该组织下的子级组织
     * @param sysOrginfo
     * @return
     */
    @Override
    public List<SysOrginfo> selectByUserOrgId(SysOrginfo sysOrginfo) {
        return orginfoMapper.selectByUserOrgId(sysOrginfo);
    }

    /**
     * 删除组织信息
     * @param sysOrginfo
     */
    @Transactional
    @Override
    public void deleteOrgs(SysOrginfo sysOrginfo) {
        for(String orgId:sysOrginfo.getOrgIds()) {
            //记录log日志
            SysRolelog sysRolelog = new SysRolelog();
            sysRolelog.setResourceId(orgId);//组织ID
            sysRolelog.setUpdateType(ParamUtil.DELETE);//变更类型
            sysRolelog.setUpdateTypeTips(ParamUtil.LogSaveOrg);//变更类型业务描述
            sysRolelog.setSysTime(sysOrginfo.getCreateTime());//创建时间
            sysRolelog.setSysUser(sysOrginfo.getCreateUser());//创建人id
            sysRolelog.setSysUserName(sysOrginfo.getCreateUserName());//创建人姓名
            //保存日志
            sysRolelogMapper.saveLog(sysRolelog);
            //删除组织基础信息
            orginfoMapper.deleteOrgs(orgId);
        }
    }
}
