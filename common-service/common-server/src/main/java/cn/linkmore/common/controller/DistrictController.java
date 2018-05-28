package cn.linkmore.common.controller;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.bean.view.Tree;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.common.entity.District;
import cn.linkmore.common.request.ReqBaseDict;
import cn.linkmore.common.request.ReqCheck;
import cn.linkmore.common.request.ReqDistrict;
import cn.linkmore.common.response.ResBaseDict;
import cn.linkmore.common.response.ResOldDict;
import cn.linkmore.common.service.BaseDictService;
import cn.linkmore.common.service.DictService;
import cn.linkmore.common.service.DistrictService;
import cn.linkmore.util.ObjectUtils;

/**
 * 区域
 * @author   GFF
 * @Date     2018年5月18日
 * @Version  v2.0
 */
@RestController
@RequestMapping(value="/district")
public class DistrictController {
	
	@Resource
	private DistrictService districtService;

	/**
	 * @Description 批量删除 
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	public void delete(List<Long> ids) {
		this.districtService.delete(ids);
	}

	/**
	 * @Description  更新
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	public void update(ReqDistrict district) {
		District object = ObjectUtils.copyObject(district, new District());
		this.districtService.update(object);
	}

	/**
	 * @Description  新增
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	public void save(ReqDistrict district) {
		District object = ObjectUtils.copyObject(district, new District());
		this.districtService.save(object);
	}

	/**
	 * @Description  校验
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	public Boolean check(ReqCheck object) {
		Boolean flag = true ;
		Integer count = this.districtService.check(object.getProperty(), object.getValue(), object.getId());
		if(count > 0){
            flag = false;
        }
        return flag;
	}

	/**
	 * @Description  分页查询
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	public ViewPage findPage(ViewPageable pageable) {
		ViewPage page = this.districtService.findPage(pageable);
		return page;
	}

	/**
	 * @Description 查询树桩结构数据 
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	public Tree findTree() {
		Tree tree= this.districtService.findTree();
		return tree;
		
	}

}
