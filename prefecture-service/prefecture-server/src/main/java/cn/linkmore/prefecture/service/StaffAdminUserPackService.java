package cn.linkmore.prefecture.service;

import java.util.HashMap;

/**
 * @author   GFF
 * @Date     2018年9月17日
 * @Version  v2.0
 */
public interface StaffAdminUserPackService {

	/**
	 * @Description  
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	HashMap<String, Object> findByAdminId(Long adminid);

}
