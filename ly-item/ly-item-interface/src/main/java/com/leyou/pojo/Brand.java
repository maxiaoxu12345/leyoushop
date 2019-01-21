package com.leyou.pojo;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @date 2019/1/4-10:42
 */
@Table(name="tb_brand")
@Data()
public class Brand {
	@Id
	@KeySql(useGeneratedKeys = true)
	private Long id;
	private String name;
	private  String image;
	private Character letter;
}
