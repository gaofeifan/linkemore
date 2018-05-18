package cn.linkmore.user.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import cn.linkmore.user.request.ReqPrefecture;
import cn.linkmore.user.response.ResPrefecture;
import cn.linkmore.user.service.PrefectureService;
/**
 * Service实现 - 车区
 * @author liwenlong
 * @version 2.0
 */
@Service
public class PrefectureServiceImpl implements PrefectureService {

	@Override
	public List<ResPrefecture> list(ReqPrefecture rp, HttpServletRequest request) {
		
		return null;
	}

}
