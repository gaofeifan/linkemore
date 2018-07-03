package cn.linkmore.ops.coupon.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import cn.linkmore.bean.exception.DataException;
import cn.linkmore.bean.view.ViewFilter;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.coupon.client.TemplateClient;
import cn.linkmore.coupon.request.ReqCheck;
import cn.linkmore.coupon.request.ReqTemplate;
import cn.linkmore.coupon.response.ResQrc;
import cn.linkmore.coupon.response.ResTemplate;
import cn.linkmore.coupon.response.ResTemplateItem;
import cn.linkmore.ops.coupon.service.TemplateService;
import cn.linkmore.util.DateUtils;
import cn.linkmore.util.DomainUtil;
import cn.linkmore.util.ObjectUtils;



@Service
public class TemplateServiceImpl implements TemplateService {
	@Autowired
	private TemplateClient templateClient;
	
	@Override
	public ViewPage findPage(ViewPageable pageable) {
		return this.templateClient.list(pageable);
	}

	@Override
	public int save(ReqTemplate reqTemp) {
		return this.templateClient.save(reqTemp);
	}
	
	@Override
	public int update(ReqTemplate reqTemp) {
		return this.templateClient.update(reqTemp);
	}

	@Override
	public int delete(Long id) {
		List<Long> ids = new ArrayList<Long>();
		ids.add(id);
		return this.templateClient.delete(ids);
	}
	
	@Override
	public Boolean check(ReqCheck reqCheck) {
		return this.templateClient.check(reqCheck); 
	}

	@Override
	public int start(Long id) {
		return this.templateClient.start(id);
	}
	
	@Override
	public int stop(Long id) {
		return this.templateClient.down(id);
	}

	@Override
	public ResTemplate findById(Long id) {
		return this.templateClient.detail(id);
	}

	@Override
	public ResQrc download(Long id) {
		return this.templateClient.download(id);
	}
}
