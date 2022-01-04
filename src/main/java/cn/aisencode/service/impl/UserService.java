package cn.aisencode.service.impl;

import cn.aisencode.mapper.UserMapper;
import cn.aisencode.pojo.User;
import cn.aisencode.service.IUserService;
import cn.aisencode.util.RespBean;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User getUserByName(String username) {
        User outUser = userMapper.selectOne(new QueryWrapper<User>().eq("username", username));
        return outUser;
    }

    @Override
    public RespBean login(User user) {
        User userByName = getUserByName(user.getUsername());
        if (userByName==null){
            return RespBean.error("登录失败，用户名或密码错误。");
        }
        if (!user.getPassword().equals(userByName.getPassword())){
            return RespBean.error("登录失败，用户名或密码错误。");
        }
        if (userByName.getStatus()==1){
            return RespBean.error("登录失败，账号已封禁，请联系管理员。");
        }
        return RespBean.success("登录成功。");
    }
}
