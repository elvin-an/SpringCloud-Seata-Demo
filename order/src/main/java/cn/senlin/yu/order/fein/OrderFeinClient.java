package cn.senlin.yu.order.fein;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@FeignClient(value = "account")
public interface OrderFeinClient {

    @RequestMapping(value = "/account/reduce")
    String reduce(@RequestParam("uid") Long uid, @RequestParam("money") BigDecimal money);
}
