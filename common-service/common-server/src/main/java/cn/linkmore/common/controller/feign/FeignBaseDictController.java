package cn.linkmore.common.controller.feign;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.common.request.ReqBaseDict;
import cn.linkmore.common.response.ResBaseDict;
import cn.linkmore.common.response.ResOldDict;
import cn.linkmore.common.service.BaseDictService;
import cn.linkmore.common.service.DictService;

/**
 * 数据词典(新)
 * @author   GFF
 * @Date     2018年5月18日
 * @Version  v2.0
 */
@RestController
@RequestMapping(value="/feign/dict")
public class FeignBaseDictController {
	
	@Resource
	private BaseDictService baseDictService;
	
	@Autowired
	private DictService dictService;
	
	/**
	 * @Description  通过code查询数据
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@RequestMapping(value="/{code}",method=RequestMethod.GET)
	public List<ResBaseDict> findList(@PathVariable("code") String code ) {
		List<ResBaseDict> res = this.baseDictService.findList(code);
		return res;
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public void save(@RequestBody ReqBaseDict baseDict) {
		this.baseDictService.save(baseDict);
	}

	@RequestMapping(method=RequestMethod.PUT)
	public void update(@RequestBody ReqBaseDict baseDict) {
		this.baseDictService.updateByIdSelective(baseDict);
	}
	
	@RequestMapping(value="/{id}",method=RequestMethod.DELETE)
	public void update(@PathVariable("id") Long id) {
		this.baseDictService.deleteById(id);
	}
	
	@RequestMapping(value="/detail/{id}",method=RequestMethod.GET)
	@ResponseBody
	public ResBaseDict find(@PathVariable("id") Long id) {
		return this.baseDictService.find(id);
	}
	
	@RequestMapping(value="/old/{id}/",method=RequestMethod.GET)
	public ResOldDict findOld(@PathVariable("id") Long id) {
		return this.dictService.find(id);
	}
	
	/**
	 * 查询老字典计费系统下拉列表
	 * @return
	 */
	@RequestMapping(value="/old/bill_system_list/",method=RequestMethod.GET)
	public List<ResOldDict> findBillSystemList(){
		return this.dictService.findBillSystemList();
	}
	
	/**
	 * @Description  批量查询
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@RequestMapping(value="/list",method=RequestMethod.POST)
	@ResponseBody
	public List<ResBaseDict> findListByCodes(@RequestBody List<String> codes){
		return this.baseDictService.findListByCodes(codes);
	}

}
