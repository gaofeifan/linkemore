package cn.linkmore.account.dao.master;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.account.entity.Receive;
import cn.linkmore.account.response.ResReceive;

/**
 * 领券记录(写)
 * @author   GFF
 * @Date     2018年5月30日
 * @Version  v2.0
 */
@Mapper
public interface ReceiveMasterMapper {
    
	/**
	 * @Description  根据id删除
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	int deleteById(Long id);

    /**
     * @Description  新增
     * @Author   GFF 
     * @Version  v2.0
     */
    int save(Receive record);

    /**
     * @Description  新增null处理
     * @Author   GFF 
     * @Version  v2.0
     */
    int saveSelective(Receive record);

    /**
     * @Description  更新null处理
     * @Author   GFF 
     * @Version  v2.0
     */
    int updateByIdSelective(Receive record);
    
    /**
     * @Description  更新
     * @Author   GFF 
     * @Version  v2.0
     */
    int updateById(Receive record);



}