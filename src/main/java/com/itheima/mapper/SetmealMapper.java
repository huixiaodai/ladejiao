package com.itheima.mapper;

import com.github.pagehelper.Page;
import com.itheima.annotation.AutoFill;
import com.itheima.dto.SetmealPageQueryDTO;
import com.itheima.entity.Setmeal;
import com.itheima.entity.SetmealDish;
import com.itheima.enumeration.OperationType;
import com.itheima.vo.SetmealVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SetmealMapper {
    @AutoFill(OperationType.INSERT)
    @Insert("insert into setmeal (category_id, name, price, status, description, image, create_time, update_time, create_user, update_user) VALUES " +
            "(#{categoryId},#{name},#{price},#{status},#{description},#{image},#{createTime},#{updateTime},#{createUser},#{updateUser})")
    @Options(useGeneratedKeys = true,keyProperty = "id")
    void insert(Setmeal setmeal);


    Page<SetmealVO> getSetmealList(SetmealPageQueryDTO setmealPageQueryDTO);

    @Select("select * from setmeal where id = #{id}")
    SetmealVO getSetmealById(Integer id);

    @AutoFill(OperationType.UPDATE)
    void updateSetmeal(Setmeal setmeal);

    @Delete("delete from setmeal where id = #{id}")
    void deleteById(Integer id);

    void deleteBatchByIds(List<Integer> ids);
}
