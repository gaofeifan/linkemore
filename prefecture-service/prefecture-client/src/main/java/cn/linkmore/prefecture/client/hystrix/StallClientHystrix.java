package cn.linkmore.prefecture.client.hystrix;

import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import cn.linkmore.bean.view.Tree;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.prefecture.client.StallClient;
import cn.linkmore.prefecture.request.ReqCheck;
import cn.linkmore.prefecture.request.ReqControlLock;
import cn.linkmore.prefecture.request.ReqOrderStall;
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
public class StallClientHystrix implements StallClient {
	private  final Logger log = LoggerFactory.getLogger(this.getClass()); 
	 
	public void close(Long id) {
		log.info("prefecture service stall close(Long id) hystrix"); 
	}

	@Override
	public void order(Long id) {
		log.info("prefecture service stall order(Long id) hystrix"); 
	}

	@Override
	public boolean cancel(Long stallId) {
		log.info("prefecture service stall cancel(Long stallId) hystrix");
		return false;
	}

	@Override
	public void downlock(ReqOrderStall stall) {
		log.info("prefecture service stall downlock(ReqOrderStall stall) hystrix");
	}

	@Override
	public Boolean uplock(Long stallId) {
		log.info("prefecture service stall uplock(Long stallId) hystrix");
		return false;
	}

	@Override
	public Boolean checkout(Long stallId) {
		log.info("prefecture service stall checkout(Long stallId) hystrix");
		return false;
	}
	
	@Override
	public List<ResStall> findStallList(Map<String, Object> param) {
		log.info("prefecture service stall findStallList(Map param) hystrix");
		return null;
	}
	
	@Override
	public List<ResStall> findPreStallList(Map<String, Object> param) {
		log.info("prefecture service stall findPreStallList(Map<String, Object> param) hystrix");
		return null;
	}

	@Override
	public ResStallEntity findById(Long stallId) {
		log.info("prefecture service stall findById(Long stallId) hystrix");
		return new ResStallEntity();
	}
	public ResStallEntity findByLock(@PathVariable("sn") String sn) {
		log.info("prefecture service stall findByLock(String sn) hystrix");
		return null;
	}
	

	@Override
	public Tree tree() {
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
	public int changedDown(Long id) {
		log.info("prefecture service stall changedDown() hystrix");
		return 0;
	}


	@Override
	public void saveAndBind(Long preId, String stallName, String sn) {
		log.info("prefecture service stall saveAndBind() hystrix");
	}

	@Override
	public List<ResStallOps> findListByParam(Map<String, Object> param) {
		log.info("prefecture service List<ResStallOps> findListByParam(Map<String, Object> param) hystrix");
		return null;
	}

	@Override
	public int updateBrand(Map<String, Object> map) {
		log.info("prefecture service int updateBrand(Map<String, Object> map) hystrix");
		return 0;
	}

	@Override
	public void controllock(ReqControlLock reqc) {
		log.info("prefecture service int controllock(ReqControlLock reqc) hystrix");
	}

	@Override
	public Map<String, Object> lockstatus(List<String> list) {
		log.info("prefecture service int lockstatus(List<String> list) hystrix");
		return null;
	}

	@Override
	public Map<String, Object> watch(Long stallId) {
		log.info("prefecture service Map<String, Object> watch(Long stallId) hystrix");
		return null;
	}

	@Override
	public void operLockWY(ReqControlLock reqc) {
		log.info("prefecture service void operLockWY(ReqControlLock reqc)  hystrix");
		
	}
	
	

}
