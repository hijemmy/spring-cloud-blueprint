/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：OpcAttachmentFeignClient.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.jemmy.services.opc.web.rpc;

import com.jemmy.apis.opc.exceptions.OpcBizException;
import com.jemmy.apis.opc.model.dto.oss.*;
import com.jemmy.apis.opc.service.OpcOssFeignApi;
import com.jemmy.common.base.exception.BusinessException;
import com.jemmy.common.core.support.BaseController;
import com.jemmy.common.util.wrapper.MvcResult;
import com.jemmy.common.util.wrapper.MvcResultBuilder;
import com.jemmy.services.opc.model.domain.OptAttachment;
import com.jemmy.services.opc.service.OpcAttachmentService;
import com.jemmy.services.opc.service.OpcOssService;
import com.qiniu.common.QiniuException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

/**
 * The class Opc attachment feign client.
 *
 * @author paascloud.net@gmail.com
 */
@RestController
@Api(value = "API - OpcAttachmentFeignClient", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class OpcAttachmentFeignClient extends BaseController implements OpcOssFeignApi {

	@Resource
	private OpcAttachmentService opcAttachmentService;
	@Resource
	private OpcOssService opcOssService;

	@Override
	@ApiOperation(httpMethod = "POST", value = "上传文件")
	public MvcResult<OptUploadFileRespDto> uploadFile(@RequestBody OptUploadFileReqDto optUploadFileReqDto) throws OpcBizException {
		OptUploadFileRespDto result;
		try {
			logger.info("rpcUploadFile - RPC上传附件信息. optUploadFileReqDto={}", optUploadFileReqDto);
			result = opcAttachmentService.rpcUploadFile(optUploadFileReqDto);
		} catch (BusinessException ex) {
			logger.error("RPC上传附件信息, 出现异常={}", ex.getMessage(), ex);
			return MvcResultBuilder.wrap(ex);
		} catch (Exception e) {
			logger.error("RPC上传附件信息, 出现异常={}", e.getMessage(), e);
			return MvcResultBuilder.error();
		}
		return MvcResultBuilder.wrap(MvcResult.SUCCESS_CODE, "操作成功", result);
	}

	@Override
	@ApiOperation(httpMethod = "POST", value = "获取附件完整路径")
	public MvcResult<String> getFileUrl(@RequestBody OptGetUrlRequest optGetUrlRequest) {
		String result;
		try {
			logger.info("getFileUrl - 获取附件完整路径. optGetUrlRequest={}", optGetUrlRequest);
			result = opcAttachmentService.rpcGetFileUrl(optGetUrlRequest);
		} catch (BusinessException ex) {
			logger.error("RPC获取附件完整路径, 出现异常={}", ex.getMessage(), ex);
			return MvcResultBuilder.wrap(ex);
		} catch (Exception e) {
			logger.error("RPC获取附件完整路径, 出现异常={}", e.getMessage(), e);
			return MvcResultBuilder.error();
		}
		return MvcResultBuilder.ok(result);
	}

	@Override
	public MvcResult<List<ElementImgUrlDto>> listFileUrl(@RequestBody OptBatchGetUrlRequest urlRequest) {
		logger.info("getFileUrl - 批量获取url链接. urlRequest={}", urlRequest);
		List<ElementImgUrlDto> result = opcAttachmentService.listFileUrl(urlRequest);
		return MvcResultBuilder.ok(result);
	}

	@Override
	public MvcResult<OptUploadFileRespDto> handleFileUpload(@RequestPart(value = "file") MultipartFile file) {
		return null;
	}

	@Override
	public void deleteExpireFile() {
		// 1.查询过期的文件
		List<OptAttachment> list = opcAttachmentService.listExpireFile();
		// 2.删除这些文件
		for (final OptAttachment attachment : list) {
			try {
				opcAttachmentService.deleteFile(attachment.getPath() + attachment.getName(), attachment.getBucketName(), attachment.getId());
			} catch (QiniuException e) {
				logger.info("删除文件失败, attachmentId={}", attachment.getId(), e);
			}
		}
	}
}
