package blog;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.class8.blog.models.CourseBasicInfo;
import com.class8.blog.services.ICourseBasicInfoService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext-core.xml","classpath:applicationContext-memcached.xml"})
public class TestSpringCache {
	
	@Autowired
	private ICourseBasicInfoService courseBasicInfoService;
	
	@Test
	public void hbm2Ddl(){
		
	}
	
	@Test
	public void testSave(){
		CourseBasicInfo course = new CourseBasicInfo();
		course.setCourseid(2L);
		course.setCourseName("《文化苦旅》读后感");
		course.setCoverUrl("http://class8.com/678910.jpg");
		course.setCreateTime(new Date());
		course.setOnlineType(1);
		course.setPeople("全班同学");
		course.setPrice(50.89);
		course.setTarget("希望对这部文学作品有一个更深层次的认识");
		courseBasicInfoService.saveCourseBasicInfo(course);
	}
	
	/**
	 * 只后查询一次，第二次从缓存中获取对象
	 */
	@Test
	public void testGet(){
		CourseBasicInfo course = courseBasicInfoService.getCourseBasicInfo(1L);
		System.out.println(course);
		CourseBasicInfo course2 = courseBasicInfoService.getCourseBasicInfo(1L);
		System.out.println(course2);
	}
	
	/**
	 * 测试移除的时候memcached只会移除其对应的cache中的数据，而不是memcached缓存中的所有数据
	 */
	@Test
	public void testEvit(){
		//查询list(查询数据库)
		List<CourseBasicInfo> courses = courseBasicInfoService.listCourseBasicInfo();
		System.out.println(courses);
		//查询2(查询数据库)
		CourseBasicInfo course = courseBasicInfoService.getCourseBasicInfo(2L);
		System.out.println(course);
		//查询1(缓存)
		course = courseBasicInfoService.getCourseBasicInfo(1L);
		System.out.println(course);
		//后删除2
		courseBasicInfoService.deleteCourseBasicInfo(2L);
		//查询2(缓存)
		course = courseBasicInfoService.getCourseBasicInfo(2L);
		System.out.println(course);
		//查询list(查询数据库)
		courses = courseBasicInfoService.listCourseBasicInfo();
		System.out.println(courses);
		//查询1(缓存)
		course = courseBasicInfoService.getCourseBasicInfo(1L);
		System.out.println(course);
		
	}
	
	
	
}
