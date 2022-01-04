

package cn.aisencode.mapper;

import cn.aisencode.pojo.OperLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;


/**
 * <p>
 * 操作日志记录 Mapper 接口
 * </p>
 *
 */
public interface OperLogMapper extends BaseMapper<OperLog> {

    /**
     * 分页查询日志
     * @param page
     * @param operLog
     * @param createDateScope
     * @param updateDateScope
     * @return
     */
    IPage<OperLog> getOperlogByPage(Page<OperLog> page, @Param("operLog") OperLog operLog, @Param("createDateScope") LocalDateTime[] createDateScope, @Param("updateDateScope") LocalDateTime[] updateDateScope);

    
    /**
     * 创建系统操作日志
     *
     * @param operLog 操作日志对象
     */
    /*void insertOperlog(OperLog operLog);*/

}
