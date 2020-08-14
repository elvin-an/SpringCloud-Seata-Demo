package cn.senlin.yu.account.mapper;

import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

public interface AccountMapper {

    /**
     * 扣减金额
     * @param uid 被扣减用户
     * @param money 扣减的金额
     * @return
     */
    int reduce(@Param("uid") Long uid, @Param("money") BigDecimal money);
}
