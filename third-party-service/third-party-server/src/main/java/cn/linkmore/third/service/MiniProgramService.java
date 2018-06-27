package cn.linkmore.third.service;

import cn.linkmore.third.response.ResMiniSession;

public interface MiniProgramService {

	ResMiniSession getSession(String code);

}
