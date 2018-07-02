package cn.linkmore.ops.biz.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import cn.linkmore.bean.exception.DataException;
import cn.linkmore.bean.view.ViewMsg;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.ops.biz.service.PrefectureService;
import cn.linkmore.ops.biz.service.StallLockService;
import cn.linkmore.ops.biz.service.StallService;
import cn.linkmore.prefecture.request.ReqCheck;
import cn.linkmore.prefecture.request.ReqStallLock;
import cn.linkmore.prefecture.response.ResPrefectureDetail;
import cn.linkmore.util.ExcelRead;

/**
 * 
 * @Description - 车位锁
 * @Author jiaohanbin
 * @Version v2.0
 */
@Controller
@RequestMapping("/admin/biz/stall_lock")
public class StallLockController {

	@Resource
	private PrefectureService prefectureService;

	@Autowired
	private StallLockService stallLockService;

	@Resource
	private StallService stallService;

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg save(ReqStallLock record) {
		ViewMsg msg = null;
		try {
			this.stallLockService.save(record);
			msg = new ViewMsg("保存成功", true);
		} catch (DataException e) {
			msg = new ViewMsg(e.getMessage(), false);
		} catch (Exception e) {
			e.printStackTrace();
			msg = new ViewMsg("保存失败", false);
		}
		return msg;

	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg update(ReqStallLock record) {
		ViewMsg msg = null;
		try {
			this.stallLockService.update(record);
			msg = new ViewMsg("保存成功", true);
		} catch (DataException e) {
			msg = new ViewMsg(e.getMessage(), false);
		} catch (Exception e) {
			msg = new ViewMsg("保存失败", false);
		}
		return msg;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg delete(Long id) {
		ViewMsg msg = null;
		try {
			this.stallLockService.delete(id);
			msg = new ViewMsg("删除成功", true);
		} catch (DataException e) {
			msg = new ViewMsg(e.getMessage(), false);
		} catch (Exception e) {
			e.printStackTrace();
			msg = new ViewMsg("删除失败", false);
		}
		return msg;
	}

	@RequestMapping(value = "/check", method = RequestMethod.POST)
	@ResponseBody
	public Boolean check(ReqCheck reqCheck) {
		return this.stallLockService.check(reqCheck);
	}

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(HttpServletRequest request, ViewPageable pageable) {
		return this.stallLockService.findPage(pageable);
	}

	@RequestMapping(value = "/import_excel", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg importExcel(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
		ViewMsg msg = new ViewMsg("导入成功", true);
		try {
			ExcelRead er = new ExcelRead();
			List<List<String>> list = er.readExcel(file);
			List<Map<String, Object>> rows = new ArrayList<>();
			Map<String, Object> row = null;
			for (List<String> cell : list) {
				if (cell != null && cell.size() > 0) {
					row = new HashMap<>();
					if (StringUtils.isNotBlank(cell.get(0))) {
						row.put("sn", cell.get(0));
					} else {
						msg = new ViewMsg("导入失败，序列号不能为空", false);
					}
					if (StringUtils.isNotBlank(cell.get(1))) {
						row.put("preName", cell.get(1));
					} else {
						msg = new ViewMsg("导入失败，专区名称不能为空", false);
					}
					if (StringUtils.isNotBlank(cell.get(2))) {
						row.put("stallName", cell.get(2));
					} else {
						msg = new ViewMsg("导入失败，车位名称不能为空", false);
					}
					rows.add(row);
				}
			}
			
			int count = 0;
			ReqCheck reqCheck = null;
			for (Map<String, Object> r : rows) {
				ResPrefectureDetail p = prefectureService.checkName(r.get("preName").toString());
				if (null == p) {
					msg = new ViewMsg("导入失败，专区不存在", false);
				}else {
					reqCheck = new ReqCheck();
					reqCheck.setProperty(r.get("stallName").toString());
					reqCheck.setValue(p.getId().toString());
					reqCheck.setId(null);
					int check = stallService.check(reqCheck);
					int checkSn = stallLockService.checkSn(r.get("sn").toString());
					if (check < 1 && checkSn < 1) {
						this.stallService.saveAndBind(p.getId(), r.get("stallName").toString(), r.get("sn").toString());
					} else {
						count++;
					}
				}
			}
			if (count != 0) {
				msg = new ViewMsg("导入成功,其中有" + count + "条数据已存在", true);
			}
		} catch (DataException e) {
			msg = new ViewMsg(e.getMessage(), false);
		} catch (Exception e) {
			msg = new ViewMsg("导入失败", false);
		}
		return msg;
	}

}
