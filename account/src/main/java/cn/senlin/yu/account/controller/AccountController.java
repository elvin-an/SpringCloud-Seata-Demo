package cn.senlin.yu.account.controller;

import cn.senlin.yu.account.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @RequestMapping("/reduce")
    public String reduce(Long uid, BigDecimal money){
        accountService.reduce(uid,money);
        return "账户扣减成功";
    }
}
