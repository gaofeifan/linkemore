package cn.linkmore.account.service;

import java.util.List;

import cn.linkmore.account.entity.VehicleMarkManage;
import cn.linkmore.account.request.ReqVehicleMark;
import cn.linkmore.account.response.ResVechicleMark;

public interface VehicleMarkManageService {

	List<VehicleMarkManage> selectByUserId(Long id);

	void save(ReqVehicleMark bean,Long userId);

	void deleteById(Long id, Long userId);

	List<ResVechicleMark> selectResList(Long userId);

}
