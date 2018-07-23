package cn.linkmore.redis;

/**
 * Redis消息处理接口
 * @author liwenlong
 * @version 2.0
 *
 */
public interface RedisMessageHandler{
	void handle(String message);
}
