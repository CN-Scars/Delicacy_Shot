package com.scars.service;

import com.scars.dto.SetmealDTO;
import com.scars.dto.SetmealPageQueryDTO;
import com.scars.result.PageResult;

public interface SetmealService {

    /**
     * 新增套餐
     * @param setmealDTO
     */
    void saveWithDish(SetmealDTO setmealDTO);

    /**
     * 分页查询
     * @param setmealPageQueryDTO
     * @return
     */
    PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);
}
