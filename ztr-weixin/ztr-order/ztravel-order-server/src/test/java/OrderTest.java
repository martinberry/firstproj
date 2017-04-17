import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

import com.travelzen.framework.util.TZBeanUtils;
import com.ztravel.common.test.SpringJUnit4ClassRunnerWithLog;
import com.ztravel.order.dao.ICommonOrderDao;
import com.ztravel.order.po.CommonOrder;
import com.ztravel.order.server.config.AppConfig;

@RunWith(SpringJUnit4ClassRunnerWithLog.class)
@ContextConfiguration(classes = AppConfig.class)
public class OrderTest {
	
	@Resource
	ICommonOrderDao commonOrderDaoImpl;
	
	  @Test
	    public void testCreateAccount() throws Exception{
	     CommonOrder commonOrder = commonOrderDaoImpl.selectById("1601291454387501");
	     System.out.println(TZBeanUtils.describe(commonOrder));
	        
	    }
}
