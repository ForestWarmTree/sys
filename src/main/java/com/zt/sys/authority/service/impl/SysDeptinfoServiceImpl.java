package com.zt.sys.authority.service.impl;

import com.zt.sys.authority.entity.SysDeptinfo;
import com.zt.sys.authority.entity.SysRolelog;
import com.zt.sys.authority.mapper.SysDeptPositionMapper;
import com.zt.sys.authority.mapper.SysDeptinfoMapper;
import com.zt.sys.authority.mapper.SysRolelogMapper;
import com.zt.sys.authority.service.ISysDeptinfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zt.sys.authority.utils.ParamUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public class SysDeptinfoServiceImpl extends ServiceImpl<SysDeptinfoMapper, SysDeptinfo> implements ISysDeptinfoService {

    @Resource
    private SysDeptinfoMapper deptinfoMapper;

    @Resource
    private SysRolelogMapper sysRolelogMapper;

    /**
     * 验证部门编码是否存在
     * @param deptId
     * @return
     */
    @Override
    public int validataDeptId(String deptId) {
        return deptinfoMapper.validataDeptId(deptId);
    }

    /**
     * 新增部门信息
     * @param sysDeptinfo
     */
    @Transactional
    @Override
    public void saveDeptInfo(SysDeptinfo sysDeptinfo) {
        SysRolelog sysRolelog = new SysRolelog();
        sysRolelog.setResourceId(sysDeptinfo.getDeptId());//被新增部门ID
        sysRolelog.setSourceName(sysDeptinfo.getDeptName());//被新增部门名称
        sysRolelog.setUpdateType(ParamUtil.INSERT);//变更类型
        sysRolelog.setUpdateTypeTips(ParamUtil.LogSaveDept);//变更类型业务描述
        sysRolelog.setSysUser(sysDeptinfo.getCreateUser());//创建人
        sysRolelog.setSysTime(new Date());//创建时间
        sysRolelog.setSysUserName(sysDeptinfo.getCreateUserName());//创建人姓名

        //保存日志
        sysRolelogMapper.saveLog(sysRolelog);
        //保存部门信息
        deptinfoMapper.saveDeptInfo(sysDeptinfo);
    }

    /**
     * 编辑部门信息
     * @param sysDeptinfo
     */
    @Transactional
    @Override
    public void editDeptInfo(SysDeptinfo sysDeptinfo) {
        SysRolelog sysRolelog = new SysRolelog();
        sysRolelog.setResourceId(sysDeptinfo.getDeptId());//被新增部门ID
        sysRolelog.setSourceName(sysDeptinfo.getDeptName());//被新增部门名称
        sysRolelog.setUpdateType(ParamUtil.UPDATE);//变更类型
        sysRolelog.setUpdateTypeTips(ParamUtil.LogSaveDept);//变更类型业务描述
        sysRolelog.setSysUser(sysDeptinfo.getUpdateUser());//创建人
        sysRolelog.setSysTime(new Date());//创建时间
        sysRolelog.setSysUserName(sysDeptinfo.getCreateUserName());//创建人姓名

        //保存日志
        sysRolelogMapper.saveLog(sysRolelog);
        //修改部门信息
        deptinfoMapper.editDeptInfo(sysDeptinfo);
    }
}
