package cn.linkmore.enterprise.dao.master;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.enterprise.entity.EntRentedRecord;
/**
 * 长租用户会用记录--写
 * @author   GFF
 * @Date     2018年7月18日
 * @Version  v2.0
 */
@Mapper
public interface EntRentedRecordMasterMapper {
    int deleteById(Long id);

    int save(EntRentedRecord record);

    int saveSelective(EntRentedRecord record);

    int updateByIdSelective(EntRentedRecord record);

    int updateById(EntRentedRecord record);

	void updateDownTime(Map<String, Object> map);
	/**
	 * 更新使用记录状态
	 * @param map
	 */
	void updateStatus(Map<String, Object> map);
	
	void updateRentUserStatus(Map<String, Object> map);
	
}