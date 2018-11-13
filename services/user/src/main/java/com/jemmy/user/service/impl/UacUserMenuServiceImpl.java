package com.jemmy.user.service.impl;


import com.jemmy.common.core.support.BaseService;
import com.jemmy.user.mapper.UacUserMenuMapper;
import com.jemmy.user.model.domain.UacUserMenu;
import com.jemmy.user.service.UacUserMenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * The class Uac user menu service.
 *
 * @author paascloud.net@gmail.com
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UacUserMenuServiceImpl extends BaseService<UacUserMenu,UacUserMenuMapper> implements UacUserMenuService {
}
