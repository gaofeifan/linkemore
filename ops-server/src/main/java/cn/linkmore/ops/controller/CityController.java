package cn.linkmore.ops.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import cn.linkmore.bean.common.ResponseEntity;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.ops.request.ReqCheck;
import cn.linkmore.ops.request.ReqCity;
import cn.linkmore.ops.service.CityService;
import io.swagger.annotations.Api;

/**
 * 城市信息
 * 
 * @author GFF
 * @Date 2018年5月26日
 * @Version v2.0
 */
@RestController
@RequestMapping("/city")
@Api(tags = "city", description = "城市")
public class CityController {

	@Autowired
	private CityService cityService;

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> save(@RequestBody ReqCity record, HttpServletRequest request) {
		this.cityService.save(record);
		return ResponseEntity.success(null, request);
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> update(@RequestBody ReqCity record, HttpServletRequest request) {
		this.cityService.update(record);
		return ResponseEntity.success(null, request);
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> delete(@RequestBody List<Long> ids, HttpServletRequest request) {
		this.cityService.delete(ids);
		return ResponseEntity.success(null, request);
	}

	@RequestMapping(value = "/check", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Boolean> check(@RequestBody ReqCheck check, HttpServletRequest request) {
		Boolean flag = this.cityService.check(check);
		return ResponseEntity.success(flag, request);
	}

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ViewPage> list(HttpServletRequest request, ViewPageable pageable) {
		ViewPage page = this.cityService.findPage(pageable);
		return ResponseEntity.success(page, request);
	}
}
