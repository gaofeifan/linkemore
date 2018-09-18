package cn.linkmore.prefecture.controller.feign;

import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.linkmore.lock.bean.LockBean;
import com.linkmore.lock.response.ResponseMessage;

import cn.linkmore.bean.view.Tree;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.prefecture.request.ReqCheck;
import cn.linkmore.prefecture.request.ReqControlLock;
import cn.linkmore.prefecture.request.ReqOrderStall;
import cn.linkmore.prefecture.request.ReqStall;
import cn.linkmore.prefecture.request.ReqStallOperateLog;
import cn.linkmore.prefecture.response.ResStall;
import cn.linkmore.prefecture.response.ResStallEntity;
import cn.linkmore.prefecture.response.ResStallLock;
import cn.linkmore.prefecture.response.ResStallOps;
import cn.linkmore.prefecture.service.PrefectureService;
import cn.linkmore.prefecture.service.StallLockService;
import cn.linkmore.prefecture.service.StallOperateLogService;
import cn.linkmore.prefecture.service.StallService;
import cn.linkmore.util.ObjectUtils;

/**
 * Controller - 车位操作
 * 
 * @author jiaohanbin
 * @version 2.0
 *
 */
@RestController
@RequestMapping("/feign/stall")
public class FeignStallController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private StallService stallService;
	
	@Autowired
	private StallLockService stallLockService;
	
	@Autowired
	private PrefectureService preService;
	
	@Autowired
	private StallOperateLogService stallOperateService;
	
	@RequestMapping(value = "/v2.0/close", method = RequestMethod.PUT)
	public void close(@RequestParam("id")Long id) {
		log.info("order closed handle stall:{}",id);
		this.stallService.close(id);
	}


	/**
	 * 预约订单时，根据车位锁序列号查询车位
	 * 
	 * @param lockSn
	 *            String
	 */
	@RequestMapping(value = "/v2.0/order", method = RequestMethod.PUT)
	public void order(@RequestParam("id") Long id) {
		this.stallService.order(id);
	}

	/**
	 * 取消订单释放车位
	 * 
	 * @param stallId
	 *            Long
	 */
	@RequestMapping(value = "/v2.0/cancel", method = RequestMethod.GET)
	public boolean cancel(@RequestParam("stallId") Long stallId) {
		return this.stallService.cancel(stallId);
	}

	/**
	 * 降锁操作
	 * 
	 * @param stallId
	 *            Long
	 */
	@RequestMapping(value = "/v2.0/downlock", method = RequestMethod.PUT)
	public void downlock(@RequestBody ReqOrderStall stall) {
		log.info("downlock:{} .......................................",JSON.toJSON(stall));
		this.stallService.downlock(stall);
	}

	/**
	 * 升锁操作
	 * 
	 * @param stallId
	 *            Long
	 */
	@RequestMapping(value = "/v2.0/uplock", method = RequestMethod.PUT)
	public Boolean uplock(@RequestParam("stallId") Long stallId) {
		boolean flag = this.stallService.uplock(stallId);
		return flag;
	}
	
	/**
	 * 操作锁
	 * 
	 * @param stallId
	 *            Long
	 */
	@RequestMapping(value = "/v2.0/controllock", method = RequestMethod.POST)
	public void controllock(@RequestBody   ReqControlLock  reqc) {
		log.info("controllock:{} .......................................",reqc.getStallId());
		this.stallService.controling(reqc);
	}
	
	/**
	 * 管理版操作锁
	 * 
	 * @param stallId
	 *            Long
	 */
	@RequestMapping(value = "/v2.0/operatelock", method = RequestMethod.POST)
	public void managerlock(@RequestBody   ReqControlLock  reqc) {
		log.info("managerlock:{} .......................................",reqc.getStallId());
		this.stallService.operating(reqc);
	}
	
	/**
	 *物业版操作锁 操作锁
	 * 
	 * @param stallId
	 *            Long
	 */
	@RequestMapping(value = "/v2.0/oper-lock-wy", method = RequestMethod.POST)
	public void operLockWY(@RequestBody   ReqControlLock  reqc) {
		log.info("operLockWY:{} .......................................",reqc.getStallId());
		this.stallService.operLockWY(reqc);
	}
	
	/**
	 * 操作锁
	 * 
	 * @param stallId
	 *            Long
	 */
	@RequestMapping(value = "/v2.0/watch", method = RequestMethod.POST)
	public Map<String,Object> watch(@RequestBody Long stallId) {
		log.info("watch:{} .....................................",stallId);
	    return	this.stallService.watch(stallId);
	}
	
	/**
	 * 查询车位锁状态
	 * 
	 * @param stallId
	 *            Long
	 */
	@RequestMapping(value = "/v2.0/lockstatus", method = RequestMethod.POST)
	public Map<String,Object> lockstatus(@RequestBody   List<String> list) {
		log.info("lockstatus:{} .......................................",list);
		return	this.stallService.lockStatus(list);
	}

	/**
	 * 结账离场释放车位
	 * 
	 * @param stallId
	 *            Long
	 */
	@RequestMapping(value = "/v2.0/checkout", method = RequestMethod.PUT)
	public Boolean checkout(@RequestParam("stallId") Long stallId) {
		boolean flag = this.stallService.checkout(stallId);
		return flag;
	}

	/**
	 * 根据车位id获取车位信息
	 * 
	 * @param stallId
	 *            Long
	 */
	@RequestMapping(value = "/v2.0/{stallId}", method = RequestMethod.GET)
	public ResStallEntity findById(@PathVariable("stallId") Long stallId) {
		ResStallEntity stallEntity = this.stallService.findById(stallId);
		return stallEntity;
	}

	@RequestMapping(value = "/v2.0/lock/{sn}", method = RequestMethod.GET)
	public ResStallEntity findByLock(@PathVariable("sn") String sn) {
		ResStallEntity stallEntity = this.stallService.findByLock(sn);
		return stallEntity;
	}
	
	@RequestMapping(value = "/v2.0/pre-stall-list", method = RequestMethod.POST)
	@ResponseBody
	public List<ResStall> findPreStallList(@RequestBody Map<String, Object> param){
		List<ResStall> list = this.stallService.findPreStallList(param);
		return list;
	}
	
	@RequestMapping(value = "/v2.0/stall-list", method = RequestMethod.POST)
	@ResponseBody
	public List<ResStall> findStallList(@RequestBody Map<String, Object> param){
		List<ResStall> list = this.stallService.findList(param);
		return list;
	}
	
	@RequestMapping(value = "/v2.0/find-list", method = RequestMethod.POST)
	@ResponseBody
	public List<ResStallOps> findListByParam(@RequestBody Map<String, Object> param){
		List<ResStallOps> list = this.stallService.findListByParam(param);
		return list;
	}
	

	@RequestMapping(value = "/v2.0/tree", method = RequestMethod.POST)
	@ResponseBody
	public Tree tree() {
		return preService.findTree();
	}

	@RequestMapping(value = "/v2.0/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(@RequestBody ViewPageable pageable) {
		return stallService.findPage(pageable);
	}

	@RequestMapping(value = "/v2.0/save", method = RequestMethod.POST)
	@ResponseBody
	public int save(@RequestBody ReqStall stall) {
		return this.stallService.save(stall);
	}

	@RequestMapping(value = "/v2.0/update", method = RequestMethod.POST)
	@ResponseBody
	public int update(@RequestBody ReqStall stall) {
		ResStallEntity st = stallService.findById(stall.getId());
		if (st != null) {
			st.setStallName(stall.getStallName());
			st.setStallLocal(stall.getStallLocal());
			ReqStall reqStall = new ReqStall();
			reqStall = ObjectUtils.copyObject(st, reqStall);
			return this.stallService.update(reqStall);
		}
		return 0;
	}

	@RequestMapping(value = "/v2.0/check", method = RequestMethod.POST)
	@ResponseBody
	public int check(@RequestBody ReqCheck reqCheck) {
		return this.stallService.check(reqCheck);
	}

	@RequestMapping(value = "/v2.0/sn", method = RequestMethod.POST)
	@ResponseBody
	public List<ResStallLock> sn(@RequestParam("lockId") Long lockId) {
		return stallLockService.findAll(lockId);
	}

	@RequestMapping(value = "/v2.0/detail", method = RequestMethod.POST)
	@ResponseBody
	public ResStallEntity detail(@RequestParam("id") Long id) {
		return stallService.findById(id);
	}

	@RequestMapping(value = "/v2.0/bind", method = RequestMethod.POST)
	@ResponseBody
	public int bind(@RequestParam("id") Long id, @RequestParam("sid") Long sid) {
		ResStallEntity stall = this.stallService.findById(id);
		Long lockId = stall.getLockId();
		boolean check = stallLockService.checkFormerLock(stall.getLockSn());
		if (null != lockId && !check && lockId.longValue() != sid) {
			this.stallLockService.reset(lockId);
		}
		stall.setLockId(sid);
		ReqStall reqStall = new ReqStall();
		reqStall = ObjectUtils.copyObject(stall, reqStall);
		return this.stallService.bind(reqStall);
	}

	@RequestMapping(value = "/v2.0/changed_up", method = RequestMethod.POST)
	@ResponseBody
	public int changedUp(@RequestBody List<Long> ids) {
		for (Long id : ids) {
			ResStallEntity stall = this.stallService.findById(id);
			if (stall != null) {
				stall.setStatus(1);
				ReqStall reqStall = new ReqStall();
				reqStall = ObjectUtils.copyObject(stall, reqStall);
			    this.stallService.updateStatus(reqStall);
			}
		}
		return 0;
	}

	@RequestMapping(value = "/v2.0/changed_down", method = RequestMethod.POST)
	@ResponseBody
	public int changedDown(@RequestParam("id") Long id) {
		ResStallEntity stall = this.stallService.findById(id);
		if (stall != null) {
			if (stall.getBindOrderStatus() != null || stall.getBindOrderStatus() == 0) {
				stall.setStatus(4);
				ReqStall reqStall = new ReqStall();
				reqStall = ObjectUtils.copyObject(stall, reqStall);
				return this.stallService.updateStatus(reqStall);
			}
		}
		return 0;
	}
	
	@RequestMapping(value = "/v2.0/save_bind", method = RequestMethod.POST)
	@ResponseBody
	public void saveAndBind(@RequestParam("preId") Long preId,@RequestParam("stallName") String stallName,@RequestParam("sn") String sn) {
		this.stallService.saveAndBind(preId, stallName, sn);
	}
	
	@RequestMapping(value = "/v2.0/update-brand", method = RequestMethod.POST)
	@ResponseBody
	public int updateBrand(@RequestBody Map<String,Object> param) {
		log.info("stallIds = {}",JSON.toJSON(param));
		return this.stallService.updateBrand(param);
	}
	
	/**
	 * @Description  车位操作记录
	 * @Author   cl
	 * @Version  v2.0
	 */
	@RequestMapping(value = "/v2.0/operate-log/save", method = RequestMethod.POST)
	@ResponseBody
	public void operateLogSave(@RequestBody ReqStallOperateLog sol) {
		this.stallOperateService.save(sol);
	}
	
}
