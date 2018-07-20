package cn.linkmore.enterprise.service;

public interface EntPreService {

	int saveEntPre(Long entId, String preName);

	int deleteEntPre(Long id);

	int updateEntPre(Long id, Long preId, String preName);

}
