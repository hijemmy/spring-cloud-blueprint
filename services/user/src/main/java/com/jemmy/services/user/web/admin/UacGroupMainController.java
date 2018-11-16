/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：UacGroupMainController.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.jemmy.services.user.web.admin;


import com.jemmy.apis.user.user.IdStatusDto;
import com.jemmy.apis.user.vo.MenuVo;
import com.jemmy.common.base.dto.LoginAuthDto;
import com.jemmy.common.core.annotation.LogAnnotation;
import com.jemmy.common.core.support.BaseController;
import com.jemmy.common.util.wrapper.MvcResult;
import com.jemmy.common.util.wrapper.MvcResultBuilder;
import com.jemmy.services.user.model.domain.UacGroup;
import com.jemmy.services.user.service.UacGroupService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 组织管理主页面
 *
 * @author paascloud.net @gmail.com
 */
@RestController
@RequestMapping(value = "/group", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "Web - UacGroupMainController", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UacGroupMainController extends BaseController {

	@Resource
	private UacGroupService uacGroupService;

	/**
	 * 根据id删除组织
	 *
	 * @param id the id
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/deleteById/{id}")
	@LogAnnotation
	@ApiOperation(httpMethod = "POST", value = "根据id删除组织")
	public MvcResult deleteGroupById(@ApiParam(name = "id", value = "组织id") @PathVariable Long id) {
		logger.info(" 根据id删除组织 id={}", id);
		int result = uacGroupService.deleteUacGroupById(id);
		if (result < 1) {
			return MvcResultBuilder.wrap(MvcResult.ERROR_CODE, "操作失败");
		} else {
			return MvcResultBuilder.wrap(MvcResult.SUCCESS_CODE, "操作成功");
		}
	}

	/**
	 * 根据id修改组织状态
	 *
	 * @param idStatusDto the id status dto
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/modifyStatus")
	@LogAnnotation
	@ApiOperation(httpMethod = "POST", value = "根据id修改组织状态")
	public MvcResult modifyGroupStatus(@ApiParam(name = "modifyGroupStatus", value = "修改状态") @RequestBody IdStatusDto idStatusDto) {
		logger.info("根据id修改组织状态 idStatusDto={}", idStatusDto);
		UacGroup uacGroup = new UacGroup();
		uacGroup.setId(idStatusDto.getId());
		LoginAuthDto loginAuthDto = super.getLoginAuthDto();
		Integer status = idStatusDto.getStatus();
		uacGroup.setStatus(status);
		int result = uacGroupService.updateUacGroupStatusById(idStatusDto, loginAuthDto);
		if (result < 1) {
			return MvcResultBuilder.wrap(MvcResult.ERROR_CODE, "操作失败");
		} else {
			return MvcResultBuilder.wrap(MvcResult.SUCCESS_CODE, "操作成功");
		}
	}


	/**
	 * 获取主页面数据
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/getTree")
	@ApiOperation(httpMethod = "POST", value = "获取菜单树")
	public MvcResult<List<MenuVo>> getTree() {
		Long userId = super.getLoginAuthDto().getUserId();
		List<MenuVo> tree = uacGroupService.getGroupTreeListByUserId(userId);
		return MvcResultBuilder.ok(tree);
	}

	/**
	 * 编辑组织
	 *
	 * @param group the group
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/save")
	@LogAnnotation
	@ApiOperation(httpMethod = "POST", value = "修改组织信息")
	public MvcResult editGroup(@ApiParam(name = "group", value = "组织信息") @RequestBody UacGroup group) {
		LoginAuthDto loginAuthDto = super.getLoginAuthDto();
		uacGroupService.saveUacGroup(group, loginAuthDto);
		return MvcResultBuilder.ok();
	}


	/**
	 * 获取编辑页面数据
	 *
	 * @param id the id
	 *
	 * @return the edit group page info
	 */
	@PostMapping(value = "/queryById/{id}")
	@ApiOperation(httpMethod = "POST", value = "获取编辑页面数据")
	public MvcResult<UacGroup> getEditGroupPageInfo(@ApiParam(name = "id", value = "组织id") @PathVariable Long id) {
		UacGroup uacGroup = uacGroupService.getById(id);
		return MvcResultBuilder.ok(uacGroup);
	}
}
