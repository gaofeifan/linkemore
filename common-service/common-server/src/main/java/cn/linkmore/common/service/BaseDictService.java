package cn.linkmore.common.service;

import java.util.List;

import cn.linkmore.common.response.ResBaseDict;

/**
 * Service接口 - 区域信息
 * @author liwenlong
 * @version 1.0
 *
 */
public interface BaseDictService {

	List<ResBaseDict> selectList(String code);


}
