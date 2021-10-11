package com.yuanqi.controller;

import com.yuanqi.entity.Merchant;
import com.yuanqi.form.MerchantCheckInForm;
import com.yuanqi.service.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("merchant")
public class MerchantController {
    @Autowired
    private MerchantService merchantService;

    @GetMapping("/getById/{id}")
    public String getById(@PathVariable("id") int id, Model model) {
        Merchant merchantInfo = merchantService.findMerchantById(id);
        model.addAttribute("merchantInfo", merchantInfo);
        return "viewMerchant";
    }

    //跳转到注册页面（商家入驻）！
    @GetMapping("toCheckIn")
    public String toCheckIn() {
        return "toCheckIn";
    }

    //注册的过程！
    @PostMapping("checkIn")
    public String RegisterPage(MerchantCheckInForm merchantCheckInForm, Model model) throws Exception {
        if (StringUtils.isEmpty(merchantCheckInForm.getAccount())) {
            model.addAttribute("error", "商家账户不能为空");
            return "toCheckIn";
        }

        if (StringUtils.isEmpty(merchantCheckInForm.getPassword()) || StringUtils.isEmpty(merchantCheckInForm.getRepassword())) {
            model.addAttribute("error", "密码不能为空");
            return "toCheckIn";
        }

        if (! merchantCheckInForm.getPassword().equals(merchantCheckInForm.getRepassword())) {
            model.addAttribute("error", "两次密码输入不一致");
            return "toCheckIn";
        }

        //把信息封装到MerchantCheckInForm对象，插入到数据库
        merchantService.saveMerchantInfo(merchantCheckInForm);

        return "toCheckIn";
    }

    //跳转到登录页面！
    @GetMapping("toLogin")
    public String toLogin() {
        return "toLogin";
    }

    //登录的过程！
    @PostMapping("login")
    public String Login(MerchantCheckInForm merchantCheckInForm, Model model) throws Exception {
        if (StringUtils.isEmpty(merchantCheckInForm.getAccount())) {
            model.addAttribute("error", "商家账户不能为空");
            return "toLogin";
        }

        if (StringUtils.isEmpty(merchantCheckInForm.getPassword())) {
            model.addAttribute("error", "密码不能为空");
            return "toLogin";
        }

        boolean result = merchantService.verifyMerchantAccount(merchantCheckInForm);
        if (!result) {
            model.addAttribute("error", "用户名或密码错误");
            return "toLogin";
        }

        return "toLogin";
    }



}
