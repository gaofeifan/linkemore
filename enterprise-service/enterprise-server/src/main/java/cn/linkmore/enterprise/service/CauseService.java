package cn.linkmore.enterprise.service;


import java.util.List;

import cn.linkmore.enterprise.controller.staff.response.CauseReponseBean;
import cn.linkmore.enterprise.controller.staff.response.CustomerResponseBean;

/**
 * @author zhangxurui
 * @version v1.0.0
 * @description - 操作原因备注
 * @date 2017/11/8  上午9:56
 */
public interface CauseService {

    List<CauseReponseBean> findRemark(String code);

    /**
     * 客户信息录入来源
     * @return
     */
	CustomerResponseBean findCustoerData(Long oId);

	List<CauseReponseBean> findCode(String param);

}
