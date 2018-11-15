package com.jemmy.services.user.service.impl;


import com.jemmy.common.core.support.BaseService;
import com.jemmy.services.user.mapper.UacUserMenuMapper;
import com.jemmy.services.user.model.domain.UacUserMenu;
import com.jemmy.services.user.service.UacUserMenuService;
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
