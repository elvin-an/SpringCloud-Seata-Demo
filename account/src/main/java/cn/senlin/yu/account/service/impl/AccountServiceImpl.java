package cn.senlin.yu.account.service.impl;

import cn.senlin.yu.account.mapper.AccountMapper;
import cn.senlin.yu.account.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Slf4j
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountMapper accountMapper;

    @Override
    public int reduce(Long uid, BigDecimal money) {
        log.error("扣减金额开始");
        //扣减金额
        accountMapper.reduce(uid,money);
        log.error("扣减金额结束");
        if(uid.equals(1L)){
            throw new RuntimeException("失败模拟");
        }
        return 1;
    }
}
