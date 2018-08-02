package cn.linkmore.ops.account.controller;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSONArray;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.ops.account.service.OrdersService;
import cn.linkmore.ops.biz.service.PrefectureService;
import cn.linkmore.ops.biz.service.StallService;
import cn.linkmore.ops.request.ReqOrderExcel;
import cn.linkmore.order.response.ResOrderExcel;
import cn.linkmore.prefecture.response.ResPrefectureDetail;
import cn.linkmore.prefecture.response.ResStallOps;
import cn.linkmore.util.ExcelUtil;
/**
 * 订单信息
 * @author   GFF
 * @Date     2018年6月25日
 * @Version  v2.0
 */
@Controller
@RequestMapping("/admin/account/order")
public class OrderController {
	@Resource
	private OrdersService ordersService;
	
	@Resource
	private PrefectureService prefectureService;
	
	@Resource
	private StallService stallService;
	
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(HttpServletRequest request,ViewPageable pageable){
		return this.ordersService.findPage(pageable); 
	} 
	@RequestMapping(value = "/prefecture_list", method = RequestMethod.POST)
	@ResponseBody
	public List<ResPrefectureDetail> prefectureList(HttpServletRequest request){
		return this.prefectureService.findList(new HashMap<String,Object>());
	} 
	 
	@RequestMapping(value = "/stall_list", method = RequestMethod.POST)
	@ResponseBody
	public List<ResStallOps> stallList(HttpServletRequest request,Long pid){
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("preId", pid);
		param.put("property", "stall_name");
		param.put("direction", "asc");
		return this.stallService.findList(param);
	}
	
	@RequestMapping(value = "/export", method = RequestMethod.POST)
	public void export(ReqOrderExcel bean,HttpServletResponse response){ 
		List<ResOrderExcel> list = this.ordersService.exportList(bean);
		InputStream is = null;
		ByteArrayOutputStream baos = null;
		ServletOutputStream sos  = null;
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try { 
	        JSONArray ja = new JSONArray();
	        Map<String,Object> s = null; 
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	        for(ResOrderExcel rrb:list){
	            s = new HashMap<String,Object>();
	            s.put("orderNo", rrb.getOrderNo());
		        s.put("username",rrb.getUsername());
		        s.put("prefectureName", rrb.getPrefectureName());
		        s.put("stallName", rrb.getStallName());
		        s.put("plateNo",rrb.getPlateNo());
		        s.put("status",this.parseStatus(rrb.getStatus())); 
		        s.put("totalAmount", rrb.getTotalAmount());
		        s.put("actualAmount", rrb.getActualAmount()); 
	            s.put("createTime", sdf.format(rrb.getCreateTime()));
	            ja.add(s);
	        } 
	        Map<String,String> headMap = new LinkedHashMap<String,String>();
	        headMap.put("orderNo", "编号");
	        headMap.put("username","用户");
	        headMap.put("prefectureName", "专区");
	        headMap.put("stallName", "车位");
	        headMap.put("plateNo","车牌号");
	        headMap.put("status", "状态"); 
	        headMap.put("totalAmount", "订单金额");
	        headMap.put("actualAmount", "实际金额");
	        headMap.put("createTime", "预约时间");
            baos = new ByteArrayOutputStream();
            String title = "预约订单信息";
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
	private String parseStatus(Integer status){
		String text = null;
		switch(status){
			case 1:text = "待支付";break;
			case 2:text = "已支付";break;
			case 3:text = "已完成";break;
			case 4:text = "已取消";break;
			case 5:text = "已退款";break;
			case 6:text = "已挂起";break;
			case 7:text = "已关闭";break;
		}
		return text;
	}
}
