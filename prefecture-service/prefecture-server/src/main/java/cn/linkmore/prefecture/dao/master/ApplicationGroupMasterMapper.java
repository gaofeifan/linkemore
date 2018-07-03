package cn.linkmore.prefecture.dao.master;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.prefecture.entity.ApplicationGroup;
/**
 * 应用程序mapper  --- 写
 * @author   GFF
 * @Date     2018年6月23日
 * @Version  v2.0
 */
@Mapper
public interface ApplicationGroupMasterMapper {
    int deleteById(Long id);

    int save(ApplicationGroup record);

    int saveSelective(ApplicationGroup record);

    int updateByIdSelective(ApplicationGroup record);

    int updateById(ApplicationGroup record);

	void startOrDown(Map<String, Object> param);
}