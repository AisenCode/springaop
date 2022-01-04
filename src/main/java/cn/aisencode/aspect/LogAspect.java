

package cn.aisencode.aspect;



import cn.aisencode.annotation.Log;
import cn.aisencode.manager.AsyncFactory;
import cn.aisencode.manager.AsyncManager;
import cn.aisencode.pojo.LogStatus;
import cn.aisencode.pojo.OperLog;

import cn.aisencode.util.*;
import com.alibaba.fastjson.JSON;
import eu.bitwalker.useragentutils.UserAgent;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.NamedThreadLocal;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * 自定义操作日志切面处理类
 */
/**
 * @Aspect 面向切面编程注解，通常应用在类上
 * @Pointcut 定义切入点，Pointcut是植入Advice的触发条件。每个Pointcut的定义包括2部分，一是表达式，
 * 二是方法签名。方法签名必须是 public及void型。可以将Pointcut中的方法看作是一个被Advice引用的助记符，
 * 因为表达式不直观，因此我们可以通过方法签名的方式为 此表达式命名。因此Pointcut中的方法只需要方法签名，
 * 而不需要在方法体内编写实际代码
 * @Around：环绕增强，可以获取到目标方法的入参和返回值
 * @AfterReturning：后置增强，方法正常退出时执行
 * @Before：前置增强，目标方法执行之前执行
 * @AfterThrowing：异常抛出增强，目标方法抛出异常后执行
 * @After: final增强，不管是抛出异常或者正常退出都会执行
 */
@Aspect
@Component
public class LogAspect {

    private static final Logger log = LoggerFactory.getLogger(LogAspect.class);
    private static final ThreadLocal<Date> beginTimeThreadLocal = new NamedThreadLocal<Date>("ThreadLocal beginTime");

    // 配置织入点
    @Pointcut("@annotation(cn.aisencode.annotation.Log)")
    public void logPointCut() {
    }

    /**
     * 定义切入点 触发条件
     * 第一个*表示任意任意返回类型
     * .* 表示所有子类
     * .*(..) 表示任何方法名，括号表示参数，两个点表示任何参数类型
     */
    /*@Pointcut("execution(* cn.aisencode.controller.*.*(..))")
    public void controllerPointerCut(){}*/

    /**
     * 前置通知 用于拦截Controller层记录用户的操作的开始时间
     * @param joinPoint 切点
     * @throws InterruptedException
     */
    @Before("logPointCut()")
    public void doBefore(JoinPoint joinPoint) throws InterruptedException{
        Date beginTime=new Date();
        beginTimeThreadLocal.set(beginTime);

    }

    /**
     * 处理完请求后执行
     *
     * @param joinPoint 切点
     */
    @AfterReturning(pointcut = "logPointCut()", returning = "jsonResult")
    public void doAfterReturning(JoinPoint joinPoint, Object jsonResult) {
        handleLog(joinPoint, null, jsonResult);
    }

    /**
     * 拦截异常操作
     *
     * @param joinPoint 切点
     * @param e         异常
     */
    @AfterThrowing(value = "logPointCut()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Exception e) {
        handleLog(joinPoint, e, null);
    }

    protected void handleLog(final JoinPoint joinPoint, final Exception e, Object jsonResult) {
        try {
            // 获得注解
            Log controllerLog = getAnnotationLog(joinPoint);
            if (controllerLog == null) {
                return;
            }

            // *========数据库日志=========*//
            OperLog operLog = new OperLog();
            //得到线程绑定的局部变量（开始时间）
            long beginTime = beginTimeThreadLocal.get().getTime();
            //2、结束时间
            long endTime = System.currentTimeMillis();
            //操作时间
            operLog.setCreateTime(new Date(beginTime));
            //花费时间
            operLog.setTimeout(endTime-beginTime);
            //创建人
            //User user = ShiroUtils.getUserInfo();
            //operLog.setOperName(StringUtils.isNull(user) ? "系统异常" : user.getUsername());
            //自己根据系统获取登录用户的方式设置
            operLog.setCreateUser(1);
            //请求成功
            operLog.setStatus(LogStatus.SUCCESS.ordinal());
            // 请求的地址
            String ip = IpUtils.getIpAddr(ServletUtils.getRequest());
            operLog.setOperIp(ip);
            //
            String header = ServletUtils.getRequest().getHeader("User-Agent");
            final UserAgent userAgent = UserAgent.parseUserAgentString(header);
            // 获取客户端操作系统
            String os = userAgent.getOperatingSystem().getName();
            // 获取客户端浏览器
            String browser = userAgent.getBrowser().getName();
            operLog.setOs(os);
            operLog.setBrowser(browser);
            operLog.setUserAgent(header);
            // 返回参数
            operLog.setJsonResult(JSON.toJSONString(jsonResult));
            operLog.setOperUrl(ServletUtils.getRequest().getRequestURI());
            if (e != null) {
                //请求失败
                operLog.setStatus(LogStatus.FAIL.ordinal());
                //备注
                operLog.setNote(StringUtils.substring(e.toString(), 0, 2000));
            }
            // 设置方法名称
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            operLog.setOperMethod(className + "." + methodName + "()");
            // 设置请求方式
            operLog.setRequestMethod(ServletUtils.getRequest().getMethod());
            // 处理设置注解上的参数
            getControllerMethodDescription(joinPoint, controllerLog, operLog);
            // 保存数据库
            AsyncManager.me().execute(AsyncFactory.recordOper(operLog));
        } catch (Exception exp) {
            // 记录本地异常日志
            log.error("==前置通知异常==");
            log.error("异常信息:{}", exp.getMessage());
            exp.printStackTrace();
        }
    }

    /**
     * 获取注解中对方法的描述信息 用于Controller层注解
     *
     * @param log     日志
     * @param operLog 操作日志
     * @throws Exception
     */
    public void getControllerMethodDescription(JoinPoint joinPoint, Log log, OperLog operLog) throws Exception {
        // 设置action动作
        operLog.setLogType(log.logType().ordinal());
        // 查询不保存返回结果。
        if(log.logType().ordinal() == 2) {
            operLog.setJsonResult(null);
        }
        // 设置标题
        operLog.setTitle(log.title());
        // 是否需要保存request，参数和值
        if (log.isSaveRequestData()) {
            // 获取参数的信息，传入到数据库中。
            setRequestValue(joinPoint, operLog);
        }
    }

    /**
     * 获取请求的参数，放到log中
     *
     * @param operLog 操作日志
     * @throws Exception 异常
     */
    private void setRequestValue(JoinPoint joinPoint, OperLog operLog) throws Exception {
        String requestMethod = operLog.getRequestMethod();
        if (HttpMethod.PUT.name().equals(requestMethod) || HttpMethod.POST.name().equals(requestMethod)) {
            String params = argsArrayToString(joinPoint.getArgs());
            operLog.setOperParam(StringUtils.substring(params, 0, 2000));
        } else {
            //只能获取@PathVariable 的参数值
            Map<?, ?> paramsMap = (Map<?, ?>) ServletUtils.getRequest().getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);

            if (paramsMap.isEmpty()){
                Map<?, ?> parameterMap = (Map<?,?>) ServletUtils.getRequest().getParameterMap();
                String jsonString = JSON.toJSONString(parameterMap);
                operLog.setOperParam(StringUtils.substring(jsonString,0,2000));
                return;
            }


            operLog.setOperParam(StringUtils.substring(paramsMap.toString(), 0, 2000));
        }
    }

    /**
     * 是否存在注解，如果存在就获取
     */
    private Log getAnnotationLog(JoinPoint joinPoint) throws Exception {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();

        if (method != null) {
            return method.getAnnotation(Log.class);
        }
        return null;
    }

    /**
     * 参数拼装
     */
    private String argsArrayToString(Object[] paramsArray) {
        String params = "";
        if (paramsArray != null && paramsArray.length > 0) {
            for (int i = 0; i < paramsArray.length; i++) {
                if (!isFilterObject(paramsArray[i])) {
                    Object jsonObj = JSON.toJSON(paramsArray[i]);
                    params += jsonObj.toString() + " ";
                }
            }
        }
        return params.trim();
    }

    /**
     * 判断是否需要过滤的对象。
     *
     * @param o 对象信息。
     * @return 如果是需要过滤的对象，则返回true；否则返回false。
     */
    public boolean isFilterObject(final Object o) {
        return o instanceof MultipartFile || o instanceof HttpServletRequest || o instanceof HttpServletResponse;
    }

}
