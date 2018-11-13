package com.jemmy.user.service.impl;

import com.jemmy.common.core.support.BaseService;
import com.jemmy.user.mapper.UacGroupUserMapper;
import com.jemmy.user.model.domain.UacGroup;
import com.jemmy.user.model.domain.UacGroupUser;
import com.jemmy.user.service.UacGroupUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * The class Uac group user service.
 *
 * @author paascloud.net@gmail.com
 */
@Service
public class UacGroupUserServiceImpl extends BaseService<UacGroupUser,UacGroupUserMapper> implements UacGroupUserService {

	@Override
	public UacGroupUser queryByUserId(Long userId) {
		return mapper.getByUserId(userId);
	}

	@Override
	public int updateByUserId(UacGroupUser uacGroupUser) {
		return mapper.updateByUserId(uacGroupUser);
	}

	@Override
	public List<UacGroup> getGroupListByUserId(Long userId) {
		return mapper.selectGroupListByUserId(userId);
	}

	@Override
	public void saveUserGroup(Long userId, Long groupId) {
		UacGroupUser groupUser = new UacGroupUser();
		groupUser.setUserId(userId);
		groupUser.setGroupId(groupId);
		mapper.insertSelective(groupUser);
	}
}
