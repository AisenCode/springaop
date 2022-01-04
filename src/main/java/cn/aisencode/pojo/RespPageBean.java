package cn.aisencode.pojo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 分页公共返回对象
 * @author Aisen
 * @time 2021.6.3 09:21
 */

@Data
@NoArgsConstructor
@ApiModel(value = "分页公共返回对象")
@AllArgsConstructor
public class RespPageBean {
    /**
     * 总条数
     */
    private Long total;
    /**
     * 每页条数
     */
    private Long pageSize;
    /**
     * 总页数
     */
    private Long totalPage;
    /**
     * 当前页码
     */
    private Long currentPage;
    /**
     * 数据list
     */
    private List<?> data;
}
