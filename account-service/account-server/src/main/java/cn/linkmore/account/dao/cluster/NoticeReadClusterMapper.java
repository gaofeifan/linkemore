package cn.linkmore.account.dao.cluster;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.account.entity.NoticeRead;

/**
 * @author   GFF
 * @Date     2018年6月9日
 * @Version  v2.0
 */
@Mapper
public interface NoticeReadClusterMapper {

    /**
     * @Description  
     * @Author   GFF 
     * @Version  v2.0
     */
    NoticeRead findById(Long id);

	/**
	 * @Description  
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	NoticeRead findNotReadByNid(Map<String, Object> map);

}