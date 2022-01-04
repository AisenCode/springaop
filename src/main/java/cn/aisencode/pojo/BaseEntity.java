

package cn.aisencode.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 基类实体对象
 *
 */
@Data
@ApiModel(value = "基类对象")
public class BaseEntity implements Serializable {

    /**
     * 主键ID
     */
    @ApiModelProperty(value = "主键id",required = false)
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 添加人id
     */
    @ApiModelProperty(value = "添加人id",required = false)
    private Integer createUser;

    /**
     * 添加人
     */
    @ApiModelProperty(value = "添加人名字",required = false)
    @TableField(exist = false)
    private String createUsername;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间",required = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 更新人id
     */
    @ApiModelProperty(value = "更新人id",required = false)
    private Integer updateUser;

    /**
     * 更新人
     */
    @ApiModelProperty(value = "更新人名字",required = false)
    @TableField(exist = false)
    private Integer updateUsername;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间",required = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    /**
     * 有效标识
     */
    @ApiModelProperty(value = "有效标识",required = false)
    private Integer mark;

}
