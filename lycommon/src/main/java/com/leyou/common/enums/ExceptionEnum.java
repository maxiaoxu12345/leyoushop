package com.leyou.common.enums;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @date 2018/12/27-13:02
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum ExceptionEnum {

	NAME_CONNOT_BE_NOT(400, "名称不能为空"),
	CATEGORY_NOT_FOUND(404, "分类列表为空"),
	BRANDS_NOT_FOUND(200, "品牌列表为空"),
	BRAND_NOT_FOUND(200, "品牌为空"),
	CREATED_DEFAULT(500, "新增失败"),
	UPLOAD_DEFAULT(500, "文件上传失败"),
	GROUP_IS_NOT_FIND(500, "主体没有找到"),
	INVALID_FILE_TYPE(500, "上传文件错误"),
	PARAM_IS_NOT_FIND(500, "参数没有查到"),
	GOODS_IS_NOT_FOUND(500, "商品查询为空"),
	GOODS_INSERT_DEFAULTED(500, "商品添加失败"),
	GOODS_UPDATE_DEFAULT(500, "商品更新失败"),
	SKU_NOT_FOUND(500, "SKU不能为空"),
	SPU_NOT_FOUND(500, "SPU不能为空"),
	CHECK_USER_PARAM_ERROR(400, "user检验参数错误"),
	INVILD_USERNAME_PASSWORD(400, "用户名回密码不能为空"),
	USERNAME_OR_PASSWORD_IS_ERROR(500, "用户名或密码错误"),
	CARTS_IS_NULL(404, "购物车为空"),
	USER_EXPLAIN_ERROR(403, "用户信息验证失败");

	private int code;
	private String message;

}