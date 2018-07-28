package cn.linkmore.ops.ent.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import cn.linkmore.bean.view.Tree;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.request.ReqOperateAuth;
import cn.linkmore.enterprise.request.ReqOperateBind;
import cn.linkmore.ops.ent.request.ReqBindStaffAuth;

/**
 * 操作权限
 * @author   GFF
 * @Date     2018年7月27日
 * @Version  v2.0
 */
public interface OperateService {

	/**
	 * @Description  分页查询
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	ViewPage findPage(ViewPageable pageable);

	/**
	 * @Description  树数据
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<Tree> tree(HttpServletRequest request);

	/**
	 * @Description  绑定
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void bind(ReqOperateBind staffAuth);

	/**
	 * @Description  获取资源
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	Map<String, Object> resource(Long id);

	/**
	 * @Description  新增
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void save(ReqOperateAuth auth);

	/**
	 * @Description  更新
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void update(ReqOperateAuth auth);

	/**
	 * @Description  删除
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void delete(Long id);

}
