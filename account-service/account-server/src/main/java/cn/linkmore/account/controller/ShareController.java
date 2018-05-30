package cn.linkmore.account.controller;

import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import cn.linkmore.account.service.ShareService;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
/**
 * 分享记录
 * @author   GFF
 * @Date     2018年5月30日
 * @Version  v2.0
 */
@RestController
@RequestMapping("/share")
public class ShareController {

	@Resource
	private ShareService shareService;

	/**
	 * @Description  分页查询
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@RequestMapping(value = "/page", method = RequestMethod.POST)
	public ViewPage list( ViewPageable pageable) {
		return shareService.findPage(pageable);
	}
}
