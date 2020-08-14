# SpringCloud-Seata-Demo
SpringCloud-Seata初体验

**整合seata主要步骤**
 1. 下载seata server并配置
 2. 创建seata 依赖表 branch_table、global_table和lock_table
 3. 数据源代理设置
 4. 启动注册中心、启动server和启动client
 
 **详细步骤**
 下载seata server
    https://github.com/seata/seata/releases
    修改conf目录下的配置文件file.conf
    将mode 改为db模式，创建依赖表
  ````
    CREATE TABLE IF NOT EXISTS `global_table`
    (
        `xid`                       VARCHAR(128) NOT NULL,
        `transaction_id`            BIGINT,
        `status`                    TINYINT      NOT NULL,
        `application_id`            VARCHAR(32),
        `transaction_service_group` VARCHAR(32),
        `transaction_name`          VARCHAR(128),
        `timeout`                   INT,
        `begin_time`                BIGINT,
        `application_data`          VARCHAR(2000),
        `gmt_create`                DATETIME,
        `gmt_modified`              DATETIME,
        PRIMARY KEY (`xid`),
        KEY `idx_gmt_modified_status` (`gmt_modified`, `status`),
        KEY `idx_transaction_id` (`transaction_id`)
    ) ENGINE = InnoDB
      DEFAULT CHARSET = utf8;
    
    -- the table to store BranchSession data
    CREATE TABLE IF NOT EXISTS `branch_table`
    (
        `branch_id`         BIGINT       NOT NULL,
        `xid`               VARCHAR(128) NOT NULL,
        `transaction_id`    BIGINT,
        `resource_group_id` VARCHAR(32),
        `resource_id`       VARCHAR(256),
        `branch_type`       VARCHAR(8),
        `status`            TINYINT,
        `client_id`         VARCHAR(64),
        `application_data`  VARCHAR(2000),
        `gmt_create`        DATETIME(6),
        `gmt_modified`      DATETIME(6),
        PRIMARY KEY (`branch_id`),
        KEY `idx_xid` (`xid`)
    ) ENGINE = InnoDB
      DEFAULT CHARSET = utf8;
    
    -- the table to store lock data
    CREATE TABLE IF NOT EXISTS `lock_table`
    (
        `row_key`        VARCHAR(128) NOT NULL,
        `xid`            VARCHAR(96),
        `transaction_id` BIGINT,
        `branch_id`      BIGINT       NOT NULL,
        `resource_id`    VARCHAR(256),
        `table_name`     VARCHAR(32),
        `pk`             VARCHAR(36),
        `gmt_create`     DATETIME,
        `gmt_modified`   DATETIME,
        PRIMARY KEY (`row_key`),
        KEY `idx_branch_id` (`branch_id`)
    ) ENGINE = InnoDB
      DEFAULT CHARSET = utf8;
   ````
    配置数据库信息 自定义分组：
   ````
   service {
          #vgroup->rgroup
          vgroup_mapping.fsp_tx_group = "default"  修改这里，fsp_tx_group这个事务组名称是我自定义的，一定要与client端的这个配置一致！否则会报错！
          #only support single node
          default.grouplist = "127.0.0.1:8091" 
          #degrade current not support
          enableDegrade = false
          #disable
          disable = false
          #unit ms,s,m,h,d represents milliseconds, seconds, minutes, hours, days, default permanent
          max.commit.retry.timeout = "-1"
          max.rollback.retry.timeout = "-1"
        }
    ````
    修改registry.conf 注册方式更改为eureka
    
   cilent配置见代码中 ：注意file.conf配置和server不同
   
   配置数据代理 注意在启动类上排除 DataSourceAutoConfiguration.class
   最后在事务发起方的方法上加上@GlobalTransactional注解
   业务方数据库需要加上undo_log表
    CREATE TABLE IF NOT EXISTS `undo_log`
    (
        `branch_id`     BIGINT(20)   NOT NULL COMMENT 'branch transaction id',
        `xid`           VARCHAR(100) NOT NULL COMMENT 'global transaction id',
        `context`       VARCHAR(128) NOT NULL COMMENT 'undo_log context,such as serialization',
        `rollback_info` LONGBLOB     NOT NULL COMMENT 'rollback info',
        `log_status`    INT(11)      NOT NULL COMMENT '0:normal status,1:defense status',
        `log_created`   DATETIME(6)  NOT NULL COMMENT 'create datetime',
        `log_modified`  DATETIME(6)  NOT NULL COMMENT 'modify datetime',
        UNIQUE KEY `ux_undo_log` (`xid`, `branch_id`)
    ) ENGINE = InnoDB
      AUTO_INCREMENT = 1
      DEFAULT CHARSET = utf8 COMMENT ='AT transaction mode undo table';

    以上均已mysql为例子
    
    