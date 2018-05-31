package cn.linkmore.ops.shiro;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.linkmore.ops.security.response.ResInterface;
import cn.linkmore.ops.security.service.InterfaceService;


 
/**
 * Service实现类 - 受控资源.
 * <p><br>
 * @author jiaohanbin
 * @version 2.0.0, 2014年4月11日
 */ 

@Component
public class FilterChainService{ 
	
	@Autowired
	private InterfaceService interfaceService;	
	private  final Logger log = LoggerFactory.getLogger(this.getClass());
	public static final String PERMISSION_STRING="perms[{0}]";
 
	public Map<String,String> getFilterChainMap() throws Exception { 
		List<ResInterface> list = this.interfaceService.findAll();
		log.info("interface list size {}" , list.size());
        Map<String,String> section = new HashMap<String,String>(); 
        if(list!=null&&list.size()>0)
        for(ResInterface re:list){
        	if(StringUtils.isNotEmpty(re.getPath())&&re.getAuthorize()==1){
        		section.put(re.getPath(),  MessageFormat.format(PERMISSION_STRING,re.getId()));
        	}
        }  
        return section;
	} 
}
