package cn.linkmore.security.dao.cluster;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.security.entity.PageElement;
import cn.linkmore.security.response.ReqAuthElement;
/**
 * 页面元素
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Mapper
public interface PageElementClusterMapper {

    PageElement findById(Long id);

	List<ReqAuthElement> findReqAuthElementList();

	Integer check(Map<String, Object> param);

	Integer count(Map<String, Object> param);

	List<PageElement> findPage(Map<String, Object> param);

	List<PageElement> findAll();

}