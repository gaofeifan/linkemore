package cn.linkmore.enterprise.dao.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.enterprise.entity.EntRentUser;
import cn.linkmore.enterprise.request.ReqRentUser;
/**
 * 长租用户---写
 * @author   GFF
 * @Date     2018年7月18日
 * @Version  v2.0
 */
@Mapper
public interface EntRentUserMasterMapper {
    int deleteById(Long id);

    int save(EntRentUser record);

    int saveSelective(EntRentUser record);

    EntRentUser findById(Long id);

    int updateByIdSelective(EntRentUser record);

    int updateById(EntRentUser record);

	void delete(List<Long> ids);

	/**
	 * @Description  新增req
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void saveReq(ReqRentUser user);

	/**
	 * @Description  更新
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void updateReq(ReqRentUser user);

	void saveBatch(List<EntRentUser> rus);

	void deleteBatch(List<Long> ids);

}