package cn.linkmore.third.service.impl;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import cn.linkmore.bean.exception.BusinessException;
import cn.linkmore.bean.exception.StatusEnum;
import cn.linkmore.third.config.BeanFactory;
import cn.linkmore.third.config.OssConfig;
import cn.linkmore.third.service.OssService;

/**
 * Service - 推送
 * @author liwenlong
 * @version 2.0
 *
 */
@Service
public class OssServiceImpl implements OssService {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private BeanFactory beanFactory;
	
	@Autowired
	private OssConfig ossConfig;

	
	
	private final static String DAY_FORMAT = "yyyy/MM/dd/";
	private final static String  IMAGE_PATH = "image/";
	private final static String FILE_PATH = "file/";
	private final static String IMAGE_MINI = "_min";
	private final static String  IMAGE_MAX = "_max";
	private final static String POINT = ".";
	private final static String JPG = "jpg";
	
	@Override
	public void uploadFile(MultipartFile file, Long id) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/");
		String date = sdf.format(new Date());
		int index = file.getOriginalFilename().lastIndexOf(POINT); 
		String suffix = file.getOriginalFilename().substring(index);  
		StringBuffer url = new StringBuffer();
		url.append(FILE_PATH).append(date).append(id).append(suffix);
		try {
			beanFactory.uploadOSSClient().putObject(ossConfig.getBucketName(), url.toString(), file.getInputStream());
		} catch (Exception e) {  
			throw new BusinessException(StatusEnum.THIRD_FILE_UPLOAD_ERROR);
		} 
	}

	@Override
	public void uploadImage(MultipartFile image,Long id) {
		FileOutputStream out = null;
		InputStream is = null;
		SimpleDateFormat sdf = new SimpleDateFormat(DAY_FORMAT);
		String date = sdf.format(new Date());
		StringBuffer url =null;
		int index = image.getOriginalFilename().lastIndexOf(POINT); 
		String suffix = image.getOriginalFilename().substring(index);  
		try {  
			url = new StringBuffer();
			url.append(IMAGE_PATH).append(date).append(id).append(IMAGE_MAX).append(suffix);
			beanFactory.uploadOSSClient().putObject(ossConfig.getBucketName(), url.toString(), image.getInputStream()); 
			File mini = new File(ossConfig.getTempDir()+image.getOriginalFilename());
			try{ 
				int outputWidth = 600; 
				Image img = ImageIO.read(image.getInputStream());
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
				ImageIO.write(tag, JPG, out);
				out.flush();  
			}catch(IOException e){
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
			url = new StringBuffer();
			url.append(IMAGE_PATH).append(date).append(id).append(IMAGE_MINI).append(suffix);
			beanFactory.uploadOSSClient().putObject(ossConfig.getBucketName(), url.toString(),new FileInputStream(mini));  
			is.close();
		}  catch (Exception e) { 
			throw new BusinessException(StatusEnum.THIRD_IMAGE_UPLOAD_ERROR);
		} finally {

			if(is!=null){
				try {
					is.close();
				} catch (IOException e) { 
					e.printStackTrace();
				}
			}  
		}
		 
	}
 
}
