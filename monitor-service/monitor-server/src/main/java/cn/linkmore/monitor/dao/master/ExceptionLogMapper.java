package cn.linkmore.monitor.dao.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.monitor.entity.ExceptionLog;
import cn.linkmore.monitor.request.ReqExceptionLog;
/**
 * @author   GFF
 * @Date     2018年7月4日
 * @Version  v2.0
 */
@Mapper
public interface ExceptionLogMapper {
	
    /**
     * @Description	刪除  
     * @Author   GFF 
     * @Version  v2.0
     */
    int deleteById(Long id);

    /**
     * @Description  新增
     * @Author   GFF 
     * @Version  v2.0
     */
    int save(ExceptionLog record);

    /**
     * @Description  新增null处理
     * @Author   GFF 
     * @Version  v2.0
     */
    int saveSelective(ExceptionLog record);
   
    /**
     * @Description  更新null处理
     * @Author   GFF 
     * @Version  v2.0
     */
    ExceptionLog findById(Long id);

    /**
     * @Description  更新
     * @Author   GFF 
     * @Version  v2.0
     */
    int updateByIdSelective(ReqExceptionLog record);

    int updateById(ReqExceptionLog record);

	List<ExceptionLog> findAll();
}