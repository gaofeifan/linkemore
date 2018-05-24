package cn.linkmore.security.dao.cluster;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.security.response.ResAuthElement;
import cn.linkmore.security.response.ResPageElement;
/**
 * 页面元素
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Mapper
public interface PageElementClusterMapper {

    ResPageElement findById(Long id);

	List<ResAuthElement> findResAuthElementList();

	Integer check(Map<String, Object> param);

	Integer count(Map<String, Object> param);

	List<ResPageElement> findPage(Map<String, Object> param);

	List<ResPageElement> findAll();

}