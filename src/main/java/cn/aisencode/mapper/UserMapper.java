

package cn.aisencode.mapper;

import cn.aisencode.pojo.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     * 创建系统登录日志
     *
     * @param loginLog 登录信息
     */
    /*void insertLoginLog(LoginLog loginLog);*/

}
