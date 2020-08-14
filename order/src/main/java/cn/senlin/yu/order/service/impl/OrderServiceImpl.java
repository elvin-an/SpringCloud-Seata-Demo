package cn.senlin.yu.order.service.impl;

import cn.senlin.yu.order.fein.OrderFeinClient;
import cn.senlin.yu.order.mapper.OrderMapper;
import cn.senlin.yu.order.service.OrderService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderFeinClient orderFeinClient;

    @Override
    @Transactional
    @GlobalTransactional
    public void create(Long uid, BigDecimal money) {
        log.error("开始创建订单");
        orderMapper.createOrder(uid);
        log.error("创建订单结束");

//        if(uid.equals(1L)){
//            throw new RuntimeException("失败模拟");
//        }
        //远程调用账户系统扣减金额
        log.error("开始远程调用");
        orderFeinClient.reduce(uid,money);
        log.error("远程调用结束");
    }
}
