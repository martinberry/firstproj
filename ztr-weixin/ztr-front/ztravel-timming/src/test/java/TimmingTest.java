import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

import com.ztravel.common.test.SpringJUnit4ClassRunnerWithLog;
import com.ztravel.order.timming.service.OrderTimmingService;
import com.ztravel.timming.config.AppConfig;


@RunWith(SpringJUnit4ClassRunnerWithLog.class)
@ContextConfiguration(classes = AppConfig.class)
public class TimmingTest {
	
	@Resource
	private OrderTimmingService orderTimmingService ;
	
	@Test
	public void autoComplete(){
		orderTimmingService.setAutoCompleted();
	}
	
	@Test
	public void autoNotice(){
		orderTimmingService.setAutoNotice();
	}
}
