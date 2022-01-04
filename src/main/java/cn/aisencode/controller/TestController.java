package cn.aisencode.controller;

import cn.aisencode.annotation.Log;
import cn.aisencode.pojo.LogType;
import cn.aisencode.pojo.OperLog;
import cn.aisencode.pojo.RespPageBean;
import cn.aisencode.pojo.User;
import cn.aisencode.service.impl.OperLogService;
import cn.aisencode.service.impl.UserService;
import cn.aisencode.util.AppException;
import cn.aisencode.util.RespBean;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author Aisen
 * @time 2021.10.8 15:34
 */

@RestController
@RequestMapping("/")
public class TestController {

    @Autowired
    private UserService userService;
    @Autowired
    private OperLogService operLogService;

    @ApiOperation(value = "正常测试")
    @GetMapping("/test")
    @Log(title = "正常测试", logType = LogType.SEARCH)
    public RespBean test() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return RespBean.success("ok");
    }

    @ApiOperation(value = "异常测试")
    @GetMapping("/err")
    @Log(title = "异常测试", logType = LogType.SEARCH)
    public RespBean err() throws AppException {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        throw new AppException("异常测试","异常");
    }



    @ApiOperation(value = "登录测试")
    @PostMapping("/login")
    @CrossOrigin
    @Log(title = "登录测试", logType = LogType.LOGIN)
    public RespBean login(User user) throws AppException {
        try {
            if (user.getUsername()!=null&&user.getPassword()!=null){
                return userService.login(user);
            }
            return RespBean.error("请输入账号或密码");
        }catch(Exception e){
            throw new AppException("登录系统出错",e.toString());
        }
    }


    @ApiOperation(value = "日志查询测试")
    @GetMapping("/getOperLog")
    @Log(title ="查询测试", logType = LogType.SEARCH)
    @CrossOrigin
    @ResponseBody
    public RespPageBean getOperLog(@RequestParam(defaultValue = "1") Integer currentPage,
                                    @RequestParam(defaultValue = "10") Integer pageSize,
                                    OperLog operLog,
                                   LocalDateTime[] createDateScope,
                                   LocalDateTime[] updateDateScope) {
        return operLogService.getOperlogByPage(currentPage,pageSize,operLog,createDateScope,updateDateScope);
    }

}
