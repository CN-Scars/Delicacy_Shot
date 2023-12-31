package com.scars.controller.admin;

import com.scars.dto.DishDTO;
import com.scars.dto.DishPageQueryDTO;
import com.scars.entity.Dish;
import com.scars.result.PageResult;
import com.scars.result.Result;
import com.scars.service.DishService;
import com.scars.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/admin/dish")
@Api(tags = "菜品管理相关接口")
@Slf4j
public class DishController {
    @Autowired
    private DishService dishService;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 新增菜品
     *
     * @param dishDTO
     * @return
     */
    @PostMapping
    @ApiOperation("新增菜品")
    private Result save(@RequestBody DishDTO dishDTO) {
        log.info("新增了菜品：{}", dishDTO);
        dishService.saveWithFlavor(dishDTO);

        // 清理缓存数据
        String key = "dish_" + dishDTO.getCategoryId();
        cleanCache(key);

        return Result.success();
    }

    @GetMapping("/page")
    @ApiOperation("菜品分页查询")
    public Result<PageResult> page(DishPageQueryDTO dishPageQueryDTO) {
        log.info("菜品分页查询信息：{}", dishPageQueryDTO);
        PageResult pageResult = dishService.pageQuery(dishPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 删除菜品
     *
     * @param ids
     * @return
     */
    @DeleteMapping
    @ApiOperation("菜品删除")
    public Result delete(@RequestParam List<Long> ids) {
        log.info("删除了菜品：{}", ids);
        dishService.deleteBatch(ids);

        // 清理缓存
        cleanCache("dish_*");

        return Result.success();
    }

    @GetMapping("/{id}")
    @ApiOperation("根据id查询菜品")
    public Result<DishVO> getById(@PathVariable Long id) {
        log.info("查询的菜品id为：{}", id);
        DishVO dishVO = dishService.getByIdWithFlavor(id);
        return Result.success(dishVO);
    }

    @PutMapping
    @ApiOperation("修改菜品信息")
    public Result update(@RequestBody DishDTO dishDTO) {
        log.info("修改的菜品信息：{}", dishDTO);
        dishService.updateWithFlavor(dishDTO);

        // 清理缓存
        cleanCache("dish_*");

        return Result.success();
    }

    @PostMapping("/status/{status}")
    @ApiOperation("菜品的起售与停售")
    public Result<String> enableOrDisable(@PathVariable Integer status, Long id) {
        dishService.enableOrDisable(status, id);

        // 清理缓存
        cleanCache("dish_*");

        return Result.success();
    }

    /**
     * 动态条件查询菜品
     *
     * @param dishDTO
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("动态条件查询菜品")
    public Result<List<Dish>> list(DishDTO dishDTO) {
        List<Dish> dishes = dishService.list(dishDTO);
        return Result.success(dishes);
    }

    /**
     * 清理缓存数据
     *
     * @param pattern
     */
    private void cleanCache(String pattern) {
        Set keys = redisTemplate.keys(pattern);
        redisTemplate.delete(keys);
    }
}
