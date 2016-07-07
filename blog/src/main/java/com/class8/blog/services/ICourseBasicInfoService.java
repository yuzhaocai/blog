package com.class8.blog.services;

import java.util.List;

import com.class8.blog.models.CourseBasicInfo;

public interface ICourseBasicInfoService {
	
	public CourseBasicInfo getCourseBasicInfo(Long courseid);
	
	public void saveCourseBasicInfo(CourseBasicInfo course);
	
	public void updateCourseBasicInfoName(CourseBasicInfo course);
	
	public void deleteCourseBasicInfo(Long courseid);
	
	public List<CourseBasicInfo> listCourseBasicInfo();
	
	
	
	
}
