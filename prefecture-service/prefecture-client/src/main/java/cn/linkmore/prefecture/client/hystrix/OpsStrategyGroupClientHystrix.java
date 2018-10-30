package cn.linkmore.prefecture.client.hystrix;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import cn.linkmore.bean.view.Tree;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.prefecture.client.OpsStrategyGroupClient;
import cn.linkmore.prefecture.request.ReqStrategyGroup;
import cn.linkmore.prefecture.request.ReqStrategyGroupDetail;
import cn.linkmore.prefecture.response.ResStall;
import cn.linkmore.prefecture.response.ResStrategyGroup;
import cn.linkmore.prefecture.response.ResStrategyGroupArea;
/**
 * 远程调用实现-断熔器 - 分组策略信息
 * @author lilinhai
 * @version 1.0
 *
 */ 
@Component
public class OpsStrategyGroupClientHystrix implements OpsStrategyGroupClient {
	private  final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public int save(ReqStrategyGroup reqStrategyGroup) {
		log.info("prefecture service strategy save(Long reqStrategyGroup) hystrix");
		return 0;
	}

	@Override
	public int update(ReqStrategyGroup reqStrategyGroup) {
		log.info("prefecture service strategy update(ReqStrategyGroup reqStrategyGroup) hystrix");
		return 0;
	}
	@Override
	public int updateStatus(Map<String, Object> map) {
		log.info("prefecture service strategy updateStatus(Map<String, Object> map) hystrix");
		return 0;
	}

	@Override
	public int delete(List<Long> ids) {
		log.info("prefecture service strategy delete(Long ids) hystrix");
		return 0;
	}

	@Override
	public ViewPage list(ViewPageable pageable) {
		log.info("prefecture service strategy list(ViewPageable pageable) hystrix");
		return null;
	}

	@Override
	public ResStrategyGroup selectByPrimaryKey(Long id) {
		log.info("prefecture service strategy ResStrategyGroup selectByPrimaryKey(Long id) hystrix");
		return new ResStrategyGroup();
	}

	@Override
	public Tree findTree(@RequestBody Map<String, Object> param) {
		log.info("prefecture service findTree  Map<String, Object> hystrix");
		return new Tree();
	}
	@Override
	public List<ResStall> findAreaStall(@RequestBody Map<String, Object> param){
		log.info("prefecture service findAreaStall ReqStrategyGroup hystrix");
		return new ArrayList<ResStall>();
	}

	@Override
	public int deleteStall(List<Long> ids) {
		log.info("prefecture service deleteStall List<Long> hystrix");
		return 0;
	}

	@Override
	public int addStall(ReqStrategyGroupDetail reqStrategyGroupDetail) {
		log.info("prefecture service addStall ReqStrategyGroupDetail hystrix");
		return 0;
	}

	@Override
	public List<ResStrategyGroupArea> selectStallByPrimaryKey(Long id) {
		log.info("prefecture service List<ResStrategyGroupArea> selectStallByPrimaryKey Long hystrix");
		return new ArrayList<ResStrategyGroupArea>();
	}

	@Override
	public Long existsStall( @RequestBody Map<String, Object> map) {
		log.info("prefecture service Long findStall @RequestBody Map<String, Object> map hystrix");
		return 0L;
	}

	@Override
	public List<ResStrategyGroup> findList() {
		log.info("prefecture service List<ResStrategyGroup> findList hystrix");
		return null;
	}
}
