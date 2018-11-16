/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：OpcOssServiceImpl.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.jemmy.services.order.service.impl;

import com.jemmy.apis.omc.exceptions.OmcBizException;
import com.jemmy.apis.opc.model.dto.oss.OptGetUrlRequest;
import com.jemmy.apis.opc.model.dto.oss.OptUploadFileReqDto;
import com.jemmy.apis.opc.model.dto.oss.OptUploadFileRespDto;
import com.jemmy.apis.opc.service.OpcOssFeignApi;
import com.jemmy.common.base.enums.ErrorCodeEnum;
import com.jemmy.common.util.wrapper.MvcResult;
import com.jemmy.services.order.service.OpcOssService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * The class Opc oss service.
 *
 * @author paascloud.net@gmail.com
 */
@Slf4j
@Service
public class OpcOssServiceImpl implements OpcOssService {
	@Resource
	private OpcOssFeignApi opcOssFeignApi;

	@Override
	public OptUploadFileRespDto uploadFile(OptUploadFileReqDto optUploadFileReqDto) {
		log.info("uploadFile - 上传附件. optUploadFileReqDto={}", optUploadFileReqDto);
		MvcResult<OptUploadFileRespDto> mvcResult = opcOssFeignApi.uploadFile(optUploadFileReqDto);
		if (null == mvcResult || mvcResult.error()) {
			throw new OmcBizException(ErrorCodeEnum.OMC10031012);
		}
		return mvcResult.getResult();
	}

	@Override
	public String getFileUrl(OptGetUrlRequest optGetUrlRequest) {
		log.info("getFileUrl - 获取附件地址. optUploadFileReqDto={}", optGetUrlRequest);
		MvcResult<String> mvcResult = opcOssFeignApi.getFileUrl(optGetUrlRequest);
		if (null == mvcResult || mvcResult.error()) {
			throw new OmcBizException(ErrorCodeEnum.OMC10031013);
		}
		return mvcResult.getResult();
	}
}
