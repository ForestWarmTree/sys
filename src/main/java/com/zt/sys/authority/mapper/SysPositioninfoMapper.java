package com.zt.sys.authority.mapper;

import com.zt.sys.authority.entity.SysPositioninfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jobob
 * @since 2020-02-17
 */
public interface SysPositioninfoMapper extends BaseMapper<SysPositioninfo> {
    /**
     * 验证岗位编码是否存在
     * @param positionId
     * @return
     */
    int validataPositionId(String positionId);

    /**
     * 新增岗位信息
     * @param sysPositioninfo
     */
    void savePositionInfo(SysPositioninfo sysPositioninfo);

    /**
     * 编辑岗位信息
     * @param sysPositioninfo
     */
    void editPositionInfo(SysPositioninfo sysPositioninfo);
}
