package com.jemmy.user.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.arronlong.httpclientutil.HttpClientUtil;
import com.arronlong.httpclientutil.common.HttpConfig;
import com.arronlong.httpclientutil.common.HttpHeader;
import com.arronlong.httpclientutil.exception.HttpProcessException;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.jemmy.common.base.dto.UserTokenDto;
import com.jemmy.common.core.support.BaseService;
import com.jemmy.common.core.utils.RequestUtil;
import com.jemmy.common.security.core.properties.OAuth2ClientProperties;
import com.jemmy.common.security.core.properties.SecurityProperties;
import com.jemmy.common.util.PublicUtil;
import com.jemmy.common.util.RedisKeyUtil;
import com.jemmy.user.mapper.UacUserTokenMapper;
import com.jemmy.user.model.dto.token.TokenMainQueryDto;
import com.jemmy.user.model.enums.UacUserTokenStatusEnum;
import com.jemmy.user.service.OpcRpcService;
import com.jemmy.user.service.UacUserService;
import com.jemmy.user.service.UacUserTokenService;
import com.jemmy.common.base.dto.LoginAuthDto;
import com.jemmy.user.model.domain.UacUser;
import com.jemmy.user.model.domain.UacUserToken;
import eu.bitwalker.useragentutils.UserAgent;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


/**
 * The class Uac user token service.
 *
 * @author paascloud.net @gmail.com
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UacUserTokenServiceImpl extends BaseService<UacUserToken,UacUserTokenMapper> implements UacUserTokenService {

	@Resource
	private UacUserService uacUserService;
	@Autowired
	private SecurityProperties securityProperties;
	@Resource
	private RedisTemplate<String, Object> redisTemplate;
	@Resource
	private OpcRpcService opcRpcService;
	@Value("${jemmycloud.auth.refresh-token-url}")
	private String refreshTokenUrl;

	@Override
	public void saveUserToken(String accessToken, String refreshToken, LoginAuthDto loginAuthDto, HttpServletRequest request) {
		// 获取登录时间
		Long userId = loginAuthDto.getUserId();
		UacUser uacUser = uacUserService.selectByKey(userId);
		final UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
		//获取客户端操作系统
		final String os = userAgent.getOperatingSystem().getName();
		//获取客户端浏览器
		final String browser = userAgent.getBrowser().getName();
		final String remoteAddr = RequestUtil.getRemoteAddr(request);
		// 根据IP获取位置信息
		final String remoteLocation = opcRpcService.getLocationById(remoteAddr);

		// 存入mysql数据库
		UacUserToken uacUserToken = new UacUserToken();
		OAuth2ClientProperties[] clients = securityProperties.getOauth2().getClients();
		int accessTokenValidateSeconds = clients[0].getAccessTokenValidateSeconds();
		int refreshTokenValiditySeconds = clients[0].getRefreshTokenValiditySeconds();
		uacUserToken.setOs(os);
		uacUserToken.setBrowser(browser);
		uacUserToken.setAccessToken(accessToken);
		uacUserToken.setAccessTokenValidity(accessTokenValidateSeconds);
		uacUserToken.setLoginIp(remoteAddr);
		uacUserToken.setLoginLocation(remoteLocation);
		uacUserToken.setLoginTime(uacUser.getLastLoginTime());
		uacUserToken.setLoginName(loginAuthDto.getLoginName());
		uacUserToken.setRefreshToken(refreshToken);
		uacUserToken.setRefreshTokenValidity(refreshTokenValiditySeconds);
		uacUserToken.setStatus(UacUserTokenStatusEnum.ON_LINE.getStatus());
		uacUserToken.setUserId(userId);
		uacUserToken.setUserName(loginAuthDto.getUserName());
		uacUserToken.setUpdateInfo(loginAuthDto);
		uacUserToken.setGroupId(loginAuthDto.getGroupId());
		uacUserToken.setGroupName(loginAuthDto.getGroupName());
		uacUserToken.setId(generateId());
		mapper.insertSelective(uacUserToken);
		UserTokenDto userTokenDto = new ModelMapper().map(uacUserToken, UserTokenDto.class);
		// 存入redis数据库
		updateRedisUserToken(accessToken, accessTokenValidateSeconds, userTokenDto);
	}

	private void updateRedisUserToken(String accessToken, int accessTokenValidateSeconds, UserTokenDto userTokenDto) {
		redisTemplate.opsForValue().set(RedisKeyUtil.getAccessTokenKey(accessToken), userTokenDto, accessTokenValidateSeconds, TimeUnit.SECONDS);
	}

	@Override
	public UserTokenDto getByAccessToken(String accessToken) {
		UserTokenDto userTokenDto = (UserTokenDto) redisTemplate.opsForValue().get(RedisKeyUtil.getAccessTokenKey(accessToken));
		if (userTokenDto == null) {
			UacUserToken uacUserToken = new UacUserToken();
			uacUserToken.setAccessToken(accessToken);
			uacUserToken = mapper.selectOne(uacUserToken);
			userTokenDto = new ModelMapper().map(uacUserToken, UserTokenDto.class);
		}
		return userTokenDto;
	}

	@Override
	public void updateUacUserToken(UserTokenDto tokenDto, LoginAuthDto loginAuthDto) {
		UacUserToken uacUserToken = new ModelMapper().map(tokenDto, UacUserToken.class);
		uacUserToken.setUpdateInfo(loginAuthDto);
		mapper.updateByPrimaryKeySelective(uacUserToken);
		OAuth2ClientProperties[] clients = securityProperties.getOauth2().getClients();
		int accessTokenValidateSeconds = clients[0].getAccessTokenValidateSeconds();
		updateRedisUserToken(uacUserToken.getAccessToken(), accessTokenValidateSeconds, tokenDto);
	}

	@Override
	public PageInfo listTokenWithPage(TokenMainQueryDto token) {
		PageHelper.startPage(token.getPageNum(), token.getPageSize());
		UacUserToken userToken = new UacUserToken();
		userToken.setStatus(token.getStatus());
		if (token.getStatus() != null) {
			userToken.setStatus(token.getStatus());
		}

		if (StringUtils.isNotBlank(token.getLoginName())) {
			userToken.setLoginName(token.getLoginName());
		}
		if (StringUtils.isNotBlank(token.getUserName())) {
			userToken.setUserName(token.getUserName());
		}
		List<UacUserToken> userTokenList = mapper.selectTokenList(userToken);
		return new PageInfo<>(userTokenList);
	}

	@Override
	public String refreshToken(String accessToken, String refreshToken, HttpServletRequest request) throws HttpProcessException {
		String token;
		Map<String, Object> map = new HashMap<>(2);
		map.put("grant_type", "refresh_token");
		map.put("refresh_token", refreshToken);

		//插件式配置请求参数（网址、请求参数、编码、client）
		Header[] headers = HttpHeader.custom().contentType(HttpHeader.Headers.APP_FORM_URLENCODED).authorization(request.getHeader(HttpHeaders.AUTHORIZATION)).build();
		HttpConfig config = HttpConfig.custom().headers(headers).url(refreshTokenUrl).map(map);
		token = HttpClientUtil.post(config);
		JSONObject jsonObj = JSON.parseObject(token);
		String accessTokenNew = (String) jsonObj.get("access_token");
		String refreshTokenNew = (String) jsonObj.get("refresh_token");
		String loginName = (String) jsonObj.get("loginName");
		// 更新本次token数据
		UserTokenDto tokenDto = this.getByAccessToken(accessToken);
		tokenDto.setStatus(UacUserTokenStatusEnum.ON_REFRESH.getStatus());
		UacUser uacUser = uacUserService.findUserInfoByLoginName(loginName);

		LoginAuthDto loginAuthDto = new LoginAuthDto(uacUser.getId(), uacUser.getLoginName(), uacUser.getUserName(), uacUser.getGroupId(), uacUser.getGroupName());
		this.updateUacUserToken(tokenDto, loginAuthDto);
		// 创建刷新token
		this.saveUserToken(accessTokenNew, refreshTokenNew, loginAuthDto, request);
		return token;
	}

	@Override
	public int batchUpdateTokenOffLine() {
		List<Long> idList = mapper.listOffLineTokenId();
		if (PublicUtil.isEmpty(idList)) {
			return 1;
		}
		Map<String, Object> map = Maps.newHashMap();
		map.put("status", 20);
		map.put("tokenIdList", idList);
		return mapper.batchUpdateTokenOffLine(map);
	}
}
