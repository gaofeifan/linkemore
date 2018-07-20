/**
 * 
 */
package cn.linkmore.enterprise.controller.ent;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.bean.common.ResponseEntity;
import cn.linkmore.enterprise.controller.ent.request.ReqAddEntPreture;

/**
 * 企业车区
 * @author luzhishen
 * @Date 2018年7月20日
 * @Version v1.0
 */
@RestController
@RequestMapping("/enterprise")
public class EntPreController {
	
	
	@RequestMapping(value = "/saveEntPre",method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> saveEntPre(@RequestBody ReqAddEntPreture reqAddEntPreture,HttpServletRequest request){
		return null;
	}

}
