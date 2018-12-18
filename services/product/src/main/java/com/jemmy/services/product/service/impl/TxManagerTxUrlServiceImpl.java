package com.jemmy.services.product.service.impl;

import com.codingapi.tx.config.service.TxManagerTxUrlService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author Jemmy
 */
@Service
@Slf4j
public class TxManagerTxUrlServiceImpl implements TxManagerTxUrlService {


    @Value("${tm.manager.url}")
    private String url;

    @Override
    public String getTxUrl() {
        log.debug("load tm.manager.url ");
        return url;
    }
}
