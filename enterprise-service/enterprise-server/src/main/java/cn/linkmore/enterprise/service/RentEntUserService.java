package cn.linkmore.enterprise.service;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.entity.RentEntUser;
import cn.linkmore.enterprise.request.ReqRentEntUser;
import cn.linkmore.enterprise.request.ReqRentUser;
/**
 * 企业长租用户
 * @author   GFF
 * @Date     2018年11月26日
 * @Version  v2.0
 */
public interface RentEntUserService {

    RentEntUser selectByPrimaryKey(Long rentEntId);

	void save(ReqRentEntUser user);

	void update(ReqRentEntUser user);

	void deleteIds(List<Long> ids);

	ViewPage findPage(ViewPageable pageable);

	void saveI(ReqRentUser ent);

	void updateI(ReqRentUser ent);

	void deleteIdsI(List<Long> ids);

	ViewPage findPageI(ViewPageable pageable);

}