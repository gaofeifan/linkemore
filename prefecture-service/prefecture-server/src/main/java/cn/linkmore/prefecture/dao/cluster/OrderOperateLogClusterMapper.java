package cn.linkmore.prefecture.dao.cluster;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.prefecture.request.ReqOrderOperateLogExcel;
import cn.linkmore.prefecture.response.ResOrderOperateLog;
import cn.linkmore.prefecture.response.ResOrderOperateLogEntity;

@Mapper
public interface OrderOperateLogClusterMapper {
	List<ResOrderOperateLogEntity> findPage(Map<String, Object> param);

	Integer count(Map<String, Object> param);

	ResOrderOperateLog findById(Long id);

	List<ResOrderOperateLogEntity> findExcelList(ReqOrderOperateLogExcel excelBean);
}