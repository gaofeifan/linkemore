package cn.linkmore.order.controller.ops;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.order.entity.WalletBanner;
import cn.linkmore.order.request.ReqUpdateStatus;
import cn.linkmore.order.request.ReqWalletBanner;
import cn.linkmore.order.response.ResWalletBanner;
import cn.linkmore.order.service.BannerService;
import cn.linkmore.util.ObjectUtils;
 
/**
 * 分享记录
 * @author   GFF
 * @Date     2018年5月18日
 * @Version  v2.0
 */

@RestController
@RequestMapping("/feign/banner")
public class WalletBannerController {

	@Resource
	private BannerService bannerService;
	
	
	@RequestMapping(value = "/v2.0" ,method = RequestMethod.POST)
	public void save(@RequestBody ReqWalletBanner banner) {
		this.bannerService.save(banner);
	}

	@RequestMapping(value = "/v2.0" ,method = RequestMethod.PUT)
	public void updateById(@RequestBody ReqWalletBanner b) {
		this.bannerService.update(b);
	}

	@RequestMapping(value = "/v2.0/status" ,method = RequestMethod.PUT)
	public void updateStatus(@RequestBody ReqUpdateStatus updateStutus) {
		this.bannerService.setStatus(updateStutus.getIds(), updateStutus.getStatus());
	}

	@RequestMapping(value = "/v2.0/page" , method = RequestMethod.POST)
	public ViewPage findPage(@RequestBody ViewPageable pageable) {
		return this.bannerService.findPage(pageable);
	}

	@RequestMapping(value = "/v2.0/detail/{bid}" , method = RequestMethod.GET)
	public ResWalletBanner findById(@PathVariable("bid")Long bid) {
		WalletBanner id = this.bannerService.findById(bid);
		return ObjectUtils.copyObject(id,new ResWalletBanner());
	}

	@RequestMapping(value = "/v2.0/condition/{sql}" , method = RequestMethod.GET)
	public List<ResWalletBanner> selectList(@PathVariable("sql")String sql){
		List<ResWalletBanner> selectList = this.bannerService.selectList(sql);
		return selectList;
	}
	
}
