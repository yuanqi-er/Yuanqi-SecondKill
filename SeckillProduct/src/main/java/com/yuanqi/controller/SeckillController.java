package com.yuanqi.controller;

import com.yuanqi.entity.Product;
import com.yuanqi.entity.SeckillProduct;
import com.yuanqi.form.SeckillForm;
import com.yuanqi.service.IntegratedService;
import com.yuanqi.service.ProductService;
import com.yuanqi.service.SeckillService;
import com.yuanqi.service.SeckillServiceByLock;
import com.yuanqi.strategy.PessimismLock;
import com.yuanqi.strategy.SeckillIntegrated;
import com.yuanqi.strategy.SeckillOperator;
import com.yuanqi.utils.RedisUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.text.ParseException;
import java.util.List;

@Controller
@RequestMapping("seckill")
public class SeckillController {
    @Autowired
    private ProductService productService;

    @Autowired
    private SeckillService seckillService;

    @Autowired
    private IntegratedService integratedService;
    @Autowired
    private SeckillServiceByLock seckillServiceByLock;

    private SeckillOperator seckillOperator;

    @GetMapping("listShopProduct")
    public String listShopProduct(int shopId, Model model) {
        List<Product> products = productService.listProductBy(shopId);
        model.addAttribute("products", products);
        return "listProduct";
    }

    //去发布审核通过的秒杀商品
    @GetMapping("preSaveSeckillProduct")
    public String preSaveSeckillProduct(int id, Model model) {
        Product productInfo = productService.findProductById(id);
        model.addAttribute("productInfo", productInfo);
        return "preSaveSeckill";
    }

    //保存秒杀商品的信息
    @PostMapping("saveSeckillProduct")
    public String saveSeckillProduct(SeckillForm seckillForm) throws ParseException {
        seckillService.saveSeckillProduct(seckillForm);
        return "success";
    }

    //展示秒杀的商品
    @GetMapping("listSeckillProductAll")
    public String listSeckillProductAll(int shopId, Model model) {
        SeckillForm seckillForm = new SeckillForm();
        seckillForm.setShopId(shopId);
        List<SeckillProduct> seckillProducts = seckillService.listSeckillInfo(seckillForm);
        model.addAttribute("seckillProducts", seckillProducts);
        return "listSeckillProduct";
    }

    //更改状态
    @GetMapping("updateState")
    public String updateState(int id, int state) {
        seckillService.updateSeckillProductState(id, state);
        return "updateSuccess";
    }



    //把通过审核的秒杀商品展示到首页
    @GetMapping("home")
    public String listSeckillProductByState(Model model) {
        SeckillForm seckillForm = new SeckillForm();
        seckillForm.setState(3);
        List<SeckillProduct> seckillProductsState3 = seckillService.listSeckillInfo(seckillForm);
        model.addAttribute("seckillProductsState3", seckillProductsState3);
        return "seckillHomePage";
    }

    /**
     * 策略模式秒杀？
     * （用策略类的类名作为redis的key名称）
     */
    @GetMapping("seckill")
    public String seckill(int userid, int id) {
        String strategy = RedisUtils.get("seckillStrategy");
        //如果策略为空，就默认这个异步实现
        if (StringUtils.isBlank(strategy)) {
            seckillOperator = new SeckillIntegrated(integratedService);
        } else {
            if ("PessimismLock".equals(strategy)) {
                seckillOperator = new PessimismLock(seckillServiceByLock);
            } else if("SeckillIntegrated".equals(strategy)) {
                seckillOperator = new SeckillIntegrated(integratedService);
            }
        }
        seckillOperator.seckill(userid, id);
        return "seckillSuccess";
    }

    /**
     * 传进去策略？？
     */
    @GetMapping("setSeckillStrategy")
    public String setSeckillStrategy(String strategy) {
        if ("PessimismLock".equals(strategy)) {
            seckillOperator = new PessimismLock(seckillServiceByLock);
        } else if("SeckillIntegrated".equals(strategy)) {
            seckillOperator = new SeckillIntegrated(integratedService);
        }
        RedisUtils.set("seckillStrategy", strategy);
        return "seckillStrategySuccess";
    }



}
