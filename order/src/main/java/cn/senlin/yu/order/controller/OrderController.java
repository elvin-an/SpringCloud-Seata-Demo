package cn.senlin.yu.order.controller;

import cn.senlin.yu.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @RequestMapping("/create")
    public String createOrder(Long uid, BigDecimal money){
        try{
            orderService.create(uid,money);
            return "订单创建成功";
        }catch (Exception e){
            return "订单创建失败";
        }
    }
}
