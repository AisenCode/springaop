

package cn.aisencode.service;



import cn.aisencode.pojo.User;
import cn.aisencode.util.RespBean;

/**
 * <p>
 * 用户 服务类
 * </p>
 *
 */

public interface IUserService {


    public User getUserByName(String username);

    RespBean login(User user);
}
