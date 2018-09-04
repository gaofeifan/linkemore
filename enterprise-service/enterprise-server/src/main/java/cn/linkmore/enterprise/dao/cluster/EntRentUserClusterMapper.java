package cn.linkmore.enterprise.dao.cluster;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.enterprise.entity.EntRentUser;
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
	List<EntRentUser> findPage(Map<String, Object> param);

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

}