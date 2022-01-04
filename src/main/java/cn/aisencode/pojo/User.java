

package cn.aisencode.pojo;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


import java.io.Serializable;



@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "User对象")
@TableName("t_user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "用户id",required = false)
    private Integer id;

    /**
     * 登录用户名
     */
    @ApiModelProperty(value = "用户名",required = true)
    private String username;

    /**
     * 登录密码
     */
    @ApiModelProperty(value = "密码",required = true)
    private String password;

    /**
     * 用户状态，0:正常,1:封禁
     */
    @ApiModelProperty(value = "用户状态",required = false)
    private Integer status;


}
