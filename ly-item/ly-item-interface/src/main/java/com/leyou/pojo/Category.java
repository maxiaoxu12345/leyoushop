package com.leyou.pojo;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

/**
 * @date 2018/12/28-8:22
 */
@Table(name="tb_category")
@Data()
public class Category {
	@Id
	@KeySql(useGeneratedKeys = true)
	private Long id;
	private String name;
	private  Long parent_id;
	private Boolean is_parent;
	private Integer sort;

	private Boolean isParent;

}
