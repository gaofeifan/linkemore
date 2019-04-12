package cn.linkmore.enterprise.dao.master;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import cn.linkmore.enterprise.entity.AuthRecord;
@Mapper
public interface AuthRecordMasterMapper {

    int save(AuthRecord record);

    int update(AuthRecord record);
    
    int cancelAuth(Long id);
    /**
     * 开启1关闭0默认开启 
     * @param param
     * @return
     */
    int operateSwitch(Map<String,Object> param);
    /**
     * 更新过期状态
     * @return
     */
	int updateOverdueStatus();
	/**
	 * 更新已失效状态
	 * @param authIds
	 */
	int batchUpdate(List<Long> authIds);
}