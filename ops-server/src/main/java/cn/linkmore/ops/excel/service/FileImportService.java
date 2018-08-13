package cn.linkmore.ops.excel.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import cn.linkmore.account.client.VehicleMarkClient;
import cn.linkmore.common.client.BaseDictClient;
import cn.linkmore.ops.biz.service.CustomerInfoService;
import cn.linkmore.ops.biz.service.EnterpriseService;
import cn.linkmore.ops.biz.service.StallService;
import cn.linkmore.ops.biz.service.UserService;
import cn.linkmore.ops.ent.service.PrefectrueService;
import cn.linkmore.ops.excel.entity.factory.RentUserFactory;
import cn.linkmore.prefecture.client.AdminUserClient;

@Service
public class FileImportService {

	private static Map<String, Integer> syn = new HashMap<String, Integer>();

	private String FILENAME;

	/**
	 * 车位锁
	 */
	@Resource
	CustomerInfoService customerInfoService;
	@Resource
	private BaseDictClient baseDictClient;
	@Resource
	private AdminUserClient adminUserClient;
	@Resource
	private VehicleMarkClient vehicleMarkClient;
	
	
	@Resource
	private EnterpriseService enterpriseService;
	@Resource
	private PrefectrueService prefectrueService;
	@Resource
	private StallService stallService;
	@Resource
	private UserService userService;
	
	public boolean ready(String fileName) {
		boolean b = true;
		FILENAME = fileName;
		synchronized (syn) {
			if (syn.containsKey(FILENAME)) { // 如果存在，说明该文件正在解析
				b = false;
			}
		}
		return b;
	}

	public void open() {
		synchronized (syn) {
			syn.put(FILENAME, 1);
		}
	}

	public void close() {
		synchronized (syn) {
			syn.remove(FILENAME);
		}
	}

	// 用户信息录入导入
	public String invoke4(String fileName, MultipartFile file) {
		String result = null;
		open();
		try {
			if ("customerInfoImport".equals(fileName)) {
				result = this.importCustomerInfo(file);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		close();
		return result;
	}

	/**
	 * 用户信息录入导入
	 */
	private String importCustomerInfo(MultipartFile file) {
		// CustomerInfoFactory factory = new
		// CustomerInfoFactory(customerInfoService,baseDictClient,adminUserClient,vehicleMarkClient);
		// factory.execute(file);
		// return factory.result();
		return null;
	}

	public String invoke5(String fileName2, MultipartFile file) {
		RentUserFactory factory = new RentUserFactory(enterpriseService, prefectrueService, stallService, userService);
		factory.execute(file);
		return factory.result();
	}
	
	

}
