package com.leyou.order.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Length;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Table(name = "tb_user")
public class User {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;
    @NotEmpty(message = "用户名不能为空")
    @Length(max = 12,min =4,message = "密码长度不对")
    private String username;// 用户名

    @JsonIgnore
	@Length(max = 12,min =4,message = "密码长度不对")
    private String password;// 密码

	@Pattern(regexp = "^[1](([3|5|8][\\d])|([4][5,6,7,8,9])|([6][5,6])|([7][3,4,5,6,7,8])|([9][8,9]))[\\d]{8}$",message = "手机号格式不对")
    private String phone;// 电话

    private Date created;// 创建时间


    @JsonIgnore
    private String salt;// 密码的盐值

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
}