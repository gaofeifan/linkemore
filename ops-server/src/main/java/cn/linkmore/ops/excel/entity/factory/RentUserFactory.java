package cn.linkmore.ops.excel.entity.factory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.apache.commons.lang3.StringUtils;

import cn.linkmore.account.response.ResUser;
import cn.linkmore.enterprise.request.ReqRentUser;
import cn.linkmore.enterprise.response.ResEntPrefecture;
import cn.linkmore.enterprise.response.ResEnterprise;
import cn.linkmore.ops.biz.service.EnterpriseService;
import cn.linkmore.ops.biz.service.StallService;
import cn.linkmore.ops.biz.service.UserService;
import cn.linkmore.ops.ent.service.PrefectrueService;
import cn.linkmore.ops.excel.ReqRentUserImport;
import cn.linkmore.ops.excel.entity.ExcelReader;
import cn.linkmore.prefecture.response.ResStallOps;
import cn.linkmore.util.StringUtil;

public class RentUserFactory extends ExcelReader<ReqRentUserImport>{

	private EnterpriseService enterpriseService;
	private PrefectrueService prefectrueService;
	private StallService stallService;
	private UserService userService;
	
	private List<ResEnterprise> ents;
	private List<ResEntPrefecture> entPres;
	private List<ResStallOps> stalls;
	private List<ReqRentUserImport> rentUsers;
	private ReqRentUser rentUser = null;
	
    private int succe = 0;
    private int err = 0;
    private int errFrom = 0;
    private int isNullPhone = 0;
    private String message ="";
	@Override
	public void del() {
		
	}

	@Override
	public void putValue(int key, Object value, ReqRentUserImport obj) {
		ents = this.enterpriseService.selectAll();
		entPres = this.prefectrueService.findAll(null);
		stalls = this.stallService.findList(new HashMap<>());
//		List<ResUser> users = this.userService.findAll();
		switch (key) {
		case 0:
			for (ResEnterprise ent : ents) {
				if(ent.getName().equals(value.toString().trim())) {
					obj.setEntName(ent.getName());
					break;
				}
			}
			break;
		case 1:
			for (ResEntPrefecture entPre : entPres) {
				if(entPre.getEntName().equals(value.toString().trim())) {
					if(obj.getEntId() != null && obj.getEntId().equals(entPre.getEntId())) {
						obj.setEntPreName(entPre.getEntName());
						obj.setEntPreId(entPre.getId());
						obj.setPreId(entPre.getPreId());
						break;
					}
				}
			}
			break;
		case 2:
			for (ResStallOps stall : stalls) {
				if(stall.getStallName().equals(value.toString().trim())) {
					if(obj.getEntId() != null && obj.getPreId() != null &&
						obj.getPreId().equals(stall.getPreId())) {
						obj.setStallId(stall.getId());
						obj.setStallName(stall.getStallName());
						break;
					}
				}
			}
			break;
			
		case 3:
			if(StringUtil.isNotBlank(obj.toString())) {
				obj.setRealname(obj.toString());
				break;
			}
		case 4:
			if(StringUtil.isNotBlank(obj.toString())) {
				String mobile = objectToString(value);
				if(isMobileNO(mobile)){
					obj.setMobile(mobile);
					break;
				}
			}else{
        		errFrom++;
        	}
        	isNullPhone++;
        	break;
		case 5:
			if(StringUtil.isNotBlank(obj.toString())) {
				obj.setPlateNo(obj.toString());
				break;
			}
		default:
			break;
		}
	}

	@Override
	public void autoSave(List<ReqRentUserImport> list) {
		List<ResUser> users = this.userService.findAll();
		Map<String, Object> userMap = new HashMap<>();
//		List<>
		for (ReqRentUserImport reqRentUserImport : list) {
			for (ResUser resUser : users) {
				if(reqRentUserImport.getMobile().equals(resUser.getUsername())) {
					rentUser = new ReqRentUser();
		    		rentUser.setEntId(reqRentUserImport.getEntId());
		    		rentUser.setEntName(reqRentUserImport.getEntName());
		    		rentUser.setPreId(reqRentUserImport.getPreId());
		    		rentUser.setPreName(reqRentUserImport.getEntPreName());
		    		rentUser.setEntPreId(reqRentUserImport.getEntPreId());
		    		rentUser.setStallId(reqRentUserImport.getStallId());
		    		rentUser.setStallName(reqRentUserImport.getStallName());
		    		rentUser.setMobile(reqRentUserImport.getMobile());
		    		rentUser.setRealname(reqRentUserImport.getRealname());
		    		rentUser.setPlate(reqRentUserImport.getPlateNo());
		    		rentUser.setUserId(resUser.getId());
				}
			}
		}
		
//		this.re
	}

	/**
     * 部分单元格，存入数字类型的字符串，会当作数字解析，导致在数据后面追加了.00
     */
    private String objectToString(Object value) {
        return value.toString().trim().replaceAll("\\.00$", "");
    }
    
	public RentUserFactory(EnterpriseService enterpriseService, PrefectrueService prefectrueService,
			StallService stallService, UserService userService) {
		rentUsers = new ArrayList<ReqRentUserImport>();
		this.enterpriseService = enterpriseService;
		this.prefectrueService = prefectrueService;
		this.stallService = stallService;
		this.userService = userService;
	}
	
	  /**
     * 每次获取到一个对象之后，执行的动作，默认保存到 list 之中
     * 子类可以重写此方法
     */
    @Override
    protected boolean afterGetObject(ReqRentUserImport obj) {
    	if(StringUtil.isNotBlank(obj)){
    		rentUsers.add(obj);
    		succe ++;
    	}
    	return true;
    }
	
    /**
     * 验证手机号格式  需要
     */
    public static boolean isMobileNO(String mobiles) {
        if (!StringUtils.isNotEmpty(mobiles)){
            return false;
        }
        return isChinaPhoneLegal(mobiles) || isHKPhoneLegal(mobiles);
    }

	/**
     * 大陆手机号码11位数，匹配格式：前三位固定格式+后8位任意数
     * 此方法中前三位格式有：
     * 13+任意数
     * 15+除4的任意数
     * 18+除1和4的任意数
     * 17+除9的任意数
     * 147
     */
    public static boolean isChinaPhoneLegal(String str) throws PatternSyntaxException {
        String regExp = "^((13[0-9])|(15[^4])|(18[0-9])|(17[0-9])|(147))\\d{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }
    /**
     * 香港手机号码8位数，5|6|8|9开头+7位任意数
     */
    public static boolean isHKPhoneLegal(String str)throws PatternSyntaxException {
        String regExp = "^(5|6|8|9)\\d{7}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }
    
    public String result() {
      	if (errFrom > 0 || isNullPhone ==0) {
      		message += "存在手机号格式错误！";
      	}
      	if(succe > 0 && err == 0){
      		return message+="导入成功" + succe + "条数据！";
      	}else{
      		message += "导入的数据全部失败！";
      		return message;
      	}
      }

}
