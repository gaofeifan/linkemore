package cn.linkmore.account.controller.ops;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import cn.linkmore.account.service.ReceiveService;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
/**
 * 领取记录
 * @author   GFF
 * @Date     2018年5月30日
 * @Version  v2.0
 */
@RestController
@RequestMapping("/receive")
public class ReceiveController {

	@Resource
	private ReceiveService receiveService;

	/**
	 * @Description	分页查询
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@RequestMapping(value = "/v2.0/page", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(@RequestBody ViewPageable pageable) {
		return receiveService.findPage(pageable);
	}
	
	/**
	 * @Description  详情分页查询
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@RequestMapping(value = "/v2.0/detail_page", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage detailList(@RequestBody ViewPageable pageable) {
		return receiveService.findDetailPage(pageable);
	}
}
