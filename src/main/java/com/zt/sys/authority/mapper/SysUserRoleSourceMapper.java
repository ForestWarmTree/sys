package com.zt.sys.authority.mapper;

import com.zt.sys.authority.entity.SysUserRoleSource;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.Map;

/**
 * <p>
 * 用户角色资源关系关联表 Mapper 接口
 * </p>
 *
 * @author jobob
 * @since 2020-02-17
 */
public interface SysUserRoleSourceMapper extends BaseMapper<SysUserRoleSource> {

    /**
     * 新增用户角色资源对应关系
     * @param map
     */
    void saveUserRoleResource(Map<String, Object> map);
}
