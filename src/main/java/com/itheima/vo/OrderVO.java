package com.itheima.vo;

import com.itheima.entity.Order;
import com.itheima.entity.OrderDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

// OrderVO:Order里的所有字段 + orderDishes + orderDetailList
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderVO extends Order implements Serializable {

    private String orderDishes; // 订单菜品信息
    private List<OrderDetail> orderDetailList; // 各个订单详情
}
