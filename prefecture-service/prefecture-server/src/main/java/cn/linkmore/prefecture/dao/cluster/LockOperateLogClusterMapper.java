package cn.linkmore.prefecture.dao.cluster;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.prefecture.request.ReqLockOperateLogExcel;
import cn.linkmore.prefecture.response.ResLockOperateLog;
/**
 * dao 锁操作日志
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Mapper
public interface LockOperateLogClusterMapper {
	List<ResLockOperateLog> findPage(Map<String, Object> param);

	List<ResLockOperateLog> findExportList(ReqLockOperateLogExcel bean);

	Integer count(Map<String, Object> param);
}