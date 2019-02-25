package cn.linkmore.order.config;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/* 项目启动后加载数据
 * @author   GFF
 * @Date     2018年5月29日
 * @Version  v2.0
 */
@Component
public class StartupRunner implements ApplicationContextAware {
	private static ApplicationContext context;


	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		context = applicationContext;
	}

	public static ApplicationContext get() {
		return context;
	}

}
