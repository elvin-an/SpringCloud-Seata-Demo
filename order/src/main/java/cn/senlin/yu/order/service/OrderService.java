package cn.senlin.yu.order.service;

import java.math.BigDecimal;

public interface OrderService {

    void create(Long uid, BigDecimal money);
}
