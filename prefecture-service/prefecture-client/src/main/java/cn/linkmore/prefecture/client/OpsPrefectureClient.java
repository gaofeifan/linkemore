package cn.linkmore.prefecture.client;

import java.util.List;
import java.util.Map;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.feign.FeignConfiguration;
import cn.linkmore.prefecture.client.hystrix.OpsPrefectureClientHystrix;
import cn.linkmore.prefecture.client.hystrix.PrefectureClientHystrix;
import cn.linkmore.prefecture.request.ReqCheck;
import cn.linkmore.prefecture.request.ReqPreExcel;
import cn.linkmore.prefecture.request.ReqPrefectureEntity;
import cn.linkmore.prefecture.response.ResPre;
import cn.linkmore.prefecture.response.ResPreExcel;
import cn.linkmore.prefecture.response.ResPreList;
import cn.linkmore.prefecture.response.ResPrefecture;
import cn.linkmore.prefecture.response.ResPrefectureDetail;
/**
 * 远程调用 - 车区信息
 * @author jiaohanbin
 * @version 2.0
 *
 */ 
@FeignClient(value = "prefecture-server", path = "/ops/pres", fallback=OpsPrefectureClientHystrix.class,configuration = FeignConfiguration.class)
public interface OpsPrefectureClient {
	/**
	 * 根据主键查询详情
	 * @param id 主键ID
	 * @return 车区信息
	 */
	@RequestMapping(value="/v2.0/{id}",method=RequestMethod.GET)
	@ResponseBody 
	public ResPrefectureDetail findById(@PathVariable("id") Long id);
	
	/**
	 * 根据主键查询批量车区名称
	 * 
	 * @param id Long
	 * @return
	 */
	@RequestMapping(value = "/v2.0/prename", method = RequestMethod.POST)
	@ResponseBody
	public List<ResPre> prenames(@RequestBody List<Long> ids);
	
	/**
	 * 车区列表分页
	 * @param pageable
	 * @return
	 */
	@RequestMapping(value = "/v2.0/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(@RequestBody ViewPageable pageable);
	
	/**
	 * 删除专区
	 */
	@RequestMapping(value = "/v2.0/delete", method = RequestMethod.POST)
	@ResponseBody
	public int delete(@RequestBody List<Long> ids);
	
	/**
	 * 检查名称重复
	 */
	@RequestMapping(value = "/v2.0/check", method = RequestMethod.POST)
	@ResponseBody
	public Boolean check(@RequestBody ReqCheck reqCheck);
	
	/**
	 * 专区下拉列表
	 */
	@RequestMapping(value = "/v2.0/select_list", method = RequestMethod.POST)
	@ResponseBody
	public List<ResPreList> selectList();

	/**
	 * 新增
	 */
	@RequestMapping(value = "/v2.0/save", method = RequestMethod.POST)
	@ResponseBody
	public int save(@RequestBody ReqPrefectureEntity prefecture);
	
	/**
	 * 更新
	 */
	@RequestMapping(value = "/v2.0/update", method = RequestMethod.POST)
	@ResponseBody
	public int update(@RequestBody ReqPrefectureEntity prefecture);
	
	/**
	 * 城市专区列表
	 * @param cityId
	 * @return
	 */
	@RequestMapping(value = "/v2.0/find_city", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String,Object>>findListByCityId(@RequestBody Long cityId);
	
	/**
	 * 查询导出列表
	 * @param reqPreExcel
	 * @return
	 */
	@RequestMapping(value = "/v2.0/export_list", method = RequestMethod.POST)
	@ResponseBody
	public List<ResPreExcel> exportList(@RequestBody ReqPreExcel reqPreExcel);
	
	@RequestMapping(value = "/v2.0/find_list", method = RequestMethod.POST)
	@ResponseBody
	public List<ResPrefectureDetail> findList(@RequestBody Map<String, Object> param);
	
	@RequestMapping(value = "/v2.0/find_all", method = RequestMethod.POST)
	@ResponseBody
	public List<ResPrefectureDetail> findAll();
	/**
	 * 根据车区名称校验是否存在
	 * @param preName
	 * @return
	 */
	@RequestMapping(value = "/v2.0/check_name", method = RequestMethod.POST)
	@ResponseBody
	public ResPrefectureDetail checkName(@RequestParam("preName") String preName);
		
	@RequestMapping(value = "/v2.0/pre-list", method = RequestMethod.GET)
	@ResponseBody
	public List<ResPrefecture> findPreList();
}
