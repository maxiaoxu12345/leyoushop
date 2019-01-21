package com.leyou.common.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @date 2019/1/4-10:53
 */
@Data
public class PageResult<T> {
	private Long total;
	private  Integer totalPage;
	private List<T> items;

	public PageResult(Long total, List<T> items) {
		this.total = total;
		this.items = items;
	}

	public PageResult() {
	}

	public PageResult(Long total, Integer totalPage, List<T> items) {
		this.total = total;
		this.totalPage = totalPage;
		this.items = items;
	}
}
