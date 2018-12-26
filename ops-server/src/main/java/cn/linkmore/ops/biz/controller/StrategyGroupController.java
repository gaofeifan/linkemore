package cn.linkmore.ops.biz.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.stream.FileImageInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JavaType;

import cn.linkmore.bean.exception.DataException;
import cn.linkmore.bean.view.Tree;
import cn.linkmore.bean.view.ViewMsg;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.response.ResEnterprise;
import cn.linkmore.ops.biz.service.EnterpriseService;
import cn.linkmore.ops.biz.service.PrefectureService;
import cn.linkmore.ops.biz.service.StrategyGroupService;
import cn.linkmore.ops.utils.QrCodeGenerateUtil;
import cn.linkmore.prefecture.request.ReqStrategyGroup;
import cn.linkmore.prefecture.request.ReqStrategyGroupDetail;
import cn.linkmore.prefecture.response.ResPrefectureDetail;
import cn.linkmore.prefecture.response.ResStall;
import cn.linkmore.prefecture.response.ResStrategyGroup;
import cn.linkmore.prefecture.response.ResStrategyGroupArea;


/**
 * Controller - 分组策略
 * 
 * @author lilinhai
 * @version 1.0
 *
 */

@RestController
@RequestMapping("/admin/biz/strategy/group")

public class StrategyGroupController extends BaseController{
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private StrategyGroupService strategyGroupService;
	
	@Autowired
	private EnterpriseService enterService;
	
	@Autowired
	private PrefectureService preService;
    
	/**
	 * 新增
	 * @param reqStrategyGroup
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg save(ReqStrategyGroup reqStrategyGroup) {
		ViewMsg msg = null;
		try {
			reqStrategyGroup.setCreateTime(new Date());
			reqStrategyGroup.setUpdateTime(new Date());
			reqStrategyGroup.setStatus((byte)1);

			reqStrategyGroup.setCreateUserId(getPerson().getId());
			reqStrategyGroup.setCreateUserName(getPerson().getUsername());
			reqStrategyGroup.setUpdateUserId(getPerson().getId());
			reqStrategyGroup.setUpdateUserName(getPerson().getUsername());

			if(StringUtils.isNotEmpty(reqStrategyGroup.getStallGroup())) {
				 JavaType javaType = getCollectionType(ArrayList.class, ReqStrategyGroupDetail.class); 
				 List<ReqStrategyGroupDetail> reqStrategyGroupDetail =  mapper.readValue(reqStrategyGroup.getStallGroup(), javaType);   //这里不需要强制转换
				 reqStrategyGroup.setStrategyGroupDetail(reqStrategyGroupDetail);
			}
			this.strategyGroupService.save(reqStrategyGroup);
			msg = new ViewMsg("保存成功", true);
		} catch (DataException e) {
			msg = new ViewMsg(e.getMessage(), false);
		} catch (Exception e) {
			e.printStackTrace();
			msg = new ViewMsg("保存失败", false);
		}
		return msg;
	}
	public JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {   
		return mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);   
	}	
	/**
	 * 更新
	 * @param reqStrategyBase
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg update(ReqStrategyGroup reqStrategyGroup) {
		ViewMsg msg = null;
		try {
			reqStrategyGroup.setUpdateUserId(getPerson().getId());
			reqStrategyGroup.setUpdateUserName(getPerson().getUsername());			
			reqStrategyGroup.setUpdateTime(new Date());

			this.strategyGroupService.update(reqStrategyGroup);
			msg = new ViewMsg("保存成功", true);
		} catch (DataException e) {
			msg = new ViewMsg(e.getMessage(), false);
		} catch (Exception e) {
			msg = new ViewMsg("保存失败", false);
		}
		return msg;
	}
	
	/**
	 * 更新状态 开启
	 * @param reqStrategyBase
	 * @return
	 */
	@RequestMapping(value = "/status/open", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg statusStart( @RequestBody List<Long> ids) {
		ViewMsg msg = null;
		try {
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("status", 2);
			map.put("updateTime",sdf.format(new Date()) );
			map.put("updateUserId", getPerson().getId());
			map.put("updateUserName", getPerson().getUsername());
			map.put("ids", ids);

			this.strategyGroupService.updateStatus(map);
			msg = new ViewMsg("修改成功", true);
		} catch (DataException e) {
			msg = new ViewMsg(e.getMessage(), false);
		} catch (Exception e) {
			msg = new ViewMsg("修改失败", false);
		}
		return msg;
	}
	/**
	 * 更新状态 关闭
	 * @param reqStrategyBase
	 * @return
	 */
	@RequestMapping(value = "/status/close", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg statusStop(@RequestBody List<Long> ids) {
		ViewMsg msg = null;
		try {
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("status", 1);
			map.put("updateTime", sdf.format(new Date()));
			map.put("updateUserId", getPerson().getId());
			map.put("updateUserName", getPerson().getUsername());
			map.put("ids", ids);
			this.strategyGroupService.updateStatus(map);
			msg = new ViewMsg("修改成功", true);
		} catch (DataException e) {
			msg = new ViewMsg(e.getMessage(), false);
		} catch (Exception e) {
			msg = new ViewMsg("修改失败", false);
		}
		return msg;
	}
	
	
	/**
	 * 删除
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg delete(@RequestBody List<Long> ids) {
		ViewMsg msg = null;
		try {
			this.strategyGroupService.delete(ids);
			msg = new ViewMsg("删除成功", true);
		} catch (DataException e) {
			msg = new ViewMsg(e.getMessage(), false);
		} catch (Exception e) {
			e.printStackTrace();
			msg = new ViewMsg("删除失败", false);
		}
		return msg;
	}

	/**
	 * 删除分组中的车位
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/stall/delete", method = RequestMethod.POST)
	@ResponseBody
	//public ViewMsg deleteStall(@RequestBody Long strategyGroupId,@RequestBody List<Long> ids) {
	public ViewMsg deleteStall(@RequestParam Map<String,Object> map) {
		ViewMsg msg = null;
		try {
			this.strategyGroupService.deleteStall(map);
			msg = new ViewMsg("删除成功", true);
		} catch (DataException e) {
			msg = new ViewMsg(e.getMessage(), false);
		} catch (Exception e) {
			e.printStackTrace();
			msg = new ViewMsg("删除失败", false);
		}
		return msg;
	}
	
	@RequestMapping(value = "/stall/exists", method = RequestMethod.POST)
	@ResponseBody
	public Long existsStall(@RequestParam Map<String,Object> map) {
		return this.strategyGroupService.existsStall(map);
	}

	/**
	 * 添加一个车位
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/stall/add", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg addStall(ReqStrategyGroupDetail reqStrategyGroupDetail) {
		ViewMsg msg = null;
		try {
			this.strategyGroupService.addStall(reqStrategyGroupDetail);
			msg = new ViewMsg("添加成功", true);
		} catch (DataException e) {
			msg = new ViewMsg(e.getMessage(), false);
		} catch (Exception e) {
			e.printStackTrace();
			msg = new ViewMsg("添加失败", false);
		}
		return msg;
	}

	/**
	 * 列表-分页
	 * @param pageable
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(ViewPageable pageable) {

		Map<String, Object> map=new HashMap<String, Object>();
		map.put("property", "id");
		map.put("value", getPerson().getId());
		ResEnterprise enter=enterService.find(map);
		if(enter != null) {
			pageable.setFilterJson(addJSONFilter(pageable.getFilterJson(),"createUserId",getPerson().getId()));
		}
		return this.strategyGroupService.findPage(pageable);
	}

	/*
	 * 信息列表-无分页
	 */
	@RequestMapping(value = "/find_list", method = RequestMethod.POST)
	@ResponseBody
	public List<ResStrategyGroup> findList(@RequestParam Map<String, Object> map) {
		map.put("property", "id");
		map.put("value", getPerson().getId());
		ResEnterprise enter=enterService.find(map);
		if(enter != null) {
			map.put("createUserId",getPerson().getId());
		}
		
		return this.strategyGroupService.findList(map);
	}
	
	/**
	 * 根据id获取一条记录
	 * @param pageable
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.POST)
	@ResponseBody
	public ResStrategyGroup getByPrimarkey(@RequestBody Long id) {
		return this.strategyGroupService.selectByPrimaryKey(id);
	}
	
	/**
	 * 获取分区信息，以及分区下的车位信息
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/area/list", method = RequestMethod.POST)
	@ResponseBody
	public List<ResStrategyGroupArea> selectStallByPrimaryKey(@RequestBody Long id) {
		return this.strategyGroupService.selectStallByPrimaryKey(id);
	}
	
	/**
	 * 获取分组树
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "/tree", method = RequestMethod.POST)
	@ResponseBody
	//public Tree findTree(@RequestParam("preId") Integer preId, @RequestParam("parkingInterval") Integer parkingInterval) {
	public Tree findTree(@RequestParam Map<String, Object> param) {	
		//Map<String, Object> param=new HashMap<String, Object>();
		//param.put("preId", preId);
		//param.put("parkingInterval", parkingInterval);
		param.put("createUserId", getPerson().getId());
		
		return this.strategyGroupService.findTree(param);
	}
	
	/**
	 * 根据preId,areaId,startName,endName 获取车区信息
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "/findAreaStall", method = RequestMethod.POST)
	@ResponseBody
	public List<ResStall> findAreaStall(@RequestParam Map<String, Object> param) {
		param.put("createUserId", getPerson().getId());
		return this.strategyGroupService.findAreaStall(param);
	}
	
	/**
	 * 下载二维码
	 */
	@RequestMapping(value = "/download", method = RequestMethod.POST)
	public void download(Long id,  HttpServletRequest request, HttpServletResponse response) {
		FileImageInputStream bis = null;
		BufferedOutputStream bos = null;
		ServletOutputStream out = null;
		try {
			ResStrategyGroup group = this.strategyGroupService.selectByPrimaryKey(id);
			ResPrefectureDetail pre = preService.findById(group.getPrefectureId());
			String content = request.getHeader("Origin") + "/mini?cityId="+pre.getCityId().toString() + "&prefectureId=" + pre.getId() + "&groupId=" + id.toString();
			log.info("down ...........{}",content);
			//String rootPathText = "/data/qrc"; //服务器路径
			String rootPathText = "C:\\test\\"; // 本机测试路径
			String realPath = rootPathText + File.separatorChar;// 临时文件夹
			// 创建文件路径保证互不影响
			File file = new File(realPath);
			if (!file.exists()) {
				file.mkdir();
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			String dateNowStr = sdf.format(new Date());
			String filePath = realPath + dateNowStr + ".png";
			try {
				QrCodeGenerateUtil.createZxing(filePath, content, 900, "png");
			} catch (Exception e) {
				e.printStackTrace();
			}
			String fileName = URLEncoder.encode(group.getName() + dateNowStr + ".png", "UTF-8");
			response.setContentType("multipart/form-data");// 指明response的返回对象是文件流
			response.setHeader("Content-Disposition", "attachment;filename=" + fileName);// 设置在下载框默认显示的文件名
			response.setCharacterEncoding("UTF-8");
			out = response.getOutputStream();
			bos = new BufferedOutputStream(out);
			bis = new FileImageInputStream(new File(filePath));
			byte[] buffer = new byte[1];
			while (bis.read(buffer) != -1) {
				bos.write(buffer);
			}
			bos.flush();
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bis != null) {
				try {
					bis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
				}
			}
			if (bos != null) {
				try {
					bos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
}
