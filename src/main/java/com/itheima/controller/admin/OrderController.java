package com.itheima.controller.admin;

import com.itheima.dto.OrderPageDTO;
import com.itheima.result.PageResult;
import com.itheima.result.Result;
import com.itheima.service.OrderService;
import com.itheima.vo.OrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/conditionSearch")
    public Result<PageResult<OrderVO>> getOrders(OrderPageDTO orderPageDTO) {
        return Result.success(orderService.getOrders(orderPageDTO));
    }
}
