package com.itheima.service;

import com.itheima.dto.OrderPageDTO;
import com.itheima.result.PageResult;
import com.itheima.vo.OrderVO;

public interface OrderService {
    PageResult<OrderVO> getOrders(OrderPageDTO orderPageDTO);
}
