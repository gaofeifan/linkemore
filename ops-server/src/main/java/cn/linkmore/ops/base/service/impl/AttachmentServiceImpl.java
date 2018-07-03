package cn.linkmore.ops.base.service.impl;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import javax.annotation.Resource;
import javax.imageio.ImageIO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.aliyun.oss.OSSClient;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.common.client.BaseAttachmentClient;
import cn.linkmore.common.request.ReqAttachment;
import cn.linkmore.common.response.ResBaseAttachment;
import cn.linkmore.ops.base.service.AttachmentService;
import cn.linkmore.third.client.OssClient;
import cn.linkmore.third.response.ResOssConfig;

/**
 * 文件上传---接口实习那么
 * @author   GFF
 * @Date     2018年6月25日
 * @Version  v2.0
 */
@Service
public class AttachmentServiceImpl implements AttachmentService {

	@Resource
	private BaseAttachmentClient attachmentClient;
	
	@Resource
	private OssClient ossClient;
	@Override
	public void delete(Long id) {
		this.attachmentClient.delete(id);
	}

	@Override
	public ViewPage findPage(ViewPageable pageable) {
		return this.attachmentClient.list(pageable);
	}

	@Override
	public ResBaseAttachment find(Long id) {
		return this.attachmentClient.find(id);
	}

	@Override
//	@Transactional(rollbackFor=RuntimeException.class)
	public ReqAttachment saveImage(MultipartFile file, ResOssConfig resOssConfig) { 
		ReqAttachment image = new ReqAttachment();  
		image.setCreateTime(new Date()); 
		image.setSource((short) 0);
		image.setType((short) 0);
		image.setSize(file.getSize());
		image.setName(file.getOriginalFilename());
		int index = file.getOriginalFilename().lastIndexOf("."); 
		image.setSuffix(file.getOriginalFilename().substring(index)); 
		this.attachmentClient.save(image);
		FileOutputStream out = null;
		InputStream is = null;
		try {
			OSSClient client = ossClient.uploadOSSClient();
			client.putObject(resOssConfig.getBucketName(), image.getOriginalUrl(), file.getInputStream());
			File mini = new File(resOssConfig.getTempDir()+file.getOriginalFilename());
			try{ 
				int outputWidth = 600;
				is = file.getInputStream();
				Image img = ImageIO.read(is);
				int newWidth;
				int newHeight;
				int w = img.getWidth(null);
				if (w <= 600) {
					outputWidth = w;
				}
				double rate1 = ((double) img.getWidth(null)) / (double) outputWidth;
				double rate = rate1;
				newWidth = (int) (((double) img.getWidth(null)) / rate);
				newHeight = (int) (((double) img.getHeight(null)) / rate);
				BufferedImage tag = new BufferedImage((int) newWidth,
						(int) newHeight, BufferedImage.TYPE_INT_RGB);

				tag.getGraphics().drawImage(
						img.getScaledInstance(newWidth, newHeight,
								Image.SCALE_SMOOTH), 0, 0, null);
				out = new FileOutputStream(mini);
				ImageIO.write(tag, "jpg", out);
				out.flush();  
			}catch(IOException e){
				e.printStackTrace();
				throw new RuntimeException();
			} finally{
				if(out!=null){
					try {
						out.close();
					} catch (IOException e) { 
						e.printStackTrace();
					}
				}
			} 
			is = new FileInputStream(mini);
			ossClient.uploadOSSClient().putObject(resOssConfig.getBucketName(), image.getCompressUrl(),new FileInputStream(mini));  
		}  catch (IOException e) { 
			throw new RuntimeException();
		}finally{
			if(is!=null){
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}  
		} 
		return image;
	} 
	
	@Override
	public ReqAttachment saveFile(MultipartFile file, ResOssConfig resOssConfig) {
		ReqAttachment attach = new ReqAttachment(); 
		attach.setCreateTime(new Date());
		attach.setSource((short)0);
		attach.setType((short)1);
		attach.setSize(file.getSize());
		attach.setName(file.getOriginalFilename()); 
		int index = file.getOriginalFilename().lastIndexOf("."); 
		attach.setSuffix(file.getOriginalFilename().substring(index)); 
		this.attachmentClient.save(attach);
		try { 
			ossClient.uploadOSSClient().putObject(resOssConfig.getBucketName(), attach.getFileUrl(), file.getInputStream());
		}  catch (IOException e) {
			throw new RuntimeException();
		}  
		return attach;
	}

	
	
	
}
