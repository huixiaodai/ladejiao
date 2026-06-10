package com.itheima.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dto.OrderPageDTO;
import com.itheima.entity.Order;
import com.itheima.entity.OrderDetail;
import com.itheima.mapper.OrderDetailMapper;
import com.itheima.mapper.OrderMapper;
import com.itheima.result.PageResult;
import com.itheima.service.OrderService;
import com.itheima.vo.OrderVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Override
    public PageResult<OrderVO> getOrders(OrderPageDTO orderPageDTO) {

        PageHelper.startPage(orderPageDTO.getPage(),orderPageDTO.getPageSize());

        // 查询订单数据
        Page<Order> orders = orderMapper.getOrders(orderPageDTO);

        // 而 Order 表里面只有订单本身的信息,但是前端订单列表页面往往还需要：订单菜品信息（鱼香肉丝×1、米饭×2）、订单明细列表
        // 部分订单状态，需要额外返回订单菜品信息，将order转为为OrderVo
        List<OrderVO> orderVOList = getOrderVOList(orders);
        return new PageResult<>(orders.getTotal(),orderVOList);
    }

    /**
     * 抽出orders.getResult()的内容，其中的订单菜品需要有详情信息
     * @param orders
     * @return
     */
    private List<OrderVO> getOrderVOList(Page<Order> orders) {
        // 需要返回订单菜品信息，自定义OrderVOList响应结果
        List<OrderVO> orderVOList = new ArrayList<>();
        // orders就是一长串的和订单相关的内容，但是没有具体菜品什么的
        List<Order> orderList = orders.getResult();
        // CollectionUtils防止null和非空
        if (!CollectionUtils.isEmpty(orderList)) {
            for (Order order : orderList) {
                //将共同字段复制到VO
                OrderVO orderVO = new OrderVO();
                BeanUtils.copyProperties(order, orderVO);

                //将菜品都取出来，转化为拼接的字符串 鱼香肉丝*1；米饭*2；可乐*1
                String orderDishes = getOrderDishesStr(order);
                orderVO.setOrderDishes(orderDishes);
                orderVOList.add(orderVO);
            }
        }
        return orderVOList;
    }

    private String getOrderDishesStr(Order order) {
        // 查询订单菜品详情
        List<OrderDetail> orderDetailList = orderDetailMapper.getByOrderId(order.getId());
        // 将每一条订单菜品信息拼接为字符串 鱼香肉丝*1；米饭*2；可乐*1
        List<String> orderDishList = orderDetailList.stream().map(x -> {
            String orderDish = x.getName() + "*" + x.getNumber() + ";";
            return orderDish;
        }).collect(Collectors.toList()); //把加工后的结果收集成一个真正的 List<String>

        // 将该订单对应的所有菜品信息拼接在一起
        return String.join("", orderDishList);
    }
}
