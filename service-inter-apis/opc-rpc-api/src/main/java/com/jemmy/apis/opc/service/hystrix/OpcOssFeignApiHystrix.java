/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：OpcOssFeignApiHystrix.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.jemmy.apis.opc.service.hystrix;

import com.jemmy.apis.opc.model.dto.oss.*;
import com.jemmy.apis.opc.service.OpcOssFeignApi;
import com.jemmy.common.util.wrapper.MvcResult;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * The class Opc oss feign api hystrix.
 *
 * @author paascloud.net@gmail.com
 */
@Component
public class OpcOssFeignApiHystrix implements OpcOssFeignApi {
	@Override
	public MvcResult<OptUploadFileRespDto> uploadFile(final OptUploadFileReqDto optUploadFileReqDto) {
		return null;
	}

	@Override
	public MvcResult<String> getFileUrl(final OptGetUrlRequest optGetUrlRequest) {
		return null;
	}

	@Override
	public MvcResult<List<ElementImgUrlDto>> listFileUrl(final OptBatchGetUrlRequest urlRequest) {
		return null;
	}

	@Override
	public MvcResult<OptUploadFileRespDto> handleFileUpload(final MultipartFile file) {
		return null;
	}

	@Override
	public void deleteExpireFile() {

	}
}
