package cn.linkmore.enterprise.controller.ent;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.bean.common.ResponseEntity;
import cn.linkmore.enterprise.controller.ent.response.ResPreOrderCount;
import cn.linkmore.enterprise.service.PrefectureService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/prefecture")
@Api("车区运营")
public class PrefectureController {

	@Resource
	private PrefectureService prefectureService;
	
	@RequestMapping
	@ApiOperation(value = "查询每日车场订单收入", notes = "查询每日车场订单收入", consumes = "application/json")
	public ResponseEntity<List<ResPreOrderCount>> findPreList(HttpServletRequest request){
		return ResponseEntity.success(this.prefectureService.findPreList(request), request);
	}
}
