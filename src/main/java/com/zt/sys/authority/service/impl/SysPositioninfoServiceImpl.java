package com.zt.sys.authority.service.impl;

import com.zt.sys.authority.entity.SysPositioninfo;
import com.zt.sys.authority.entity.SysRolelog;
import com.zt.sys.authority.mapper.SysPositioninfoMapper;
import com.zt.sys.authority.mapper.SysRolelogMapper;
import com.zt.sys.authority.service.ISysPositioninfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zt.sys.authority.utils.ParamUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jobob
 * @since 2020-02-17
 */
@Service
public class SysPositioninfoServiceImpl extends ServiceImpl<SysPositioninfoMapper, SysPositioninfo> implements ISysPositioninfoService {

    @Resource
    private SysPositioninfoMapper positioninfoMapper;

    @Resource
    private SysRolelogMapper sysRolelogMapper;

    /**
     * 验证岗位信息是否存在
     * @param positionId
     * @return
     */
    @Override
    public int validataPositionId(String positionId) {
        return positioninfoMapper.validataPositionId(positionId);
    }

    /**
     * 新增岗位信息
     * @param sysPositioninfo
     */
    @Override
    public void savePositionInfo(SysPositioninfo sysPositioninfo) {
        SysRolelog sysRolelog = new SysRolelog();
        sysRolelog.setResourceId(sysPositioninfo.getPositionNo());//
        sysRolelog.setSourceName(sysPositioninfo.getPositionName());
        sysRolelog.setUpdateType(ParamUtil.INSERT);//变更类型
        sysRolelog.setUpdateTypeTips(ParamUtil.LogSavePosition);//变更类型业务描述
        sysRolelog.setSysUser(sysPositioninfo.getCreateUser());//创建人
        sysRolelog.setSysUserName(sysPositioninfo.getCreateUserName());//创建人姓名
        sysRolelog.setSysTime(new Date());//创建时间

        //保存日志
        sysRolelogMapper.saveLog(sysRolelog);
        //保存岗位信息
        positioninfoMapper.savePositionInfo(sysPositioninfo);
    }

    /**
     * 编辑岗位信息
     * @param sysPositioninfo
     */
    @Override
    public void editPositionInfo(SysPositioninfo sysPositioninfo) {
        SysRolelog sysRolelog = new SysRolelog();
        sysRolelog.setResourceId(sysPositioninfo.getPositionNo());//
        sysRolelog.setSourceName(sysPositioninfo.getPositionName());
        sysRolelog.setUpdateType(ParamUtil.UPDATE);//变更类型
        sysRolelog.setUpdateTypeTips(ParamUtil.LogSavePosition);//变更类型业务描述
        sysRolelog.setSysUser(sysPositioninfo.getUpdateUser());//创建人
        sysRolelog.setSysUserName(sysPositioninfo.getCreateUserName());//创建人姓名
        sysRolelog.setSysTime(new Date());//创建时间

        //保存日志
        sysRolelogMapper.saveLog(sysRolelog);
        //修改岗位信息
        positioninfoMapper.editPositionInfo(sysPositioninfo);
    }
}
