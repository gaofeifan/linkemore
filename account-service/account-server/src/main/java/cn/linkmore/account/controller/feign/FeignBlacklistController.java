package cn.linkmore.account.controller.feign;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.linkmore.account.entity.UserBlacklist;
import cn.linkmore.account.service.BlacklistService;
import cn.linkmore.bean.common.Constants.RedisKey;
import cn.linkmore.bean.exception.DataException;
import cn.linkmore.bean.view.ViewMsg;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.redis.RedisService;

/**
 * Controller - 权限模块 - 类记录
 * @author liwenlong
 * @version 1.0
 *
 */ 
@Controller
@RequestMapping("/feign/blacklist")
public class FeignBlacklistController {
	
	@Autowired
	private BlacklistService blacklistService;
	
	@Autowired
	private RedisService redisService;
	
	
	@RequestMapping(value = "/v2.0/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(ViewPageable pageable){
		return this.blacklistService.findPage(pageable); 
	} 
	
	/*@RequestMapping(value = "/pre_list", method = RequestMethod.POST)
	@ResponseBody
	public List<PrefectureBean> list(){
		return this.blacklistService.findPreList(); 
	}  
	*/
	@RequestMapping(value = "/v2.0/status", method = RequestMethod.PUT)
	@ResponseBody
	public Boolean status(){ 
		Object limit = redisService.get(RedisKey.BLACKLIST_ORDER_LIMIT.key);
		if(limit==null){
			return true;
		} 
		return false;
	}  
	
	@RequestMapping(value = "/v2.0/open", method = RequestMethod.PUT)
	@ResponseBody
	public ViewMsg open() {
		ViewMsg msg = null;
		try {
			redisService.set(RedisKey.BLACKLIST_ORDER_LIMIT.key, true);
			msg = new ViewMsg("开启成功", true);
		} catch (DataException e) {
			msg = new ViewMsg(e.getMessage(), false);
		} catch (Exception e) { 
			msg = new ViewMsg("开启失败", false);
		}
		return msg; 
	}
	@RequestMapping(value = "/v2.0/close", method = RequestMethod.DELETE)
	@ResponseBody
	public ViewMsg close() {
		ViewMsg msg = null;
		try { 
			redisService.set(RedisKey.BLACKLIST_ORDER_LIMIT.key,false);
			msg = new ViewMsg("关闭成功", true);
		} catch (DataException e) {
			msg = new ViewMsg(e.getMessage(), false);
		} catch (Exception e) { 
			msg = new ViewMsg("关闭失败", false);
		}
		return msg;

	}
	
	@RequestMapping(value = "/v2.0/enable", method = RequestMethod.PUT)
	@ResponseBody
	public ViewMsg enable(@RequestBody List<Long> list){ 
		ViewMsg msg = null;
		try {
			this.blacklistService.enable(list);
			msg = new ViewMsg("解锁成功",true);
		}catch(DataException e) {
			msg = new ViewMsg(e.getMessage(),false);
		}catch(Exception e) {
			e.printStackTrace();
			msg = new ViewMsg("解锁失败",false);
		}
		return msg;
	}
	
	private String parsePrefecture(Map<Long,String> map,UserBlacklist bl){
		StringBuffer content = new StringBuffer("");
		String name1 = map.get(bl.getMaxPreId());
		String name2 = map.get(bl.getMinPreId());
		if(StringUtils.isNotBlank(name1)){
			content.append(name1);
		}
		if(StringUtils.isNotBlank(name2)){
			if(!"".equals(content.toString())){
				content.append(",");
			}
			content.append(name2);
		}
		return content.toString();
	}
	/*
	@RequestMapping(value = "/export", method = RequestMethod.POST)
	public void export(HttpServletResponse response){  
		List<PrefectureBean> pres = this.blacklistService.findPreList();
		Map<Long,String> map = new HashMap<Long,String>();
		for(PrefectureBean pb:pres){
			map.put(pb.getId(), pb.getName());
		}
		List<Blacklist> list = this.blacklistService.findList();
		InputStream is = null;
		ByteArrayOutputStream baos = null;
		ServletOutputStream sos  = null;
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try { 
	        JSONArray ja = new JSONArray();
	        Map<String,Object> s = null; 
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	        for(Blacklist bl:list){
	            s = new HashMap<String,Object>();
	            s.put("username", bl.getUsername());
	            s.put("prefecture",this.parsePrefecture(map, bl));
	            s.put("totalOrderCount", bl.getTotalOrderCount());
	            s.put("couponCount", bl.getCouponCount());
	            s.put("couponValidate",sdf.format(bl.getCreateTime()));
	            s.put("cashOrderCount", bl.getCashOrderCount()); 
	            s.put("limitStatus", bl.getLimitStatus()==1?"已禁用":"已解锁");
	            ja.add(s);
	        } 
	        Map<String,String> headMap = new LinkedHashMap<String,String>();
	        headMap.put("username", "编号");
	        headMap.put("prefecture","常规使用地");
	        headMap.put("totalOrderCount", "交易次数");
	        headMap.put("couponCount", "停车券数量");
	        headMap.put("couponValidate","停车券截止日期");
	        headMap.put("cashOrderCount", "现金交易次数"); 
	        headMap.put("limitStatus", "受限状态");
            baos = new ByteArrayOutputStream();
            String title = "预约受限名单"+sdf.format(new Date());
            ExcelUtil.exportExcel(title,headMap,ja,null,0,baos);
            byte[] content = baos.toByteArray();
            is = new ByteArrayInputStream(content); 
            response.reset(); 
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8"); 
            response.setHeader("Content-Disposition", "attachment;filename="+ new String((title + ".xlsx").getBytes(), "iso-8859-1"));
            response.setContentLength(content.length);
            sos = response.getOutputStream();
            bis = new BufferedInputStream(is);
            bos = new BufferedOutputStream(sos);
            byte[] buff = new byte[8192];
            int bytesRead;
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead); 
            } 
            bos.flush(); 
        }catch (Exception e) {
            e.printStackTrace();
        }finally{
        	if( bos!=null){
        		try{
        			bos.close();
        		}catch(Exception e){}
        	}
        	if( sos!=null){
        		try{
        			sos.close();
        		}catch(Exception e){}
        	}
        	if( bis!=null){
        		try{
        			bis.close();
        		}catch(Exception e){}
        	}
        	if(is!=null){
        		try {
					is.close();
				} catch (IOException e) {}
        	}
        	if( baos!=null){
        		try{
        			baos.close();
        		}catch(Exception e){}
        	} 
        }
	}
*/
}
