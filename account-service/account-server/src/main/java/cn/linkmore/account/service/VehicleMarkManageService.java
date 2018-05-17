package cn.linkmore.account.service;

import java.util.List;

import cn.linkmore.account.entity.VehicleMarkManage;
import cn.linkmore.account.request.ReqVehicleMark;
import cn.linkmore.account.response.ResVechicleMark;

public interface VehicleMarkManageService {

	List<VehicleMarkManage> selectByUserId(Long id);

	void save(ReqVehicleMark bean);

	void deleteById(Long id);

	List<ResVechicleMark> selectResList(Long userId);

}
