package com.yuanqi.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Aspect
@Component
public class SeckillAspect {
    //导入锁
    private static Lock lock = new ReentrantLock();


    /**
     * 定义了一个切点 Pointcut， 并且把切点配置到通知上面
     */
    //我们的controller是两个参数，要注意
    @Pointcut("execution(public * com.yuanqi.service.SeckillServiceByLock.procedureLockByAOP(*,*))")
    public void lockSeckill() {}

    @Around("lockSeckill()")
    public Object around(ProceedingJoinPoint joinPoint) {
        lock.lock();            //上锁
        Object obj = null;

        try {
            obj = joinPoint.proceed();      //去执行切片点的方法
            System.out.println("进入around");
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            lock.unlock();      //释放锁
        }
        return obj;
    }

}