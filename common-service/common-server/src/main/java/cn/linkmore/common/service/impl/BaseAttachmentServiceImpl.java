package cn.linkmore.common.service.impl;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.imageio.ImageIO;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import cn.linkmore.bean.view.ViewFilter;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.common.dao.cluster.BaseAttachmentClusterMapper;
import cn.linkmore.common.dao.master.BaseAttachmentMasterMapper;
import cn.linkmore.common.entity.BaseAttachment;
import cn.linkmore.common.request.ReqAttachment;
import cn.linkmore.common.service.BaseAttachmentService;
import cn.linkmore.common.service.OssService;
import cn.linkmore.third.client.OssClient;
import cn.linkmore.util.DomainUtil;
import cn.linkmore.util.ObjectUtils;
/**
 * 文件管理--实现
 * @author   GFF
 * @Date     2018年6月6日
 * @Version  v2.0
 */
@Service
public class BaseAttachmentServiceImpl implements BaseAttachmentService {

	@Resource
	private BaseAttachmentClusterMapper attachmentClusterMapper;
	@Resource
	private BaseAttachmentMasterMapper attachmentmasterMapper;
	@Resource
	private OssService ossService;
	@Override
	public BaseAttachment find(Long id){
		return this.attachmentClusterMapper.findById(id);
	}
	
	@Override
	public ViewPage findPage(ViewPageable pageable) { 
		Map<String,Object> param = new HashMap<String,Object>(); 
		List<ViewFilter> filters = pageable.getFilters();
		if(StringUtils.isNotBlank(pageable.getSearchProperty())) {
			param.put(pageable.getSearchProperty(), pageable.getSearchValue());
		}
		if(filters!=null&&filters.size()>0) {
			for(ViewFilter filter:filters) {
				param.put(filter.getProperty(), filter.getValue());
			}
		}
		if(StringUtils.isNotBlank(pageable.getOrderProperty())) {
			param.put("property", DomainUtil.camelToUnderline(pageable.getOrderProperty()));
			param.put("direction", pageable.getOrderDirection());
		}
		Integer count = this.attachmentClusterMapper.count(param);
		param.put("start", pageable.getStart());
		param.put("pageSize", pageable.getPageSize());
		List<BaseAttachment> list = this.attachmentClusterMapper.findPage(param);
		return new ViewPage(count,pageable.getPageSize(),list); 
	}

	@Override
	@Transactional(rollbackFor=RuntimeException.class)
	public BaseAttachment saveImage(MultipartFile file) { 
		BaseAttachment image = new BaseAttachment();  
		image.setCreateTime(new Date()); 
		image.setSource((short) 0);
		image.setType((short) 0);
		image.setSize((int)file.getSize());
		image.setName(file.getOriginalFilename());
		int index = file.getOriginalFilename().lastIndexOf("."); 
		image.setSuffix(file.getOriginalFilename().substring(index)); 
		this.attachmentmasterMapper.save(image);  
		ossService.uploadImage(file, image.getId());
		return image;
	} 
	

	@Override
	@Transactional(rollbackFor=RuntimeException.class)
	public boolean delete(Long id) { 
		boolean flag = false;
		/*BaseAttachment attach = this.attachmentClusterMapper.findById(id); 
		try{ 
			switch(attach.getType()){
				case 0:{
					ossClient.uploadOSSClient().deleteObject(resOssConfig.getBucketName(), attach.getCompressUrl());
					ossClient.uploadOSSClient().deleteObject(resOssConfig.getBucketName(), attach.getOriginalUrl());
					flag = true;
				};break;
				case 1:{
					ossClient.uploadOSSClient().deleteObject(resOssConfig.getBucketName(), attach.getFileUrl());
					flag = true;
				};break;
			}
		}catch(Exception e){
		} */
		this.attachmentmasterMapper.deleteById(id);
		return flag = true; 
	}

	@Override
	@Transactional(rollbackFor=RuntimeException.class)
	public BaseAttachment saveFile(MultipartFile file) {
		BaseAttachment attach = new BaseAttachment(); 
		attach.setCreateTime(new Date()); 
		attach.setSource((short)0);
		attach.setType((short)1);
		attach.setSize((int)file.getSize());
		attach.setName(file.getOriginalFilename()); 
		int index = file.getOriginalFilename().lastIndexOf("."); 
		attach.setSuffix(file.getOriginalFilename().substring(index)); 
		this.attachmentmasterMapper.save(attach);
		ossService.uploadFile(file, attach.getId());
		return attach;
	}

	@Override
	public String createImage(String fileName, InputStream is) {
		BaseAttachment image = new BaseAttachment();
		image.setCreateTime(new Date()); 
		image.setSource((short)0);
		image.setType((short)0);
		image.setSize(0);
		image.setName(fileName);
		int index = fileName.lastIndexOf("."); 
		image.setSuffix(fileName.substring(index));
		this.attachmentmasterMapper.save(image);
		try { 
//			ossClient.uploadImage(image, id); 
		} catch (Exception e) { 
			throw new RuntimeException();
		}finally{
			if(is!=null){try {is.close();} catch (IOException e) {e.printStackTrace();}}  
		} 
		return image.getOriginalUrl();
	}

	@Override
	public void save(ReqAttachment image) {
		BaseAttachment object = ObjectUtils.copyObject(image, new BaseAttachment(),null,null,new String[] {"size"});
		object.setSize(image.getSource().intValue());
		this.attachmentmasterMapper.save(object);
	}

	
	
}
