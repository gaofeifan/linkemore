package cn.linkmore.account.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.linkmore.account.dao.cluster.VehicleMarkManageClusterMapper;
import cn.linkmore.account.dao.master.VehicleMarkManageMasterMapper;
import cn.linkmore.account.entity.Common;
import cn.linkmore.account.entity.CreateCriteria;
import cn.linkmore.account.entity.User;
import cn.linkmore.account.entity.VehicleMarkManage;
import cn.linkmore.account.request.ReqVehicleMark;
import cn.linkmore.account.response.ResVechicleMark;
import cn.linkmore.account.service.UserService;
import cn.linkmore.account.service.VehicleMarkManageService;
import cn.linkmore.bean.exception.BusinessException;
import cn.linkmore.util.ObjectUtils;
@Service
public class VehicleMarkManageServiceImpl implements VehicleMarkManageService {

	@Resource
	private VehicleMarkManageClusterMapper vehicleMarkManageClusterMapper;
	@Resource
	private VehicleMarkManageMasterMapper vehicleMarkManageMasterMapper;
	@Resource
	private UserService userService;
	
	@Override
	public List<VehicleMarkManage> selectByUserId(Long userId) {
		return this.vehicleMarkManageClusterMapper.selectByUserId(userId);
	}

	@Override
	public void save(ReqVehicleMark bean, Long userId) {
		User user = this.userService.getUserCacheKey(userId);
		List<VehicleMarkManage> list = this.selectByUserId(user.getId());
		if(list.size() < 3){
			//检查车牌号是否已经存在
			List<String> fieldVlaue = ObjectUtils.findFieldVlaue(list, "vehMark", new String[]{"vehMark"}, new String[] {bean.getVehMark()});
			if(fieldVlaue.size() > 0){
				throw new BusinessException();
			}else{
				VehicleMarkManage manage = new VehicleMarkManage();
				manage.setVehUserId(user.getId().toString());
				manage.setVehMark(bean.getVehMark());
				manage.setCreateTime(new Date());
				manage.setUpdateTime(new Date());
				vehicleMarkManageMasterMapper.insertSelective(manage);
				return;
			}
		}
		throw new BusinessException();
	}

	@Override
	public void deleteById(Long id, Long userId) {
		List<VehicleMarkManage> list = this.selectByUserId(userId);
		if(list !=null && list.size() != 0) {
			List<String> vlaue = ObjectUtils.findFieldVlaue(list, "id", new String[] {"id"}, new Object[] {userId});
			if(vlaue.size() > 0) {
				this.vehicleMarkManageMasterMapper.deleteById(id);
				return ;
			}
		}
		throw new BusinessException();
	}

	@Override
	public List<ResVechicleMark> selectResList(Long userId) {
		return this.vehicleMarkManageClusterMapper.selectResList(userId);
	}

	
}
