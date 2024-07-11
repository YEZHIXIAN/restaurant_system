package com.sky.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderDetailMapper {

    /**
     * 批量插入订单明细数据
     * @param orderDetailMapper
     */
    void insertBatch(OrderDetailMapper orderDetailMapper);
}
