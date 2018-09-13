package cn.linkmore.prefecture.service;

import java.util.List;
import java.util.Map;
import cn.linkmore.bean.view.Tree;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.prefecture.request.ReqAdminUser;
import cn.linkmore.prefecture.request.ReqCheck;
import cn.linkmore.prefecture.response.ResAdmin;
import cn.linkmore.prefecture.response.ResAdminUser;

/**
 * Service - 线下管理员
 * @author zhoayufei
 */
public interface AdminUserService {
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	int delete(List<Long> ids);
	/**
	 * 保存
	 * @param admin
	 * @return
	 */
	int save(ReqAdminUser admin);
	/**
	 * 更新
	 * @param admin
	 * @return
	 */
	int update(ReqAdminUser admin);
	/**
	 * 查看详情
	 * @param id
	 * @return
	 */
	ResAdminUser find(Long id);
	/**
	 * 分页
	 * @param pageable
	 * @return
	 */
	ViewPage findPage(ViewPageable pageable);
	/**
	 * 检查
	 * @param reqCheck
	 * @return
	 */
	Integer check(ReqCheck reqCheck);
	/**
	 * 车区树
	 * @return
	 */
	Tree findTree();
	/**
	 * 授权绑定
	 * @param id
	 * @param authids
	 */
	void bind(Long id, String authids);
	/**
	 * 资源回显
	 * @param id
	 * @return
	 */
	Map<String, Object> resource(Long id);
	/**
	 * 查询所有
	 * @return
	 */
	List<ResAdminUser> findAll();
	
	/**
	 * @Description  根据手机号查询
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	ResAdminUser findMobile(String mobile);
	
	
	/**
	 * @Description  
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void updateLoginTime(Long id);
	
	/**
	 * @Description  
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	ResAdmin authLogin(String mobile);

}
