package cn.linkmore.order.dao.master;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.order.entity.OrdersDetail;
import cn.linkmore.order.response.ResOrderOperateLog;
@Mapper
public interface OrdersDetailMasterMapper {
    int delete(Long id);

    int save(OrdersDetail record);

    int update(OrdersDetail record);
    
    void updateT(Map<String, Object> param);
    
   void  savelog(ResOrderOperateLog resOrderOperateLog);
    
}