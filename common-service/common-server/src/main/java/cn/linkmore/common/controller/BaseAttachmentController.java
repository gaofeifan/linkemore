package cn.linkmore.common.controller;

import java.io.InputStream;

import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.common.entity.BaseAttachment;
import cn.linkmore.common.response.ResBaseAttachment;
import cn.linkmore.common.service.BaseAttachmentService;
import cn.linkmore.util.ObjectUtils;
/**
 * 文件管理
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Controller
@RequestMapping("/attach")
public class BaseAttachmentController {
	
	@Resource
	private BaseAttachmentService attachmentService;
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public boolean delete(@RequestParam("id") Long id){ 
		return this.attachmentService.delete(id);
	} 
	
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(@RequestBody ViewPageable pageable){
		return this.attachmentService.findPage(pageable); 
	} 
	
	@RequestMapping(value="/image_upload",method=RequestMethod.POST)  
	@ResponseBody
    public ResBaseAttachment imageUpload(@RequestParam("file") MultipartFile file) { 
		BaseAttachment attach = this.attachmentService.saveImage(file);
		return ObjectUtils.copyObject(attach, new ResBaseAttachment());
    } 
	
	@RequestMapping(value="/file_upload",method=RequestMethod.POST)  
	@ResponseBody
    public ResBaseAttachment fileUpload(@RequestParam("file") MultipartFile file) {
		BaseAttachment attach = this.attachmentService.saveFile(file); 
		return ObjectUtils.copyObject(attach, new ResBaseAttachment());
    }
	
	@RequestMapping(value="/create_image",method=RequestMethod.POST)  
	@ResponseBody
    public String createImage(@RequestParam("fileName") String fileName, @RequestParam("is") InputStream is){
    	return this.attachmentService.createImage(fileName,is); 
    }
}
