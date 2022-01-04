

package cn.aisencode.pojo;

import io.swagger.annotations.ApiModel;

/**
 * 日志类型
 */
@ApiModel(value = "日志类型枚举")
public enum LogType {
    /**
     * 登录
     */
    LOGIN,

    /**
     * 注销
     */
    LOGOUT,

    /**
     * 查询
     */
    SEARCH,

    /**
     * 新增
     */
    INSERT,

    /**
     * 修改
     */
    UPDATE,

    /**
     * 删除
     */
    DELETE,

    /**
     * 其它
     */
    OTHER,

    /**
     * 授权
     */
    GRANT,

    /**
     * 导出
     */
    EXPORT,

    /**
     * 导入
     */
    IMPORT,

    /**
     * 强退
     */
    FORCE,

    /**
     * 生成代码
     */
    GENCODE,

    /**
     * 清空数据
     */
    CLEAN,

    /**
     * 状态
     */
    STATUS,

    /**
     * 重置密码
     */
    RESETPWD,

}
