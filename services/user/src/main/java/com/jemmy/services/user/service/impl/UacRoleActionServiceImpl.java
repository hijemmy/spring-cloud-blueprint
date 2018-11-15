package com.jemmy.services.user.service.impl;


import com.jemmy.apis.user.exceptions.UacBizException;
import com.jemmy.common.base.enums.ErrorCodeEnum;
import com.jemmy.common.core.support.BaseService;
import com.jemmy.services.user.mapper.UacRoleActionMapper;
import com.jemmy.services.user.model.domain.UacRoleAction;
import com.jemmy.services.user.service.UacRoleActionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * The class Uac role action service.
 *
 * @author paascloud.net@gmail.com
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UacRoleActionServiceImpl extends BaseService<UacRoleAction,UacRoleActionMapper> implements UacRoleActionService {

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<UacRoleAction> listByRoleId(Long roleId) {
		if (roleId == null) {
			throw new UacBizException(ErrorCodeEnum.UAC10012001);
		}
		UacRoleAction roleMenu = new UacRoleAction();
		roleMenu.setRoleId(roleId);
		return mapper.select(roleMenu);
	}

	@Override
	public void deleteByRoleId(Long roleId) {
		if (roleId == null) {
			throw new UacBizException(ErrorCodeEnum.UAC10012001);
		}
		UacRoleAction roleMenu = new UacRoleAction();
		roleMenu.setRoleId(roleId);
		mapper.delete(roleMenu);
	}

	@Override
	public void insert(Long roleId, Set<Long> actionIdList) {
		if (roleId == null) {
			throw new UacBizException(ErrorCodeEnum.UAC10012001);
		}
		UacRoleAction uacRoleAction = new UacRoleAction();
		uacRoleAction.setRoleId(roleId);
		for (Long actionId : actionIdList) {
			uacRoleAction.setActionId(actionId);
			mapper.insert(uacRoleAction);
		}
	}

	@Override
	public int deleteByRoleIdList(final List<Long> roleIdList) {
		return mapper.deleteByRoleIdList(roleIdList);
	}
}
