package cn.linkmore.prefecture.client.hystrix;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

import cn.linkmore.bean.view.Tree;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.prefecture.client.OpsStallClient;
import cn.linkmore.prefecture.request.ReqCheck;
import cn.linkmore.prefecture.request.ReqStall;
import cn.linkmore.prefecture.response.ResStall;
import cn.linkmore.prefecture.response.ResStallEntity;
import cn.linkmore.prefecture.response.ResStallLock;
import cn.linkmore.prefecture.response.ResStallOps;
/**
 * 远程调用实现 - 车位信息
 * @author jiaohanbin
 * @version 2.0
 *
 */ 
@Component
public class OpsStallClientHystrix implements OpsStallClient {
	private  final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public Tree tree(Map<String,Object> param) {
		log.info("prefecture service stall tree() hystrix");
		return null;
	}

	@Override
	public ViewPage list(ViewPageable pageable) {
		log.info("prefecture service stall list() hystrix");
		return null;
	}

	@Override
	public int save(ReqStall stall) {
		log.info("prefecture service stall save() hystrix");
		return 0;
	}

	@Override
	public int update(ReqStall stall) {
		log.info("prefecture service stall update() hystrix");
		return 0;
	}

	@Override
	public int check(ReqCheck reqCheck) {
		log.info("prefecture service stall check() hystrix");
		return 0;
	}

	@Override
	public List<ResStallLock> sn(Long lockId) {
		log.info("prefecture service stall sn() hystrix");
		return null;
	}

	@Override
	public ResStallEntity detail(Long id) {
		log.info("prefecture service stall detail() hystrix");
		return null;
	}

	@Override
	public int bind(Long id, Long sid) {
		log.info("prefecture service stall bind() hystrix");
		return 0;
	}

	@Override
	public int changedUp(List<Long> ids) {
		log.info("prefecture service stall changedUp() hystrix");
		return 0;
	}


	@Override
	public void saveAndBind(Long preId, String stallName, String sn) {
		log.info("prefecture service stall saveAndBind() hystrix");
	}
	
	@Override
	public ResStallEntity findById(Long stallId) {
		log.info("prefecture service stall findById(Long stallId) hystrix");
		return new ResStallEntity();
	}

	@Override
	public List<ResStallOps> findList(Map<String, Object> param) {
		log.info("prefecture service List<ResStallOps> findList(Map<String, Object> param) hystrix");
		return null;
	}

	@Override
	public List<ResStall> findStallList(Map<String, Object> param) {
		log.info("prefecture service List<ResStall> findStallList(Map<String, Object> param) hystrix");
		return null;
	}

	@Override
	public int unBind(List<Long> ids) {
		log.info("prefecture service int unBind(List<Long> ids) hystrix");
		return 0;
	}

	@Override
	public int changedDown(List<Long> ids) {
		log.info("prefecture service stall changedDown() hystrix ids={}",JSON.toJSON(ids));
		return 0;
	}
	
	
}
