package cn.linkmore.enterprise.dao.cluster;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.enterprise.entity.EntOwnerStall;
import cn.linkmore.enterprise.entity.EntRentUser;
import cn.linkmore.enterprise.response.ResEntRentUser;
/**
 * 长租用户---读
 * @author   GFF
 * @Date     2018年7月18日
 * @Version  v2.0
 */
@Mapper
public interface EntRentUserClusterMapper {

    /**
     * @Description  
     * @Author   GFF 
     * @Version  v2.0
     */
    EntRentUser findById(Long id);

	/**
	 * @Description  
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<EntRentUser> findByIdEntPreId(Long entPreId);

	/**
	 * @Description  查询总数
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	Integer count(Map<String, Object> param);

	/**
	 * @Description  分页查询
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<ResEntRentUser> findPage(Map<String, Object> param);

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
	Integer check(Map<String, Object> param);
	/**
	 * 查询已使用固定车位列表
	 * @return
	 */
	List<ResEntRentUser> findUsedStall();
	
	/**
	 * 查询企业用户长租车位用户权限
	 * @param param
	 * @return
	 */
	List<EntRentUser> findRentComUserList(Map<String, Object> param);
	/**
	 * 查询长租用户列表
	 * @param param
	 * @return
	 */
	List<EntRentUser> findComUserList(Map<String, Object> param);

	List<ResEntRentUser> findResAll(Map<String, Object> param);

	/**
	 * 查询公司下长租用户列表
	 * @param param
	 * @return
	 */
	List<EntRentUser> findCompanyUserList(Map<String, Object> param);
	
	/**
	 * 查询个人长租车位用户列表
	 * @param param
	 * @return
	 */
	List<EntRentUser> findPersonalUserList(Map<String, Object> param);
	/**
	 * 查询个人长租车位用户应有权限
	 * @param param
	 * @return
	 */
	List<EntRentUser> findRentPersonalUserList(Map<String, Object> param);
}