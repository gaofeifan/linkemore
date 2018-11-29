package cn.linkmore.enterprise.dao.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.enterprise.entity.RentEntUser;
/**
 * 企业长租用户
 * @author   GFF
 * @Date     2018年11月26日
 * @Version  v2.0
 */
@Mapper
public interface RentEntUserMasterMapper {
    /**
     * @Description  s删除
     * @Author   GFF 
     * @Version  v2.0
     */
    int deleteByPrimaryKey(Long rentEntId);

    int insert(RentEntUser record);

    int insertSelective(RentEntUser record);

    int updateByPrimaryKeySelective(RentEntUser record);

    int updateByPrimaryKey(RentEntUser record);

	void deleteIds(List<Long> ids);
}