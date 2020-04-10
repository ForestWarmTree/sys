package com.zt.sys.authority.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.zt.sys.authority.logutil.BaseLogger;
import lombok.Data;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Data
public class BaseModel{


	@TableField(exist = false)
	private long shardValue = 1L; //0L 外部库；1L 内部库

	/**
	 * 排序方式
	 */
	@TableField(exist = false)
	private String orderDirection = "desc";

	// 每页数据条数
	@TableField(exist = false)
	private int pageSize = 10;

	// 当前检索条件下的总数据条数
	@TableField(exist = false)
	private int resultCount;

	// 页码
	@TableField(exist = false)
    private int current = 1;
    // 当前日期
	@TableField(exist = false)
    private Date nowDate;
	@TableField(exist = false)
	private String startDate;
	@TableField(exist = false)
	private String endDate;

	//是否已被分配用户组 0/1，非DB字段。仅供前台组件使用
	@TableField(exist = false)
	private String isChoose = "0";
    //
	@TableField(exist = false)
    private boolean editable = false;

	@TableField(exist = false)
	private String totalMappedStatementId;
	// 多变参数ID，非DB字段
	@TableField(exist = false)
	private String paramId;


	@TableField(exist = false)
	private List<String> createTimes;

	public String getStartDate() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {
			if(startDate != null) {
				startDate = df.format(df.parse(startDate));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return startDate;
	}

	public String getEndDate() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {
			if(endDate != null) {
				endDate = df.format(df.parse(endDate));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return endDate;
	}
}
