package cn.senlin.yu.account.service;


import java.math.BigDecimal;

public interface AccountService {

    int reduce(Long uid, BigDecimal money);
}
