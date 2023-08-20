package com.scars.service;

import com.scars.dto.ShoppingCartDTO;

public interface ShoppingCartService {
    /**
     * 添加购物车数据
     * @param shoppingCartDTO
     */
    void addShoppingCart(ShoppingCartDTO shoppingCartDTO);
}
