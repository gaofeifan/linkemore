package cn.linkmore.account.dao.cluster;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.account.entity.Notice;
import cn.linkmore.account.entity.NoticeRead;
import cn.linkmore.account.request.ReqNotice;
import cn.linkmore.account.response.ResPageNotice;

/**
 * 消息--读
 * @author   GFF
 * @Date     2018年6月9日
 * @Version  v2.0
 */
@Mapper
public interface NoticeClusterMapper {
    /**
     * @Description  
     * @Author   GFF 
     * @Version  v2.0
     */
    int deleteById(Long id);

    /**
     * @Description  
     * @Author   GFF 
     * @Version  v2.0
     */
    int save(Notice record);

    /**
     * @Description  
     * @Author   GFF 
     * @Version  v2.0
     */
    int saveSelective(Notice record);

    /**
     * @Description  
     * @Author   GFF 
     * @Version  v2.0
     */
    Notice findById(Long id);

    /**
     * @Description  
     * @Author   GFF 
     * @Version  v2.0
     */
    int updateByIdSelective(Notice record);

    /**
     * @Description  
     * @Author   GFF 
     * @Version  v2.0
     */
    int updateById(Notice record);

	/**
	 * @Description  
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<ResPageNotice> findPageNotice(Map<String, Object> map);

	/**
	 * @Description  
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<Long> findNoticeReadDel(Long userId);

	/**
	 * @Description  
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	int findNotReadCount(Long userId);

	/**
	 * @Description  
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	NoticeRead findNoticeReadByNid(ReqNotice notice);

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
	List<Notice> findPage(Map<String, Object> param);

	Integer findNotReadNotice(Long id);

	/**
	 * @Description  
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<Long> findNotReadList(Long id);


}