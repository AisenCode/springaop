

package cn.aisencode.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * <p>
 * 操作日志记录
 * </p>
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "日志对象")
@TableName("t_sys_oper_log")
public class OperLog extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 日志标题
     */
    @ApiModelProperty(value = "日志标题",required = false)
    private String title;

    /**
     * 业务类型（0其它 1新增 2修改 3删除）
     */
    @ApiModelProperty(value = "业务类型",required = false)
    private Integer logType;

    /**
     * 操作方法
     */
    @ApiModelProperty(value = "操作方法",required = false)
    private String operMethod;

    /**
     * 请求方式
     */
    @ApiModelProperty(value = "请求方式",required = false)
    private String requestMethod;



    /**
     * 请求URL
     */
    @ApiModelProperty(value = "请求URL",required = false)
    private String operUrl;

    /**
     * 主机地址
     */
    @ApiModelProperty(value = "主机地址",required = false)
    private String operIp;

    /**
     * 操作地点
     */
    @ApiModelProperty(value = "操作地点",required = false)
    private String operLocation;

    /**
     * 请求参数
     */
    @ApiModelProperty(value = "请求参数",required = false)
    private String operParam;

    /**
     * 返回参数
     */
    @ApiModelProperty(value = "返回参数",required = false)
    private String jsonResult;

    /**
     * 操作状态（0正常 1异常）
     */
    @ApiModelProperty(value = "操作状态",required = false)
    private Integer status;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注",required = false)
    private String note;


    /**
     * 花费时间
     */
    @ApiModelProperty(value = "花费时间",required = false)
    private Long timeout;

    /**
     * 浏览器信息
     */
    @ApiModelProperty(value = "浏览器信息",required = false)
    private String browser;

    /**
     * 系统信息
     */
    @ApiModelProperty(value = "系统信息",required = false)
    private String os;

    /**
     * userAgent
     */
    @ApiModelProperty(value = "userAgent",required = false)
    private String userAgent;
}
