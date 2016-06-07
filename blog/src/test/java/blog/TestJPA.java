package blog;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.class8.blog.models.User;
import com.class8.blog.services.IUserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-core.xml")
public class TestJPA {
	
	@Autowired
	private IUserService userService;
	
	@Test
	public void testHbm2Ddl(){
		
	}
	
	@Test
	public void testInsert(){
		User user = new User();
		user.setUsername("yuzhaocai");
		user.setPassword("123456");
		user.setNickName("情非得已");
		user.setEmail("870646595@qq.com");
		userService.createUser(user);
	}
}
