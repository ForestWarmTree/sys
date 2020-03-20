package com.zt.sys.authority.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Data
public class BaseModel {


	private long shardValue = 1L; //0L 外部库；1L 内部库

	/**
	 * 排序方式
	 */
	private String orderDirection = "desc";



	// 每页数据条数
	private int pageSize = 10;

	// 当前检索条件下的总数据条数
	private int resultCount;

	// 页码
    private int current = 1;
    // 当前日期
    private Date nowDate;
	private String startDate;
	private String endDate;

	//是否已被分配用户组 0/1，非DB字段。仅供前台组件使用
	private String isChoose;
    //
    private boolean editable = false;

	private String totalMappedStatementId;


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
