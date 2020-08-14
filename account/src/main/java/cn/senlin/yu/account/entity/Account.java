package cn.senlin.yu.account.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Account {

    private Long id;

    private Long uid;

    private BigDecimal balance;
}
