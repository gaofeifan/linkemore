package cn.linkmore.common.dao.cluster;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.common.entity.Attachment;
import cn.linkmore.common.entity.BaseAttachment;

/**
 * @author   GFF
 * @Date     2018年6月7日
 * @Version  v2.0
 */
@Mapper
public interface BaseAttachmentClusterMapper {

    /**
     * @Description  
     * @Author   GFF 
     * @Version  v2.0
     */
    BaseAttachment findById(Long id);

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
	List<Attachment> findPage(Map<String, Object> param);

}