package com.yuanqi.controller;

import com.yuanqi.entity.User;
import com.yuanqi.form.UserRegisterForm;
import com.yuanqi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;


    @GetMapping("/getById/{id}")
    public String getById(@PathVariable("id") int id, Model model) {
        User userInfo = userService.findUserById(id);
        model.addAttribute("userInfo", userInfo);
        return "viewUser";
    }

    /**
     * 跳转到注册页面
     * @return
     */
    @GetMapping("toRegisterUser")
    public String toRegisterPage() {
        return "toRegisterUser";
    }

    /**
     * 注册的过程
     * 校验前台信息。把前台的数据封装成UserRegisterForm对象，并插入到数据库中。
     * @param userRegisterForm
     * @param model
     * @return
     */
    @PostMapping("registerUser")
    public String RegisterPage(UserRegisterForm userRegisterForm, Model model) throws Exception {
        if (StringUtils.isEmpty(userRegisterForm.getAccount())) {
            model.addAttribute("error", "用户名不能为空");
            return "toRegisterUser";
        }

        if (StringUtils.isEmpty(userRegisterForm.getPassword()) || StringUtils.isEmpty(userRegisterForm.getRepassword())) {
            model.addAttribute("error", "密码不能为空");
            return "toRegisterUser";
        }

        if (! userRegisterForm.getPassword().equals(userRegisterForm.getRepassword())) {
            model.addAttribute("error", "两次密码输入不一致");
            return "toRegisterUser";
        }

        //把信息封装到UserRegisterForm对象，插入到数据库
        userService.saveUserInfo(userRegisterForm);

        return "viewUser";
    }

    /**
     * 跳转到登录页面
     * @return
     */
    @GetMapping("toLogin")
    public String toLogin() {
        return "toLogin";
    }

    /**
     * 登录的过程
     * 校验前台信息。把前台的数据封装成UserRegisterForm对象，并与账户、加密后的密码进行比对
     * @param userRegisterForm
     * @param model
     * @return
     */
    @PostMapping("login")
    public String Login(UserRegisterForm userRegisterForm, Model model) throws Exception {
        if (StringUtils.isEmpty(userRegisterForm.getAccount())) {
            model.addAttribute("error", "用户名不能为空");
            return "toLogin";
        }

        if (StringUtils.isEmpty(userRegisterForm.getPassword())) {
            model.addAttribute("error", "密码不能为空");
            return "toLogin";
        }

        boolean result = userService.verifyUserAccount(userRegisterForm);
        if (!result) {
            model.addAttribute("error", "用户名或密码错误");
            return "toLogin";
        }

        return "toLogin";
    }





}
