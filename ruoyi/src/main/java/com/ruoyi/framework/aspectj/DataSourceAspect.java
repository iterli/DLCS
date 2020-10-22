package com.ruoyi.framework.aspectj;

import java.util.Objects;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.aspectj.lang.annotation.DataSource;
import com.ruoyi.framework.datasource.DynamicDataSourceContextHolder;

/**
 * 多数据源处理
 *
 * @author ruoyi
 */
@Aspect
@Order(1)
@Component
public class DataSourceAspect
{
    protected Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 目标(target)使用了@Transactional注解的方法
     * @target(org.springframework.transaction.annotation.Transactional)
     * 目标类(target)如果有Transactional注解的所有方法
     * @within(org.springframework.transaction.annotation.Transactional)
     * 任何方法有Transactional注解的方法
     * @annotation(org.springframework.transaction.annotation.Transactional)
     * 有且仅有一个参数并且参数上类型上有Transactional注解
     * @args(org.springframework.transaction.annotation.Transactional)
     */
    @Pointcut("@annotation(com.ruoyi.framework.aspectj.lang.annotation.DataSource)"
            + "|| @within(com.ruoyi.framework.aspectj.lang.annotation.DataSource)")
    public void dsPointCut()
    {

    }

    @Around("dsPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable
    {
        DataSource dataSource = getDataSource(point);

        if (StringUtils.isNotNull(dataSource))
        {
            DynamicDataSourceContextHolder.setDataSourceType(dataSource.value().name());
        }

        try
        {
            return point.proceed();
        }
        finally
        {
            // 销毁数据源 在执行方法之后
            DynamicDataSourceContextHolder.clearDataSourceType();
        }
    }

    /**
     * 获取需要切换的数据源
     */
    public DataSource getDataSource(ProceedingJoinPoint point)
    {
        MethodSignature signature = (MethodSignature) point.getSignature();
        /**
         * AnnotationUtils工具类提供的findAnnotation、getAnnotation等方法查找类、及其方法的注释对象内容。
         * findAnnotation: 从类或方法中查找某个annotation。
         */
        DataSource dataSource = AnnotationUtils.findAnnotation(signature.getMethod(), DataSource.class);
        //如果dataSource对象不为空
        if (Objects.nonNull(dataSource))
        {
            return dataSource;
        }

        return AnnotationUtils.findAnnotation(signature.getDeclaringType(), DataSource.class);
    }
}
