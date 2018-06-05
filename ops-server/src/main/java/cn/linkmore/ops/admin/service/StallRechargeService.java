package cn.linkmore.ops.admin.service;

import java.util.List;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.prefecture.request.ReqStallRechargeExcel;
import cn.linkmore.prefecture.response.ResStallRecharge;

/**
 * Service - 电池更换记录
 * @author zhoayufei
 *
 */
public interface StallRechargeService {

	ViewPage findPage(ViewPageable pageable);

	List<ResStallRecharge> exportList(ReqStallRechargeExcel bean);

}
