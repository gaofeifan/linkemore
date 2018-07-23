package cn.linkmore.enterprise.dao.master;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.enterprise.entity.EntOperateAuth;
import cn.linkmore.enterprise.request.ReqOperateAuth;
/**
 * 操作权限--写
 * @author   GFF
 * @Date     2018年7月18日
 * @Version  v2.0
 */
@Mapper
public interface EntOperateAuthMasterMapper {
    int deleteById(Long id);

    int save(EntOperateAuth record);

    int saveSelective(EntOperateAuth record);

    int updateById(EntOperateAuth record);

	void saveReq(ReqOperateAuth operateAuth);

	void updateByIdSelective(ReqOperateAuth operateAuth);

	/**
	 * @Description  更新状态
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void updateStatus(Map<String, Object> map);
}