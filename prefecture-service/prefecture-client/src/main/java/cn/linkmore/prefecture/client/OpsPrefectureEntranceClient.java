package cn.linkmore.prefecture.client;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.feign.FeignConfiguration;
import cn.linkmore.prefecture.client.hystrix.OpsPrefectureEntranceClientHystrix;
import cn.linkmore.prefecture.request.ReqPrefectureEntrance;

/**
 * 远程调用 - 车区元素
 * @author 焦汉斌
 * @version 1.0
 *
 */

@FeignClient(value = "prefecture-server", path = "/ops/prefecture_entrance", fallback=OpsPrefectureEntranceClientHystrix.class,configuration = FeignConfiguration.class)
public interface OpsPrefectureEntranceClient {

	/**
	 * 新增
	 * @param reqPrefectureEle
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public int save(@RequestBody ReqPrefectureEntrance reqPrefectureEle);
	
	/**
	 * 更新
	 * @param reqPrefectureEle
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public int update(@RequestBody ReqPrefectureEntrance reqPrefectureEle);
	
	/**
	 * 删除
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public int delete(@RequestBody List<Long> ids);
	
	/**
	 * 列表-分页
	 * @param pageable
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(@RequestBody ViewPageable pageable);

}
