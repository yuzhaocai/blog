package blog;

import net.spy.memcached.MemcachedClient;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext-memcached.xml"})
public class TestMemcached {
	
	@Autowired
	private MemcachedClient memcachedClient;
	
	@Test
	public void normal(){
		memcachedClient.set("hello", 60*60*1, "xingfu");
		String value = (String) memcachedClient.get("hello");
		System.out.println(value);
	}
	
}
