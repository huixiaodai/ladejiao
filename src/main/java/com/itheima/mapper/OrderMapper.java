package com.itheima.mapper;

import com.github.pagehelper.Page;
import com.itheima.dto.OrderPageDTO;
import com.itheima.entity.Order;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper {
    Page<Order> getOrders(OrderPageDTO orderPageDTO);
}
