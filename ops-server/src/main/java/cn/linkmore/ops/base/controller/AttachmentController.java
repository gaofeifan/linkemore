package cn.linkmore.ops.base.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/base/attachment")
public class AttachmentController {
	/*@Resource(name="baseOSSClient")
	private OSSClient client;
	
	@Resource
	private BaseOssConfig baseOssConfig;
	
	@Resource
	private AttachmentService attachmentService;
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg delete(Long id){ 
		ViewMsg msg = null;
		try {
			this.attachmentService.delete(id);
			msg = new ViewMsg("删除成功",true);
		}catch(DataException e) {
			msg = new ViewMsg(e.getMessage(),false);
		}catch(Exception e) {
			e.printStackTrace();
			msg = new ViewMsg("删除失败",false);
		}
		return msg;
	} 
	
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(HttpServletRequest request,ViewPageable pageable){
		return this.attachmentService.findPage(pageable); 
	} 
	
	@RequestMapping(value="/image_upload",method=RequestMethod.POST)  
	@ResponseBody
    public ViewMsg imageUpload(@RequestParam("file") MultipartFile file,HttpServletRequest request) {  
		 ViewMsg msg = null; 
		try{
			Attachment attach = this.attachmentService.saveImage(file); 
			msg = new ViewMsg("上传成功",true); 
			msg.add("attach", attach); 
		}catch(Exception e){  
			msg = new ViewMsg("上传失败",false);
		}  
        return msg;
    } 
	
	@RequestMapping(value="/file_upload",method=RequestMethod.POST)  
	@ResponseBody
    public ViewMsg fileUpload(@RequestParam("file") MultipartFile file,HttpServletRequest request) {  
		 ViewMsg msg = null; 
		try{
			Attachment attach = this.attachmentService.saveFile(file); 
			msg = new ViewMsg("上传成功",true); 
			msg.add("attach", attach); 
		}catch(Exception e){  
			msg = new ViewMsg("上传失败",false);
		}  
        return msg;
    } 
	*//**
	 * 处理下载请求
	 * @param id 主键
	 * @param response
	 *//* 
	@RequestMapping(value = "/download", method = RequestMethod.POST) 
 	public void download(Long id,HttpServletResponse response,HttpServletRequest request){ 
		Attachment attach = this.attachmentService.find(id);
		ServletOutputStream out = null;   
		if(attach!=null){  
			String key = null;
			switch(attach.getType()){
				case 0:{
					key = attach.getOriginalUrl();
				};break;
				case 1:{
					key = attach.getFileUrl();
				};break;
			} 
			OSSObject osso = null; 
			BufferedInputStream bis = null;
			BufferedOutputStream  bos = null;
			try{
				osso =   client.getObject(baseOssConfig.getBucketName(), key); 
				bis = new BufferedInputStream( osso.getObjectContent());
				response.setContentType("multipart/form-data");
				response.setHeader("Content-disposition", "attachment;filename="+URLEncoder.encode(attach.getName(), "utf-8")); 
				response.setCharacterEncoding("UTF-8");
				out = response.getOutputStream(); 
				bos = new BufferedOutputStream (out);
				byte[] buffer = new byte[1];  
				while(bis.read(buffer)!=-1){     
	                bos.write(buffer);     
	            }   
				bos.flush();
	            out.flush();   
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				
				if(bis!=null){
					try {
						bis.close();
					} catch (IOException e) { 
						e.printStackTrace();
					}
				}
				if(osso!=null){
					try {
						osso.close();
					} catch (IOException e) { 
						e.printStackTrace();
					}
				} 
				if(out!=null){
					try {
						out.close();
					} catch (IOException e) {}
				}
				if(bos!=null){
					try {
						bos.close();
					} catch (IOException e) { 
						e.printStackTrace();
					}
				}
			} 
		}else{ 
		} 
	}*/
}
