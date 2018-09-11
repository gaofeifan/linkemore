package cn.linkmore.task;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程池
 * @author   changlei
 * @Date     2018年9月10日
 * @Version  v1.0
 */
public class TaskPool {

	static ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
	
	private static class LazyHolder {
        final static TaskPool INSTANCE = new TaskPool();
    }
	
	public static final TaskPool getInstance() {
        return TaskPool.LazyHolder.INSTANCE;
    }
	
	public void task(Runnable runnable) {
        cachedThreadPool.execute(runnable);
    }
	
}
