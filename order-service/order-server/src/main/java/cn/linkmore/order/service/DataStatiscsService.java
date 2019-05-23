package cn.linkmore.order.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import cn.linkmore.order.controller.staff.ReqPreReportForms;
import cn.linkmore.order.controller.staff.request.ReqPreDetails;
import cn.linkmore.order.controller.staff.response.ResPreDetails;
import cn.linkmore.order.controller.staff.response.ResPreList;
import cn.linkmore.order.controller.staff.response.ResPreListAndDetails;
import cn.linkmore.order.controller.staff.response.ResPreReportForms;

/**
 * @author   GFF
 * @Date     2019年5月14日
 * @Version  v2.0
 */
public interface DataStatiscsService {

	ResPreListAndDetails findPreList(HttpServletRequest request, Long cityId);

	ResPreDetails findPreDetails(HttpServletRequest request, ReqPreDetails details);

	ResPreReportForms findPreReportForms(HttpServletRequest request, ReqPreReportForms reportForms);

}
