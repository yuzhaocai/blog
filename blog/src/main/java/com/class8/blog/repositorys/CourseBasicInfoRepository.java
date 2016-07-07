package com.class8.blog.repositorys;

import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import com.class8.blog.models.CourseBasicInfo;
/**
 * spring data jpa查询方法的解析规则
 * find…By, read…By, query…By, count…By,get…By
 * 
 */
public interface CourseBasicInfoRepository extends Repository<CourseBasicInfo, Long>{
	
	/**
	 * 保存
	 * @param course
	 */
	public void save(CourseBasicInfo course);
	
	/**
	 * 删除
	 * @param courseid
	 */
	public void delete(Long courseid);
	
	/**
	 * 查询所有
	 */
	public List<CourseBasicInfo> findAll();
	
	/**
	 * 根据主键获取课程(返回的对象为代理对象),类似于hibernate的load方法
	 * @param courseid
	 * @return
	 */
	public CourseBasicInfo getOne(Long courseid);
	
	/**
	 * 根据主键获取课程，返回实体对象，类似于hibernate的get方法
	 * @param courseid
	 * @return
	 */
	public CourseBasicInfo findOne(Long courseid);
	
	/**
	 * 更新课程名称
	 * @param course
	 */
	@Modifying
	@Query("update CourseBasicInfo set courseName = ?1 where courseid = ?2")
	public void updateCourseBasicInfoName(String courseName,Long courseid);
	
}
