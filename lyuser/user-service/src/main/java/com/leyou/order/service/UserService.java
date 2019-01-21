package com.leyou.order.service;

import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.order.mapper.UserMapper;
import com.leyou.order.pojo.User;
import com.leyou.order.utils.CodecUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public Boolean checkUser(String data, Integer type) {
        User user=new User();
    switch (type){
        case 1:
            user.setUsername(data);
            break;
        case 2:
            user.setPhone(data);
            break;
         default:
             throw new LyException(ExceptionEnum.CHECK_USER_PARAM_ERROR);
    }
        return userMapper.selectCount(user)==0;
    }

    public void register(User user) {
            String salt="abc";
            user.setSalt(salt);
            user.setCreated(new Date());
        String pwd = CodecUtils.string2MD5(user.getPassword()+salt);
        user.setPassword(pwd);
        userMapper.insert(user);
    }

    public User query(String username, String password) {
        User user1=new User();
        user1.setUsername(username);
        User selectOne = userMapper.selectOne(user1);
        if (StringUtils.isEmpty(selectOne)){
            throw new LyException(ExceptionEnum.USERNAME_OR_PASSWORD_IS_ERROR);
        }
        if (!selectOne.getPassword().equals(CodecUtils.string2MD5(password+selectOne.getSalt()))){
            throw new LyException(ExceptionEnum.USERNAME_OR_PASSWORD_IS_ERROR);
             }
    return selectOne;
}
}