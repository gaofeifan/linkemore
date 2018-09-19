package cn.linkmore.account.service;

import javax.servlet.http.HttpServletRequest;

import cn.linkmore.account.controller.staff.response.ResCenter;

/**
 * @author   GFF
 * @Date     2018年9月17日
 * @Version  v2.0
 */
public interface StaffCenterService {

	ResCenter getCenter(HttpServletRequest request);

}
