package com.jemmy.services.user.service.impl;

import com.google.common.base.Preconditions;
import com.jemmy.apis.product.exceptions.MdcBizException;
import com.jemmy.apis.product.model.dto.AddressDTO;
import com.jemmy.apis.product.service.MdcAddressQueryFeignApi;
import com.jemmy.common.base.enums.ErrorCodeEnum;
import com.jemmy.common.util.wrapper.MvcResult;
import com.jemmy.services.user.service.MdcAddressService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * The class Mdc address service.
 *
 * @author paascloud.net @gmail.com
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class MdcAddressServiceImpl implements MdcAddressService {
	@Resource
	private MdcAddressQueryFeignApi mdcAddressQueryFeignApi;

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public AddressDTO getAddressById(Long addressId) {
		Preconditions.checkArgument(addressId != null, "地址ID不能为空");
		MvcResult<AddressDTO> mvcResult = mdcAddressQueryFeignApi.getById(addressId);

		if (mvcResult == null) {
			throw new MdcBizException(ErrorCodeEnum.GL99990002);
		}
		if (mvcResult.error()) {
			throw new MdcBizException(ErrorCodeEnum.MDC10021002);
		}
		return mvcResult.getResult();
	}
}
