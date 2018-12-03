package cn.linkmore.enterprise.service;

import java.util.List;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.entity.EntRentUser;
import cn.linkmore.enterprise.request.ReqCheck;
import cn.linkmore.enterprise.request.ReqRentUser;
import cn.linkmore.enterprise.response.ResEntRentUser;

public interface EntRentUserService {
	
	/**
	 * 新增长租用户信息
	 * 
	 * @author luzhishen
	 * @Date 2018年7月20日
	 * @Version v1.0
	 */
	int saveEntRentUser(Long entId, Long entPreId, Long stallId, String mobile, String realname, String plate);
	/**
	 * 删除长租用户信息
	 * 
	 * @author luzhishen
	 * @Date 2018年7月20日
	 * @Version v1.0
	 */
	int deleteEntRentUser(Long id);

	/**
	 * 修改长租用户信息
	 * 
	 * @author luzhishen
	 * @Date 2018年7月20日
	 * @Version v1.0
	 */
	int updateEntRentUser(Long id, String mobile, String realname, String plate);
	
	/**
	 * @Description  删除
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void delete(List<Long> ids);
	
	/**
	 * @Description  分页查询
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	ViewPage findList(ViewPageable pageable);
	
	/**
	 * @Description  新增
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void save(ReqRentUser user);
	
	/**
	 * @Description  更新
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void update(ReqRentUser user);
	
	/**
	 * @Description  查询所有长租用户
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<EntRentUser> findAll();
	
	/**
	 * @Description  根据stallid查询
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	EntRentUser findByStallId(Long id);
	
	
	/**
	 * @Description  校验
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	Integer check(ReqCheck reqCheck);
	/**
	 * 查询已使用固定车位列表
	 * @return
	 */
	List<ResEntRentUser> findUsedStall();
	
	void saveBatch(List<ReqRentUser> rus);

}
