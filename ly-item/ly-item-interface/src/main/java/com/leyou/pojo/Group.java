package com.leyou.pojo;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

/**
 * @date 2019/1/7-21:07
 */
@Table(name="tb_spec_group")
@Data()
public class Group {
	@Id
	@KeySql(useGeneratedKeys = true)
	private Long id;
	private Long cid;
	private String name;
	@Transient
	private List<Param> params;

}