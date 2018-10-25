package cn.linkmore.account.dao.master;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.account.request.ReqMessage;
/**
 * Mapper - 
 * @author GFF
 * @version 2.0
 *
 */
@Mapper
public interface MessageMasterMapper { 
	
    int save(ReqMessage record); 

    int update(ReqMessage record);

}