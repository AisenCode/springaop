

package cn.aisencode.pojo;

import io.swagger.annotations.ApiModel;

/**
 * 操作状态
 */
@ApiModel(value = "操作状态枚举")
public enum LogStatus {

    /**
     * 成功
     */
    SUCCESS,

    /**
     * 失败
     */
    FAIL,

}
