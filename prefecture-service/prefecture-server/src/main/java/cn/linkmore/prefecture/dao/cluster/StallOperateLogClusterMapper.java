package cn.linkmore.prefecture.dao.cluster;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.prefecture.entity.StallOperateLog;
import cn.linkmore.prefecture.request.ReqStallOperateLogExcel;
import cn.linkmore.prefecture.response.ResStallOperateLog;
/**
 * dao 车位操作日志
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Mapper
public interface StallOperateLogClusterMapper {
    StallOperateLog findById(Long id);

	Integer count(Map<String, Object> param);

	List<ResStallOperateLog> findPage(Map<String, Object> param);

	List<ResStallOperateLog> findExportList(ReqStallOperateLogExcel excelBean);
}