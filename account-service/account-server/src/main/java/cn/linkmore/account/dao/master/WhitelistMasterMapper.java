package cn.linkmore.account.dao.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.account.entity.Whitelist;
import cn.linkmore.account.request.ReqWhitelist;
/**权限模块 - 类记录-- 写mapper
 * @author   GFF
 * @Date     2018年6月22日
 * @Version  v2.0
 */
@Mapper
public interface WhitelistMasterMapper {
    /**
     * @Description  批量删除
     * @Author   GFF 
     * @Version  v2.0
     */
    int deleteIds(List<Long> id);

    /**
     * @Description  新增
     * @Author   GFF 
     * @Version  v2.0
     */
    int saveReq(ReqWhitelist record);

    /**
     * @Description  新增 null处理
     * @Author   GFF 
     * @Version  v2.0
     */
    int saveReqSelective(ReqWhitelist record);

    /**
     * @Description  更新null处理
     * @Author   GFF 
     * @Version  v2.0
     */
    int updateReqSelective(ReqWhitelist record);

    /**
     * @Description  更新
     * @Author   GFF 
     * @Version  v2.0
     */
    int updateReq(ReqWhitelist record);
}