package com.scars.service;

import com.scars.dto.DishDTO;
import com.scars.dto.DishPageQueryDTO;
import com.scars.result.PageResult;

public interface DishService {

    /**
     * 新增菜品和对应的口味
     * @param dishDTO
     */
    void saveWithFlavor(DishDTO dishDTO);

    /**
     * 菜品分页查询
     * @param dishPageQueryDTO
     * @return
     */
    PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO);
}
