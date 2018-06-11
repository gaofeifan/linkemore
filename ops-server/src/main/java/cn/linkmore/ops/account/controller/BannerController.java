package cn.linkmore.ops.account.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.bean.exception.DataException;
import cn.linkmore.bean.view.ViewMsg;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.ops.account.service.BannerService;
import cn.linkmore.order.request.ReqUpdateStatus;
import cn.linkmore.order.request.ReqWalletBanner;
import cn.linkmore.order.response.ResWalletBanner;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 滚动横幅的 Controller
 * @author   GFF
 * @Date     2018年5月31日
 * @Version  v2.0
 */
@Api(value = "banner管理", produces = "application/json")
@RestController
@RequestMapping("/admin/account/banner")
public class BannerController  {

	@Resource
	private BannerService bannerService;

	/**
	 * 分页
	 * 
	 * @param bean
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "分页", notes = "分页", consumes = "application/json")
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public ViewPage combonlist(ViewPageable pageable,
			HttpServletRequest request) {
		return bannerService.findPage(pageable);
	}

	/**
	 * 新增信息
	 * 
	 * @param ban
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ViewMsg save( ReqWalletBanner ban, HttpServletRequest request) {
		ViewMsg msg = null;
		try {
			this.bannerService.save(ban);
			msg = new ViewMsg("保存成功",true);
		}catch(DataException e) {
			msg = new ViewMsg(e.getMessage(),false);
		}catch(Exception e) {
			e.printStackTrace();
			msg = new ViewMsg("保存失败",false);
		}
		return msg;
	}

	/**
	 * 修改信息
	 * 
	 * @param ban
	 * @return
	 */
	@ApiOperation(value = "修改信息", notes = "修改信息", consumes = "application/json")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ViewMsg update(ReqWalletBanner ban, HttpServletRequest request) {
		ViewMsg msg = null;
		try {
			this.bannerService.update(ban);
			msg = new ViewMsg("更新成功",true);
		}catch(DataException e) {
			msg = new ViewMsg(e.getMessage(),false);
		}catch(Exception e) {
			e.printStackTrace();
			msg = new ViewMsg("更新失败",false);
		}
		return msg;
	}

	/**
	 * 修改状态
	 * 
	 * @param ban
	 * @return
	 */
	@RequestMapping(value = "/set_show", method = RequestMethod.POST)
	public ViewMsg setShow(@RequestBody List<Long> ids, HttpServletRequest request) {
		ViewMsg msg = null;
		try {
			this.bannerService.updateStatus(new ReqUpdateStatus(ids, 1));
			msg = new ViewMsg("操作成功",true);
		}catch(DataException e) {
			msg = new ViewMsg(e.getMessage(),false);
		}catch(Exception e) {
			e.printStackTrace();
			msg = new ViewMsg("操作失败",false);
		}
		return msg;
	}
	/**
	 * 修改状态
	 * 
	 * @param ban
	 * @return
	 */
	@RequestMapping(value = "/set_hide", method = RequestMethod.POST)
	public ViewMsg setHide(@RequestBody List<Long> ids, HttpServletRequest request) {
		ViewMsg msg = null;
		try {
			
			this.bannerService.updateStatus(new ReqUpdateStatus(ids, 0));
			msg = new ViewMsg("操作成功",true);
		}catch(DataException e) {
			msg = new ViewMsg(e.getMessage(),false);
		}catch(Exception e) {
			e.printStackTrace();
			msg = new ViewMsg("操作失败",false);
		}
		return msg;
	}

	/**
	 * 数据回显
	 * 
	 * @return
	 */
	@RequestMapping(value = "/detail", method = RequestMethod.POST)
	public ResWalletBanner detail(Long bid ,HttpServletRequest request) {
		return this.bannerService.findById(bid);
	}
	
	/**
	 *  查看上线个数
	 * 
	 * @param ban
	 * @return
	 */
	@RequestMapping(value = "/count", method = RequestMethod.POST)
	public  Map<String, Object>  count(HttpServletRequest request) {
		boolean flag = true;
		Map<String, Object> msg = new HashMap<String, Object>();
		int count = this.bannerService.findStatusCount();
		if(count >= 3 ) {
			flag=false;
		}
		msg.put("success",flag);
		return msg;
	}
	
}
