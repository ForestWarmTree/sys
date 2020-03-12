package com.zt.sys.authority.mapper;

import com.zt.sys.authority.entity.SysUsersgroup;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jobob
 * @since 2020-02-17
 */
public interface SysUsersgroupMapper extends BaseMapper<SysUsersgroup> {

    /**
     * 新增用户组与用户对应关系
     */
    void saveUsersGroup(Map<String, Object> map);

    /**
     * 删除用户组与用户对应关系
     */
    void delete(SysUsersgroup sysUsersgroup);

    /**
     * 根据组ID查询用户ID
     * @param groupId
     */
    List<String> selectUserIdsByGroupId(String groupId);
}
