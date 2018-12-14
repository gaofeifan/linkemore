package cn.linkmore.enterprise.service;

import java.util.List;
import java.util.Map;

import cn.linkmore.bean.view.Tree;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.entity.RentEnt;
import cn.linkmore.enterprise.request.ReqRentEnt;
/**
 * 长租企业
 * @author   GFF
 * @Date     2018年11月26日
 * @Version  v2.0
 */
public interface RentEntService {
    /**
     * @Description  根据主键查询
     * @Author   GFF 
     * @Version  v2.0
     */
    RentEnt findById(Long id);

	void save(ReqRentEnt ent);

	void update(ReqRentEnt ent);

	void delete(List<Long> ids);

	ViewPage findPage(ViewPageable pageable);

	ViewPage stallListCompany(ViewPageable pageable);

	List<Tree> tree(Long entId);

	int updateStatus(Map<String, Object> map);

	void deleteStall(List<Long> ids);

	void saveStall(ReqRentEnt ent);

	void updateOverdueStatus();

}