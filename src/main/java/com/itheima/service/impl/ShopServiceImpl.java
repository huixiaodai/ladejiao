package com.itheima.service.impl;

import com.itheima.mapper.ShopMapper;
import com.itheima.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import static com.itheima.constant.RedisConstant.SHOP_STATUS;

@Service
public class ShopServiceImpl implements ShopService {
    @Autowired
    private ShopMapper shopMapper;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void setStatus(Integer status) {
        stringRedisTemplate.opsForValue().set(SHOP_STATUS, String.valueOf(status));
    }

    @Override
    public Integer getStatus() {
        String status = stringRedisTemplate.opsForValue().get(SHOP_STATUS);
        if (status == null){
            setStatus(1);
            return Integer.valueOf(1);
        }
        return Integer.valueOf(status);
    }
}
