package cn.linkmore.common.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.linkmore.common.dao.cluster.BaseAttachmentClusterMapper;
import cn.linkmore.common.dao.master.BaseAttachmentMasterMapper;
import cn.linkmore.common.service.BaseAttachmentService;
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
	/*
	@Resource(name="baseOSSClient")
	private OSSClient client;
	
	@Resource
	private BaseOssConfig baseOssConfig;
	
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
		List<Attachment> list = this.attachmentClusterMapper.findPage(param);
		return new ViewPage(count,pageable.getPageSize(),list); 
	}

	@Override
	@Transactional(rollbackFor=RuntimeException.class)
	public BaseAttachment saveImage(MultipartFile file) { 
		BaseAttachment image = new BaseAttachment();  
		image.setCreateTime(new Date()); 
		image.setSource((short) 0);
		image.setType((short) 0);
		image.setSize(file.getSize());
		image.setName(file.getOriginalFilename());
		int index = file.getOriginalFilename().lastIndexOf("."); 
		image.setSuffix(file.getOriginalFilename().substring(index)); 
		this.attachmentmasterMapper.save(image);  
		FileOutputStream out = null;
		InputStream is = null;
		try { 
			client.putObject(baseOssConfig.getBucketName(), image.getOriginalUrl(), file.getInputStream()); 
			File mini = new File(baseOssConfig.getTemp()+file.getOriginalFilename());
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
			client.putObject(baseOssConfig.getBucketName(), image.getCompressUrl(),new FileInputStream(mini));  
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
	@Transactional(rollbackFor=RuntimeException.class)
	public boolean delete(Long id) { 
		BaseAttachment attach = this.attachmentClusterMapper.findById(id); 
		boolean flag = false;
		try{ 
			switch(attach.getType()){
				case 0:{
					client.deleteObject(baseOssConfig.getBucketName(), attach.getCompressUrl());
					client.deleteObject(baseOssConfig.getBucketName(), attach.getOriginalUrl());
					flag = true;
				};break;
				case 1:{
					client.deleteObject(baseOssConfig.getBucketName(), attach.getFileUrl());
					flag = true;
				};break;
			}
		}catch(Exception e){
		} 
		this.attachmentmasterMapper.deleteById(id);
		return flag; 
	}

	@Override
	@Transactional(rollbackFor=RuntimeException.class)
	public BaseAttachment saveFile(MultipartFile file) {
		BaseAttachment attach = new BaseAttachment(); 
		attach.setCreateTime(new Date()); 
		attach.setSource((short)0);
		attach.setType((short)1);
		attach.setSize(file.getSize());
		attach.setName(file.getOriginalFilename()); 
		int index = file.getOriginalFilename().lastIndexOf("."); 
		attach.setSuffix(file.getOriginalFilename().substring(index)); 
		this.attachmentmasterMapper.save(attach);
		try { 
			client.putObject(baseOssConfig.getBucketName(), attach.getFileUrl(), file.getInputStream());
		}  catch (IOException e) { 
			throw new RuntimeException();
		}  
		return attach;
	}*/
}
