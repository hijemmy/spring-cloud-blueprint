package com.jemmy.order.service;

import com.jemmy.common.constants.RedisKeySpaceConstant;
import com.jemmy.order.redisson.DistributedLocker;
import org.apache.commons.lang.RandomStringUtils;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.Instant;

/**
 * @author Jemmy
 */
@Component
public class OrderUtilComponent {

    @Autowired
    private DistributedLocker locker;


    public String generateOrderCode(Long uid){
        String lockKey=RedisKeySpaceConstant.LOCK_ORDER_NUMBER;
        String _uid=uid.toString();
        String part2=_uid.length()>=4?_uid.substring(4):String.format("%04d",uid);
        String part3=RandomStringUtils.randomNumeric(2);
        SimpleDateFormat format=new SimpleDateFormat("yyyyMMddHHmmss");
        RLock lock=locker.lock(lockKey);
        try{
            String part1=format.format(Date.from(Instant.now()));
            return part1+part2+part3;
        }finally {
            if(null!=lock){
                lock.unlock();
            }
        }
    }

}
