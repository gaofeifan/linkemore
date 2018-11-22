package cn.linkmore.prefecture.service;

import javax.servlet.http.HttpServletRequest;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.prefecture.entity.BluetoothData;

/**
 * Service接口 - 蓝牙上报数据信息
 * @author jiaohanbin
 * @version 1.0
 *
 */
public interface BluetoothDataService {
	/**
	 * 分页查询数据
	 * @param pageable 分页查询条件
	 * @return 查询结果集合
	 */
	ViewPage findPage(ViewPageable pageable);
	
	/**
	 * 保存
	 * @param admin
	 * @return
	 */
	int save(BluetoothData blue);

	Boolean saveData(String param, HttpServletRequest request);
	
}
