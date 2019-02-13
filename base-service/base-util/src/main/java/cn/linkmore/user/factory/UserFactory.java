package cn.linkmore.user.factory;

public interface UserFactory {

	public static final String MINI = "mini:";
	
	public String createUserRedisKey(String token,String os);
}
