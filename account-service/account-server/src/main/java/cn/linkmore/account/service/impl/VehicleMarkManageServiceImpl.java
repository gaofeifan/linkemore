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
import cn.linkmore.account.service.CommonService;
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
	private CommonService commonService;
	@Resource
	private UserService userService;
	
	@Override
	public List<VehicleMarkManage> selectByUserId(Long userId) {
		return selectBy("veh_user_id", userId);
	}

	@SuppressWarnings("unchecked")
	private List<VehicleMarkManage> selectBy(String column , Object value){
		Common common = new Common(VehicleMarkManage.class);
		CreateCriteria criteria = common.createCriteria();
		criteria.equals(column , value);
		return (List<VehicleMarkManage>) commonService.selectList(common );
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
		this.vehicleMarkManageMasterMapper.deleteById(id);
	}

	@Override
	public List<ResVechicleMark> selectList(Long userId) {
		User user = this.userService.getUserCacheKey(userId);
		Common common = new Common(ResVechicleMark.class);
		CreateCriteria criteria = common.createCriteria();
		criteria.equals("veh_user_id" , user.getIcon());
		return (List<ResVechicleMark>) commonService.selectList(common );
	}
	
	
	
}
