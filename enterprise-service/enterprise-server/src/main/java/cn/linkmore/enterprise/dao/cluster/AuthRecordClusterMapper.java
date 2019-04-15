package cn.linkmore.enterprise.dao.cluster;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.linkmore.enterprise.entity.AuthRecord;
@Mapper
public interface AuthRecordClusterMapper {
	/**
	 * 查看详情
	 * @param id
	 * @return
	 */
    AuthRecord findById(Long id);
    /**
     * 根据授权人id或者车区id查询授权记录
     * @param param
     * @return
     */
    List<AuthRecord> findRecordList(Map<String,Object> param);
    /**
     * 分页
     * @param param
     * @return
     */
    List<AuthRecord> findPage(Map<String,Object> param);
    /**
     * 查询记录数
     * @param param
     * @return
     */
    int count(Map<String,Object> param);
    
	AuthRecord findByUserId(@Param("userId")Long userId, @Param("stallId")Long stallId);
	
	public List<AuthRecord> findAuthUserIdAndStallId(@Param("userId")Long userId, @Param("stallId")Long stallId);

    
}