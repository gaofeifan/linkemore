package cn.linkmore.user.service;

import java.util.List;

import cn.linkmore.user.response.ResUserGuide;

/**
 * 用户指南
 * @author   GFF
 * @Date     2018年6月9日
 * @Version  v2.0
 */
public interface UserGuideService {

	/**
	 * @Description  
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<ResUserGuide> find(String language);

}
