package blog;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.class8.blog.services.IUserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-core.xml")
public class TestJPA {
	
	@Autowired
	private IUserService userService;
	
	@Test
	public void testHbm2Ddl(){
		
	}
	
}
