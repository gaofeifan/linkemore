package cn.linkmore.prefecture.dao.master;

import org.apache.ibatis.annotations.Mapper;
import cn.linkmore.prefecture.entity.EntRentRecord;
/**
 * 长租用户会用记录--写
 * @author  cl
 * @Date     2018年7月18日
 * @Version  v2.0
 */
@Mapper
public interface EntRentRecordMasterMapper {
	
    int deleteById(Long id);

    int save(EntRentRecord record);

    int saveSelective(EntRentRecord record);

    int updateByIdSelective(EntRentRecord record);

    int updateById(EntRentRecord record);
}