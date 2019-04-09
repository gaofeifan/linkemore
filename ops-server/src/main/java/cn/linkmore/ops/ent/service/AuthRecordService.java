package cn.linkmore.ops.ent.service;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;

/**
 * 授权使用记录接口
 * @author   jhb
 * @Date     2018年7月30日
 * @Version  v2.0
 */
public interface AuthRecordService {

	/**
	 * @Description  分页查询
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	ViewPage findList(HttpServletRequest request, ViewPageable pageable);
	
	int operateSwitch(Map<String,Object> param);

}
