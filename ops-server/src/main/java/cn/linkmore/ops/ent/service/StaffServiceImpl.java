package cn.linkmore.ops.ent.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
/**
 * 员工接口实现
 * @author   GFF
 * @Date     2018年7月25日
 * @Version  v2.0
 */
@Service
public class StaffServiceImpl implements StaffService {

//	@Resource
//	private 
	
	@Override
	public ViewPage findPage(ViewPageable pageable) {
		
		return null;
	}

}
