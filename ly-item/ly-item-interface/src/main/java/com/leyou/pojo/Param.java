package com.leyou.pojo;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @date 2019/1/7-22:06
 */
@Table(name="tb_spec_param")
@Data()
public class Param {
	@Id
	@KeySql(useGeneratedKeys = true)
	private Long id;
	private Long cid;
	private Long group_id;
	private String name;
	@Column(name = "`numeric`")
	private Boolean numeric;
	private String unit;
	private Boolean generic;
	private Boolean searching;
	private String segments;
}