package cn.linkmore.user.controller;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.bean.common.ResponseEntity;
import cn.linkmore.common.response.ResBaseDict;
import cn.linkmore.user.response.ResDonwLockError;
import cn.linkmore.user.service.BaseDictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 数据字典
 * @author   GFF
 * @Date     2018年5月18日
 * @Version  v2.0
 */
@RestController
@Api(tags="dict",description="数据字典")
@RequestMapping(value="/dict")
public class BaseDictController {
	
	@Resource
	private BaseDictService baseDictService;
	
	/**
	 * @Description  查询降锁异常原因
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@ApiOperation(value = "查询降锁异常原因", notes = "查询降锁异常原因", consumes = "application/json")
	@RequestMapping(value="/lock_down",method=RequestMethod.GET)
	public ResponseEntity<List<ResDonwLockError>> selectLockDownErrorCause(HttpServletRequest request) {
		List<ResDonwLockError> res = this.baseDictService.selectLockDownErrorCause();
		ResponseEntity<List<ResDonwLockError>> success = ResponseEntity.success(res, request);
		return success;
	}

}
