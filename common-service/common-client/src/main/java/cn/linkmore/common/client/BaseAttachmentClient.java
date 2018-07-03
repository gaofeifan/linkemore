package cn.linkmore.common.client;

import java.io.InputStream;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.common.client.hystrix.BaseAttachmentClientHystrix;
import cn.linkmore.common.request.ReqAttachment;
import cn.linkmore.common.response.ResBaseAttachment;
import cn.linkmore.feign.FeignConfiguration;
/**
 * 数据词典
 * @author   GFF
 * @Date     2018年5月18日
 * @Version  v2.0
 */
@FeignClient(value = "common-server", path = "/attach", fallback=BaseAttachmentClientHystrix.class,configuration = FeignConfiguration.class)
public interface BaseAttachmentClient {
	
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	@ResponseBody
	public boolean delete(@RequestParam("id") Long id);
	
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(@RequestBody ViewPageable pageable); 
	
	@RequestMapping(value="/image_upload",method=RequestMethod.POST)  
	@ResponseBody
    public ResBaseAttachment imageUpload(@RequestBody MultipartFile file);
	
	@RequestMapping(value="/file_upload",method=RequestMethod.POST)  
	@ResponseBody
    public ResBaseAttachment fileUpload(@RequestBody MultipartFile file);
	
	@RequestMapping(value="/create_image",method=RequestMethod.POST)  
	@ResponseBody
    public String createImage(@RequestParam("fileName") String fileName, @RequestParam("is") InputStream is);

	@RequestMapping(value="/detail/{id}",method=RequestMethod.POST)  
	@ResponseBody
	public ResBaseAttachment find(@PathVariable("id")Long id);

	/**
	 * @Description  新增图片
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@RequestMapping(value="/save",method=RequestMethod.POST)  
	@ResponseBody
	public void save(@RequestBody ReqAttachment image);
	
	
}
