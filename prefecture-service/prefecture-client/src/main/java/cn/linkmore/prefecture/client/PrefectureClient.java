package cn.linkmore.prefecture.client;

import java.util.List;
import java.util.Map;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.feign.FeignConfiguration;
import cn.linkmore.prefecture.client.hystrix.PrefectureClientHystrix;
import cn.linkmore.prefecture.request.ReqCheck;
import cn.linkmore.prefecture.request.ReqPreExcel;
import cn.linkmore.prefecture.request.ReqPrefecture;
import cn.linkmore.prefecture.request.ReqPrefectureEntity;
import cn.linkmore.prefecture.response.ResPre;
import cn.linkmore.prefecture.response.ResPreExcel;
import cn.linkmore.prefecture.response.ResPreList;
import cn.linkmore.prefecture.response.ResPrefecture;
import cn.linkmore.prefecture.response.ResPrefectureDetail;
import cn.linkmore.prefecture.response.ResPrefectureList;
import cn.linkmore.prefecture.response.ResPrefectureStrategy;
/**
 * 远程调用 - 车区信息
 * @author jiaohanbin
 * @version 2.0
 *
 */ 
@FeignClient(value = "prefecture-server", path = "/pres", fallback=PrefectureClientHystrix.class,configuration = FeignConfiguration.class)
public interface PrefectureClient {
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
	 * 根据车区id查询计费策略
	 * 
	 * @param preId Long
	 * @return
	 */
	@RequestMapping(value = "/v2.0/strategy/{preId}", method = RequestMethod.GET)
	@ResponseBody
	public ResPrefectureStrategy findPreStrategy(@PathVariable("preId") Long preId);
	
	
	/**
	 * 根据位置查询车区地图
	 * 
	 * @param reqPrefecture ReqPrefecture
	 * @return
	 */
	@RequestMapping(value = "/v2.0/loc", method = RequestMethod.POST)
	@ResponseBody
	public List<ResPrefecture> findPreListByLoc(@RequestBody ReqPrefecture reqPrefecture);
	
	/**
	 * 根据车区id查询车区空闲车位
	 * 
	 * @param preId Long
	 * @return
	 */
	@RequestMapping(value = "/v2.0/free_count", method = RequestMethod.GET)
	@ResponseBody
	public List<ResPrefectureList> refreshFreeStall();
	
	
	
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
	@RequestMapping(value = "/v2.0/selectList", method = RequestMethod.POST)
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
	public List<ResPreExcel> exportList(@RequestBody ReqPreExcel reqPreExcel);
	
}
