/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：UacGroupCommonController.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.jemmy.services.user.web.admin;

import com.jemmy.apis.user.enums.UacGroupTypeEnum;
import com.jemmy.common.base.dto.LoginAuthDto;
import com.jemmy.common.core.support.BaseController;
import com.jemmy.common.util.wrapper.MvcResult;
import com.jemmy.common.util.wrapper.MvcResultBuilder;
import com.jemmy.services.user.model.domain.UacGroup;
import com.jemmy.services.user.model.dto.group.CheckGroupCodeDto;
import com.jemmy.services.user.model.dto.group.CheckGroupNameDto;
import com.jemmy.services.user.model.vo.GroupZtreeVo;
import com.jemmy.services.user.service.UacGroupService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 组织相关公用接口
 *
 * @author paascloud.net @gmail.com
 */
@RestController
@RequestMapping(value = "/group", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "Web - UacGroupCommonController", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UacGroupCommonController extends BaseController {

	@Resource
	private UacGroupService uacGroupService;

	/**
	 * 根据当前登录人查询组织列表
	 *
	 * @return the group tree by id
	 */
	@PostMapping(value = "/getGroupTree")
	@ApiOperation(httpMethod = "POST", value = "根据当前登录人查询组织列表")
	public MvcResult<List<GroupZtreeVo>> getGroupTreeById() {

		logger.info("根据当前登录人查询组织列表");
		LoginAuthDto loginAuthDto = super.getLoginAuthDto();
		Long groupId = loginAuthDto.getGroupId();
		UacGroup uacGroup = uacGroupService.queryById(groupId);
		List<GroupZtreeVo> tree = uacGroupService.getGroupTree(uacGroup.getId());
		return MvcResultBuilder.wrap(MvcResult.SUCCESS_CODE, "操作成功", tree);
	}

	/**
	 * 通过组织ID查询组织树
	 *
	 * @param groupId the group id
	 *
	 * @return the group tree by id
	 */
	@PostMapping(value = "/getGroupTree/{groupId}")
	@ApiOperation(httpMethod = "POST", value = "通过组织ID查询组织列表")
	public MvcResult<List<GroupZtreeVo>> getGroupTreeById(@ApiParam(name = "groupId", value = "通过组织ID查询组织列表") @PathVariable Long groupId) {

		logger.info("通过组织ID查询组织列表 groupId={}", groupId);

		List<GroupZtreeVo> tree = uacGroupService.getGroupTree(groupId);
		return MvcResultBuilder.wrap(MvcResult.SUCCESS_CODE, "操作成功", tree);
	}

	/**
	 * Check group name with edit wrapper.
	 *
	 * @param checkGroupNameDto the check group name dto
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/checkGroupName")
	@ApiOperation(httpMethod = "POST", value = "编辑校验组织名唯一性")
	public MvcResult<Boolean> checkGroupName(@ApiParam(name = "checkGroupName", value = "组织名称") @RequestBody CheckGroupNameDto checkGroupNameDto) {
		logger.info("校验组织名称唯一性 checkGroupNameDto={}", checkGroupNameDto);

		Long id = checkGroupNameDto.getGroupId();
		String groupName = checkGroupNameDto.getGroupName();

		Example example = new Example(UacGroup.class);
		Example.Criteria criteria = example.createCriteria();

		if (id != null) {
			criteria.andNotEqualTo("id", id);
		}
		criteria.andEqualTo("groupName", groupName);

		int result = uacGroupService.selectCountByExample(example);
		return MvcResultBuilder.ok(result < 1);
	}

	/**
	 * 修改时验证组织编码
	 *
	 * @param checkGroupCodeDto the check group code dto
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/checkGroupCode")
	@ApiOperation(httpMethod = "POST", value = "修改校验组织编码唯一性")
	public MvcResult<Boolean> checkGroupCode(@ApiParam(name = "checkGroupCode", value = "组织相关信息") @RequestBody CheckGroupCodeDto checkGroupCodeDto) {
		logger.info("校验组织编码唯一性 checkGroupCodeDto={}", checkGroupCodeDto);

		Long id = checkGroupCodeDto.getGroupId();
		String groupCode = checkGroupCodeDto.getGroupCode();

		Example example = new Example(UacGroup.class);
		Example.Criteria criteria = example.createCriteria();

		if (id != null) {
			criteria.andNotEqualTo("id", id);
		}
		criteria.andEqualTo("groupCode", groupCode);

		int result = uacGroupService.selectCountByExample(example);
		return MvcResultBuilder.ok(result < 1);
	}

	/**
	 * 查询组织类型
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "queryGroupType")
	@ApiOperation(httpMethod = "POST", value = "查询组织类型")
	public MvcResult<List<Map<String, String>>> queryGroupType() {
		List<Map<String, String>> groupTypeList = UacGroupTypeEnum.getMap2List();
		return MvcResultBuilder.wrap(MvcResult.SUCCESS_CODE, MvcResult.SUCCESS_MESSAGE, groupTypeList);
	}

}