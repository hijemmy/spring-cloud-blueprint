package com.jemmy.services.user.service.impl;

import com.jemmy.apis.user.exceptions.UacBizException;
import com.jemmy.common.base.enums.ErrorCodeEnum;
import com.jemmy.common.core.support.BaseService;
import com.jemmy.services.user.mapper.UacRoleMenuMapper;
import com.jemmy.services.user.model.domain.UacRoleMenu;
import com.jemmy.services.user.service.UacRoleMenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * The class Uac role menu service.
 *
 * @author paascloud.net@gmail.com
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UacRoleMenuServiceImpl extends BaseService<UacRoleMenu,UacRoleMenuMapper> implements UacRoleMenuService {


	@Override
	public int delRoleMenuList(Set<UacRoleMenu> uacRoleMenus) {
		int result = 0;
		for (UacRoleMenu uacRoleMenu : uacRoleMenus) {
			result += mapper.delete(uacRoleMenu);
		}
		return result;
	}

	@Override
	public void deleteByRoleId(Long roleId) {
		if (roleId == null) {
			throw new UacBizException(ErrorCodeEnum.UAC10012001);
		}
		UacRoleMenu roleMenu = new UacRoleMenu();
		roleMenu.setRoleId(roleId);
		mapper.delete(roleMenu);
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<UacRoleMenu> listByRoleId(Long roleId) {
		if (roleId == null) {
			throw new UacBizException(ErrorCodeEnum.UAC10012001);
		}
		UacRoleMenu roleMenu = new UacRoleMenu();
		roleMenu.setRoleId(roleId);
		return mapper.select(roleMenu);
	}

	@Override
	public void insert(Long roleId, Set<Long> menuIdList) {
		if (roleId == null) {
			throw new UacBizException(ErrorCodeEnum.UAC10012001);
		}
		UacRoleMenu uacRoleMenu = new UacRoleMenu();
		uacRoleMenu.setRoleId(roleId);
		for (Long menuId : menuIdList) {
			uacRoleMenu.setMenuId(menuId);
			mapper.insertSelective(uacRoleMenu);
		}
	}

	@Override
	public void deleteByRoleIdList(final List<Long> roleIdList) {
		mapper.deleteByRoleIdList(roleIdList);
	}
}
