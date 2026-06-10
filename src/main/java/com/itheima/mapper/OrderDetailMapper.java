package com.itheima.mapper;

import com.itheima.entity.OrderDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrderDetailMapper {

    @Select("select * from order_detail where dish_id = #{id}")
    List<OrderDetail> getByOrderId(Integer id);
}
