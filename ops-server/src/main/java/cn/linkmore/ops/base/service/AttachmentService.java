package cn.linkmore.ops.base.service;

import org.springframework.web.multipart.MultipartFile;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.common.request.ReqAttachment;
import cn.linkmore.common.response.ResAttachment;
import cn.linkmore.common.response.ResBaseAttachment;

/**
 * 文件上传
 * @author   GFF
 * @Date     2018年6月25日
 * @Version  v2.0
 */
public interface AttachmentService {

	/**
	 * @Description  根据id删除 
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
	 * @Description  根据id查询
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	ResBaseAttachment find(Long id);

	/**
	 * @param resOssConfig 
	 * @Description  保存图片
	 * @Author   GFF 
	 * @Version  v2.0
	 */	
	/*ReqAttachment saveImage(MultipartFile file, ResOssConfig resOssConfig);
*/
	/**
	 * @Description  保存文件
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	/*ReqAttachment saveFile(MultipartFile file, ResOssConfig resOssConfig);*/

}
