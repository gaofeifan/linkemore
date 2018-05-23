package cn.linkmore.security.shiro;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import cn.linkmore.security.dao.cluster.InterfaceClusterMapper;
import cn.linkmore.security.entity.Interface;


 
/**
 * Service实现类 - 受控资源.
 * <p><br>
 * @author liwenlong
 * @version 1.0.0, 2014年4月11日
 */ 

@Component
public class FilterChainService{ 
	/**
	 * 注入dao
	 */
	@Autowired
	private InterfaceClusterMapper interfaceClusterMapper;								
																												
	 	
	public static final String PERMISSION_STRING="perms[{0}]";
	
	 
 
	public Map<String,String> getFilterChainMap() throws Exception { 
		 
		List<Interface> list = this.interfaceClusterMapper.findAll();
        Map<String,String> section = new HashMap<String,String>(); 
        if(list!=null&&list.size()>0)
        for(Interface re:list){
        	if(StringUtils.isNotEmpty(re.getPath())&&re.getAuthorize()==1){
        		section.put(re.getPath(),  MessageFormat.format(PERMISSION_STRING,re.getId()));
        	}
        }  
        return section;
	} 
}
