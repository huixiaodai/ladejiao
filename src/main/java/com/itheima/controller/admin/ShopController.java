package com.itheima.controller.admin;

import com.itheima.result.Result;
import com.itheima.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/shop")
public class ShopController {
    @Autowired
    private ShopService shopService;

    @PutMapping("/{status}")
    public Result setStatus(@PathVariable Integer status) {
        shopService.setStatus(status);
        return Result.success();
    }

    @GetMapping("/status")
    public Result<Integer> getStatus() {
        return Result.success(shopService.getStatus());
    }
}
