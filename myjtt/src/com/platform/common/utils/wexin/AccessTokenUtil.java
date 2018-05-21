package com.platform.common.utils.wexin;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.platform.modules.wexin.dao.WxTokenDao;
import com.platform.modules.wexin.entity.WxToken;

@Service("accessToken")
@Lazy(false)
public class AccessTokenUtil {
	private static final Logger logger = Logger.getLogger(AccessTokenUtil.class);

	@Resource
	private WxTokenDao wxTokenDao;

	@Scheduled(initialDelay = 0, fixedRate = 1000 * 60 * 115)
	public void runJob() {
		// 获得token
		try {
			String requestUrl = WeixinUtil.ACCESS_TOKEN_URL.replace("APPID", WeixinConstans.WEIXIN_APP_ID)
					.replace("APPSECRET", WeixinConstans.WEIXIN_APP_SECRET);
			JSONObject jsonObject = WeixinUtil.httpRequest(requestUrl, "GET", null);
			if (jsonObject != null) {
				WxToken token = WxToken.getInstance();
				token.setWxToken(jsonObject.getString("access_token"));
				token.setReqTime(new Date().getTime() + 1000 * 60 * 60);
				wxTokenDao.update(token);
				logger.info("请求获得access_token接口成功");
			} else {
				logger.info("请求获得access_token接口失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
