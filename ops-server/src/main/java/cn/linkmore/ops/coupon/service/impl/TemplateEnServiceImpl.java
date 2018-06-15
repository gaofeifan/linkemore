package cn.linkmore.ops.coupon.service.impl;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.coupon.client.TemplateEnClient;
import cn.linkmore.coupon.request.ReqCheck;
import cn.linkmore.coupon.request.ReqTemplate;
import cn.linkmore.coupon.response.ResTemplate;
import cn.linkmore.ops.coupon.service.TemplateEnService;

@Service
public class TemplateEnServiceImpl implements TemplateEnService {
	@Autowired
	private TemplateEnClient templateEnClient;
	
	@Override
	public ViewPage findPage(ViewPageable pageable) {
		return this.templateEnClient.list(pageable);
	}


	@Override
	public int save(ReqTemplate record) {
		return this.templateEnClient.save(record);
	}
	
	@Override
	public int update(ReqTemplate record) {
		return this.templateEnClient.update(record);
	}

	@Override
	public int delete(Long id) {
		List<Long> ids = new ArrayList<Long>();
		ids.add(id);
		return this.templateEnClient.delete(ids);
	}
	
	@Override
	public Boolean check(ReqCheck reqCheck) {
		return this.templateEnClient.check(reqCheck); 
	}

	@Override
	public int start(Long id) {
		return this.templateEnClient.start(id);
	}

	private String downloadFile(String urlString){
		String name = new Date().getTime()+".jpg";
		String originalUrl = "";
		URL url;
		try {
			url = new URL(urlString);
			URLConnection con = url.openConnection();
            InputStream is = con.getInputStream();
            //originalUrl = createImage(name,is);
            is.close();
		} catch (Exception  e) {
			e.printStackTrace();
		}
		return originalUrl;
	}
	
	/*private String createImage(String fileName,InputStream is) {
		Attachment image = new Attachment();
		//image.setId(uuid.toString());
		image.setCreateTime(new Date()); 
		image.setSource(Attachment.SOURCE_SERVER);
		image.setType(Attachment.TYPE_IMAGE);
		image.setSize(0l);
		image.setName(fileName);
		int index = fileName.lastIndexOf("."); 
		image.setSuffix(fileName.substring(index));
		this.attachmentMapper.save(image);
		try { 
			client.putObject(ossConfig.getBucketName(), image.getOriginalUrl(), is); 
		} catch (Exception e) { 
			throw new RuntimeException();
		}finally{
			if(is!=null){try {is.close();} catch (IOException e) {e.printStackTrace();}}  
		} 
		return image.getOriginalUrl();
	}*/
	@Override
	public int stop(Long id) {
		return this.templateEnClient.down(id);
	}

	@Override
	public ResTemplate findById(Long id) {
		return this.templateEnClient.detail(id);
	}

	@Override
	public List<ResTemplate> findByEnterpriseId(Long entId) {
		return this.templateEnClient.findByEnterpriseId(entId);
	}

	@Override
	public int saveBusiness(ReqTemplate record) {
		return this.templateEnClient.saveBusiness(record);
	}
	
}
