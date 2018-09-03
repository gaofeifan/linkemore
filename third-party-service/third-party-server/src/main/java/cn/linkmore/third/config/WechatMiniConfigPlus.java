package cn.linkmore.third.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Config - 小程序配置
 * @author cl
 * @version 3.0
 */
@ConfigurationProperties(prefix = "wechat-mini-plus")
@Component
public class WechatMiniConfigPlus {

	private Map<String, String> miniProps = new HashMap<>();

	public Map<String, String> getMiniProps() {
		return miniProps;
	}

	public void setMiniProps(Map<String, String> miniProps) {
		this.miniProps = miniProps;
	}
	
	
	
}
