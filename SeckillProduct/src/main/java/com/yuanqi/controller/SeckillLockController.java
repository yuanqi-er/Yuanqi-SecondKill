package com.yuanqi.controller;

import com.yuanqi.service.IntegratedService;
import com.yuanqi.service.SeckillServiceByLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("seckill")
public class SeckillLockController {
    @Autowired
    private SeckillServiceByLock seckillServiceByLock;
    @Autowired
    private IntegratedService integratedService;

    @ResponseBody
    @GetMapping("procedureLock")
    public Map<String, String> procedureLockByAOP(int userId, int id, Model model) {
        Map<String, String> dataMap = seckillServiceByLock.procedureLockByAOP(userId, id);
        return dataMap;
    }

    @ResponseBody
    @GetMapping("multiThread")
    public void multiThread(int userId, int id, Model model) {
        seckillServiceByLock.multiThread(userId, id);
    }

    @ResponseBody
    @GetMapping("pessimismLock")
    public Map<String, String> pessimismLock(int userId, int id, Model model) {
        Map<String, String> dataMap = seckillServiceByLock.pessimismLock(userId, id);
        return dataMap;
    }

    @ResponseBody
    @GetMapping("optimisticLock")
    public Map<String, String> optimisticLock(int userId, int id, Model model) {
        Map<String, String> dataMap = seckillServiceByLock.optimisticLock(userId, id);
        return dataMap;
    }

    @ResponseBody
    @GetMapping("redisDistributeLock")
    public Map<String, String> redisDistributeLock(int userId, int id, Model model) {
        return seckillServiceByLock.redisLock(userId, id);
    }

    @ResponseBody
    @GetMapping("seckillFuture")
    public void seckillFuture(int userId, int id) {
        seckillServiceByLock.seckillFuture(userId, id);
    }

    @ResponseBody
    @GetMapping("seckillIntegrated")
    public void seckillIntegrated(int userId, int id) {
        integratedService.seckillIntegrated(userId, id);
    }





}
