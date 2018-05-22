package cn.linkmore.common.client.hystrix;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import cn.linkmore.common.client.BaseDictClient;
import cn.linkmore.common.request.ReqBaseDict;
import cn.linkmore.common.response.ResBaseDict;

/**
 * 数据词典容错
 * @author   GFF
 * @Date     2018年5月18日
 * @Version  v2.0
 */
@Component
public class BaseDictClientHystrix implements BaseDictClient {
	
	private  final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public List<ResBaseDict> selectList(@PathVariable("code") String code) {
		log.info("common service dict  selectList(String code) hystrix");
		return null;
	}

	@Override
	public void save(ReqBaseDict baseDict) {
		log.info("common service dict  save(ReqBaseDict baseDict) hystrix");
	}

	@Override
	public void update(ReqBaseDict baseDict) {
		log.info("common service dict  update(ReqBaseDict baseDict) hystrix");
	}

	@Override
	public void update(Long id) {
		log.info("common service dict  update(Long id) hystrix");
	}

	
}
