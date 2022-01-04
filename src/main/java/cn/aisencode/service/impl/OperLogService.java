package cn.aisencode.service.impl;

import cn.aisencode.mapper.OperLogMapper;
import cn.aisencode.pojo.OperLog;
import cn.aisencode.pojo.RespPageBean;
import cn.aisencode.service.IOperLogService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Aisen
 * @time 2021.10.11 14:29
 */
@Service
public class OperLogService implements IOperLogService {

    @Autowired
    OperLogMapper operLogMapper;

    @Override
    public void insertOperlog(OperLog operLog) {
        operLogMapper.insert(operLog);
    }

    @Override
    public RespPageBean getOperlogByPage(Integer currentPage, Integer pageSize, OperLog operLog, LocalDateTime[] createDateScope, LocalDateTime[] updateDateScope) {
        //开启分页
        Page<OperLog> page = new Page<>(currentPage,pageSize);
        IPage<OperLog> iPage = operLogMapper.getOperlogByPage(page, operLog, createDateScope,updateDateScope);
        RespPageBean respPageBean = new RespPageBean(iPage.getTotal(),iPage.getSize(), iPage.getPages(), iPage.getCurrent(), iPage.getRecords());
        return respPageBean;
    }


}
