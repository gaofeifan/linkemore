package cn.linkmore.common.controller.ops;

import java.io.InputStream;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.linkmore.bean.view.ViewMsg;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.common.entity.BaseAttachment;
import cn.linkmore.common.request.ReqAttachment;
import cn.linkmore.common.response.ResBaseAttachment;
import cn.linkmore.common.service.BaseAttachmentService;
import cn.linkmore.util.ObjectUtils;

/**
 * 文件管理
 * 
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
	public boolean delete(@RequestParam("id") Long id) {
		return this.attachmentService.delete(id);
	}

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(@RequestBody ViewPageable pageable) {
		return this.attachmentService.findPage(pageable);
	}

	@RequestMapping(value = "/image_upload", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg imageUpload(@RequestParam("file") MultipartFile file) {
		ViewMsg msg = null;
		try {
			BaseAttachment attach = this.attachmentService.saveImage(file);
			msg = new ViewMsg("上传成功", true);
			msg.add("attach", attach);
		} catch (Exception e) {
			msg = new ViewMsg("上传失败", false);
		}
		return msg;
	}

	@RequestMapping(value = "/file_upload", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg fileUpload(@RequestParam("file") MultipartFile file) {
		ViewMsg msg = null;
		try {
			BaseAttachment attach = this.attachmentService.saveFile(file);
			msg = new ViewMsg("上传成功", true);
			msg.add("attach", attach);
		} catch (Exception e) {
			msg = new ViewMsg("上传失败", false);
		}
		return msg;
	}

	@RequestMapping(value = "/create_image", method = RequestMethod.POST)
	@ResponseBody
	public String createImage(@RequestParam("fileName") String fileName, @RequestParam("is") InputStream is) {
		return this.attachmentService.createImage(fileName, is);
	}

	@RequestMapping(value = "/detail/{id}", method = RequestMethod.POST)
	@ResponseBody
	public ResBaseAttachment find(@PathVariable("id") Long id) {
		BaseAttachment attachment = this.attachmentService.find(id);
		ResBaseAttachment object = ObjectUtils.copyObject(attachment, new ResBaseAttachment(), null, null,
				new String[] { "size" });
		object.setSize(attachment.getSize());
		return object;
	}

	/**
	 * @Description 新增图片
	 * @Author GFF
	 * @Version v2.0
	 */

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public void save(@RequestBody ReqAttachment image) {
		this.attachmentService.save(image);
	}

}
