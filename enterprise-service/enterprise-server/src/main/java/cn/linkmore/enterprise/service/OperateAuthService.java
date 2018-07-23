package cn.linkmore.enterprise.service;

import java.util.List;
import java.util.Map;

import cn.linkmore.bean.view.Tree;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.entity.EntOperateAuth;
import cn.linkmore.enterprise.request.ReqOperateAuth;
import cn.linkmore.enterprise.request.ReqOperateBind;

/**
 * 操作权限
 * @author   GFF
 * @Date     2018年7月20日
 * @Version  v2.0
 */
public interface OperateAuthService {

	/**
	 * @Description  查询树桩数据
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<Tree> tree();

	/**
	 * @Description  新增
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void save(ReqOperateAuth operateAuth);

	/**
	 * @Description  更新
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void update(ReqOperateAuth operateAuth);

	/**
	 * @Description  禁用
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void stop(Long id);

	/**
	 * @Description  启动
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void start(Long id);

	/**
	 * @Description  删除
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void delete(Long id);

	/**
	 * @Description  分页查询
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	ViewPage findPage(ViewPageable pageable);

	/**
	 * @Description  绑定
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void bind(ReqOperateBind bind);

	/**
	 * @Description  查询资源
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	Map<String, Object> resource(Long id);


}
