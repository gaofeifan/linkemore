package cn.linkmore.prefecture.dao.master;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.prefecture.entity.AdminUserPack;

/**
 * @author   GFF
 * @Date     2018年9月17日
 * @Version  v2.0
 */
@Mapper
public interface AdminUserPackMasterMapper {

	  /**
	 * @Description  
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	AdminUserPack findById(Long id);
}
