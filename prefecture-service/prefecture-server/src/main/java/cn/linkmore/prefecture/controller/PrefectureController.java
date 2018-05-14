package cn.linkmore.prefecture.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import cn.linkmore.account.entity.User;
import cn.linkmore.prefecture.request.ReqPrefecture;
import cn.linkmore.prefecture.response.ResPrefecture;
import cn.linkmore.prefecture.response.ResPrefectureDetail;
import cn.linkmore.prefecture.response.ResPrefectureList;
import cn.linkmore.prefecture.response.ResPrefectureStrategy;
import cn.linkmore.prefecture.service.PrefectureService; 

/**
 * Controller - 车区信息
 * @author jiaohanbin
 * @version 2.0
 *
 */ 
@RestController
@RequestMapping("/prefecture/pres")
public class PrefectureController {
	
	private  final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private PrefectureService preService;
	
	//private UserService userService;
	
	/**
	 * 根据主键查询详情  
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping(value="{id}",method=RequestMethod.GET)
	@ResponseBody 
	public ResPrefectureDetail findById(@PathVariable("id") Long id,HttpServletRequest request) {
		String language = request.getHeader("lan");
		return this.preService.find(id,language);
	}
	/**
	 * 根据位置查询车区地图 
	 * @param reqPrefecture
	 * @param userId
	 * @return
	 */
	@RequestMapping(value="/findPreListByLoc", method=RequestMethod.GET)
	@ResponseBody 
	public List<ResPrefecture> findPreListByLoc(@RequestBody ReqPrefecture reqPrefecture,Long userId) { 
		//User user = userService.findById(userId);
		User user = null;
		return this.preService.findPreListByLoc(reqPrefecture,user);
	}
	/**
	 * 根据城市id查询车区卡片列表
	 * @param cityId
	 * @param userId
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/findPreListByCityId", method=RequestMethod.GET)
	@ResponseBody 
	public List<ResPrefectureList> findPreListByCityId(Long cityId, Long userId, HttpServletRequest request) { 
		//User user = userService.findById(userId);
		User user = null;
		String language = request.getHeader("lan");
		List<ResPrefectureList> preList= this.preService.findPreListByCityId(cityId,language,user);
		return preList;
	}
	/**
	 * 根据车区id查询计费策略
	 * @param preId
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/findPreStrategy", method=RequestMethod.GET)
	@ResponseBody 
	public ResPrefectureStrategy findPreStrategy(Long preId,HttpServletRequest request) { 
		String language = request.getHeader("lan");
		ResPrefectureStrategy resPreStrategy = preService.getPreStrategy(preId,language);
		return resPreStrategy;
	}
}
