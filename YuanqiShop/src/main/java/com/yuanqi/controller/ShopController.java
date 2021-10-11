package com.yuanqi.controller;

import com.yuanqi.entity.Shop;
import com.yuanqi.form.ShopApplyForm;
import com.yuanqi.form.ShopSearchForm;
import com.yuanqi.form.UserRegisterForm;
import com.yuanqi.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
@RequestMapping("shop")
public class ShopController {
    @Autowired
    private ShopService shopService;

    //商家跳转到申请页面
    @GetMapping("toApplyShop")
    public String toApplyShop() {
        return "toApplyShop";
    }

    //申请的过程
    @PostMapping("applyShop")
    public String ApplyShop(ShopApplyForm shopApplyForm, Model model) throws Exception {
        if (StringUtils.isEmpty(shopApplyForm.getShopName())) {
            model.addAttribute("error", "店铺名称不能为空");
            return "toApplyShop";
        }

        if (StringUtils.isEmpty(shopApplyForm.getShopBusinessScope())) {
            model.addAttribute("error", "经营范围不能为空");
            return "toApplyShop";
        }

        if (StringUtils.isEmpty(shopApplyForm.getBusinessLicense())) {
            model.addAttribute("error", "店铺营业执照不能为空");
            return "toApplyShop";
        }

        shopService.saveShopInfo(shopApplyForm);

        return "toApplyShop";
    }

    //跳转到查询店铺的页面
    @GetMapping("toSearchShop")
    public String toSearchShop() {
        return "toSearchShop";
    }

    /**
     * 管理员查询所有的店铺
     */
    @PostMapping("searchShop")
    public String searchShop(ShopSearchForm shopSearchForm, Model model) {
        List<Shop> listShop = shopService.listShopInfoBy(shopSearchForm);
        model.addAttribute("listShop", listShop);
        return "listShop";
    }

    /**
     * 管理员更新店铺的状态
     */
    @GetMapping("updateState")
    public String updateState(int state, int id) {
        Shop shop = new Shop();
        shop.setState(state);
        shop.setId(id);
        shopService.updateInfoBy(shop);
        return "listShop";
    }


}
