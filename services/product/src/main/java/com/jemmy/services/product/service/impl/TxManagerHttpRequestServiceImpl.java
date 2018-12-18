package com.jemmy.services.product.service.impl;

import com.codingapi.tx.netty.service.TxManagerHttpRequestService;
import com.lorne.core.framework.utils.http.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author Jemmy
 */
@Service
@Slf4j
public class TxManagerHttpRequestServiceImpl implements TxManagerHttpRequestService {

    @Override
    public String httpGet(String url) {
        log.debug("httpGet-start:url{}",url);
        String res = HttpUtils.get(url);
        log.debug("httpGet-end");
        return res;
    }

    @Override
    public String httpPost(String url, String params) {
        log.debug("httpPost-start:url:{},params:{}",url,params);
        String res = HttpUtils.post(url,params);
        log.debug("httpPost-end");
        return res;
    }
}
