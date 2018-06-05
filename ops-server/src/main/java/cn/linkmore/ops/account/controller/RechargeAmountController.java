package cn.linkmore.ops.account.controller;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.account.request.ReqRechargeAmount;
import cn.linkmore.account.response.ResRechargeAmount;
import cn.linkmore.bean.view.ViewMsg;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.ops.account.service.RechargeAmountService;
import io.swagger.annotations.Api;

/**
 * 用户充值面额
 * @author zhaoyufei
 *
 */

@Api(value = "用户充值面额", produces = "application/json")
@RestController
@RequestMapping("/admin/account/recharge_amount")
public class RechargeAmountController {
	
	@Resource
	private RechargeAmountService amountService;
	
	
	/**
	 * 新增充值面额
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ViewMsg create(ReqRechargeAmount requestBean,HttpServletRequest request){
		ViewMsg msg = null;
		try {
			amountService.create(requestBean);
    		msg = new ViewMsg("操作成功",true);
    	} catch (RuntimeException e) {
			msg = new ViewMsg("操作失败",false);
		} catch (Exception e) {
			msg = new ViewMsg("系统异常",false);
		}
    	return msg;
	}
	/**
	 * 获取面额列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public ViewPage list(ViewPageable pageable){
		return amountService.getList(pageable);
	}
	/**
	 * 选中
	 */
	@RequestMapping(value = "/checked", method = RequestMethod.POST)
	public ViewMsg checked(Long id ,HttpServletRequest request){
		ViewMsg msg = null;
		try {
			this.amountService.updateColumnValue(" id ="+id+" ", " checked ", 1);
    		msg = new ViewMsg("操作成功",true);
    	} catch (RuntimeException e) {
			msg = new ViewMsg("操作失败",false);
		} catch (Exception e) {
			msg = new ViewMsg("系统异常",false);
		}
    	return msg;
	}
	/**
	 * 选中
	 */
	@RequestMapping(value = "/no_checked", method = RequestMethod.POST)
	public ViewMsg noChecked(Long id ,HttpServletRequest request){
		ViewMsg msg = null;
		try {
			this.amountService.updateColumnValue(" id ="+id+" ", " checked ", 0);
			msg = new ViewMsg("操作成功",true);
		} catch (RuntimeException e) {
			msg = new ViewMsg("操作失败",false);
		} catch (Exception e) {
			msg = new ViewMsg("系统异常",false);
		}
		return msg;
	}
	/**
	 * 禁用面額
	 */
	@RequestMapping(value = "/stop", method = RequestMethod.POST)
	public ViewMsg stop(Long id, HttpServletRequest request) {
		ViewMsg msg = null;
		try {
			this.amountService.updateColumnValue(" id ="+id+" ", " status ", 0);
    		msg = new ViewMsg("操作成功",true);
    	} catch (RuntimeException e) {
			msg = new ViewMsg("操作失败",false);
		} catch (Exception e) {
			msg = new ViewMsg("系统异常",false);
	    }
    	return msg;
	}
	/**
	 * 启用面額
	 */
	@RequestMapping(value = "/rstart", method = RequestMethod.POST)
	public ViewMsg rstart( Long id, HttpServletRequest request) {
		ViewMsg msg = null;
		try {
			this.amountService.updateColumnValue(" id ="+id+" ", " status ", 1);
    		msg = new ViewMsg("操作成功",true);
    	} catch (RuntimeException e) {
			msg = new ViewMsg("操作失败",false);
		} catch (Exception e) {
			msg = new ViewMsg("系统异常",false);
	    }
    	return msg;
	}
	/**
	 * 根据id获取数据
	 */
	@RequestMapping(value = "/before_edit/{id}", method = RequestMethod.POST)
	public ResponseEntity<ResRechargeAmount> beforeEdit(@PathVariable("id") Long id){
		ResRechargeAmount amount =  amountService.queryDataById(id);
		return ResponseEntity.ok(amount);
	}
	/**
	 * 根据id获取数据
	 */
	@RequestMapping(value = "/getPaymentAmount/{id}", method = RequestMethod.GET)
	public ResponseEntity<ResRechargeAmount> getPaymentAmount(@PathVariable("id") Long id){
		ResRechargeAmount rePackage =  amountService.queryDataById(id);
		return ResponseEntity.ok(rePackage);
	}
	/**
	 * 修改面额
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ViewMsg updateData( ReqRechargeAmount bean , HttpServletRequest request) {
		ViewMsg msg = null;
		try {
			ResRechargeAmount amount =  amountService.queryDetail(bean.getId());
    		String db = amount.toString();
    		amountService.updateData(bean);
    		msg = new ViewMsg("操作成功",true);
    	} catch (RuntimeException e) {
			msg = new ViewMsg("操作失败",false);
		} catch (Exception e) {
			msg = new ViewMsg("系统异常",false);
	    }
    	return msg;
	}
}
