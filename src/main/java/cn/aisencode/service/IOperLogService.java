

package cn.aisencode.service;


import cn.aisencode.pojo.OperLog;
import cn.aisencode.pojo.RespPageBean;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 操作日志记录 服务类
 * </p>
 *
 */
@Service
public interface IOperLogService{

    /**
     * 创建系统操作日志
     *
     * @param operLog 操作日志对象
     */
    public void insertOperlog(OperLog operLog);

    RespPageBean getOperlogByPage(Integer currentPage, Integer pageSize, OperLog operLog, LocalDateTime[] createDateScope, LocalDateTime[] updateDateScope);
}
