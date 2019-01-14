package cn.linkmore.prefecture.controller.feign;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import cn.linkmore.bean.common.ResponseEntity;
import cn.linkmore.bean.exception.BusinessException;
import cn.linkmore.bean.exception.StatusEnum;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.prefecture.request.ReqDep;
import cn.linkmore.prefecture.service.CodePayPrefectureService;

/**
 * Controller - 车区帐号配置
 * 
 * @author c.l
 */

@RestController
@RequestMapping("/feign/deploy")
public class CodePayPrefectureController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private CodePayPrefectureService codePayPrefectureService;

	// 查询列表
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(@RequestBody ViewPageable pageable) throws IOException {
		log.info("查询列表");
		return codePayPrefectureService.findPage(pageable);
	}

	// 查询列表
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public void save(@RequestBody ReqDep reqDep) {
		codePayPrefectureService.save(reqDep);
	}

	// 查询列表
	@RequestMapping(value = "/record", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage record(@RequestBody ViewPageable pageable) throws IOException {
		log.info("查询列表");
		return codePayPrefectureService.findRecordPage(pageable);
	}

	// 查询列表
	@RequestMapping(value = "/payList", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String, Object>> payList(@RequestBody String orderNo) throws IOException {
		log.info("查询列表payList" + orderNo);
		return codePayPrefectureService.payList(orderNo);
	}

	// 删除
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public void delete(@RequestBody String preId) throws IOException {
		log.info("删除" + preId);
		codePayPrefectureService.delete(preId);
	}

	// 删除
	@RequestMapping(value = "/down", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> down(@RequestBody String preId) throws IOException {
		log.info("下载" + preId);
		return codePayPrefectureService.down(preId);
	}

	// 查询列表
	@RequestMapping(value = "/selectAll", method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String, Object>> selectAll() throws IOException {
		log.info("selectAll");
		return codePayPrefectureService.selectAll();
	}

}
