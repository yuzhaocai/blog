package com.class8.blog.services.impl;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.class8.blog.models.CourseBasicInfo;
import com.class8.blog.repositorys.CourseBasicInfoRepository;
import com.class8.blog.services.ICourseBasicInfoService;

@Service
@Transactional
public class CourseBasicInfoServiceImpl implements ICourseBasicInfoService {
	
	public static final String CACHE_COURSE = "cache.course";
	public static final String CACHE_COURSE_ALL = CACHE_COURSE + ".all";
	
	private static final Logger logger = LoggerFactory.getLogger(CourseBasicInfoServiceImpl.class);
	
	@Autowired
	private CourseBasicInfoRepository courseBasicInfoRepository;
	
	@Transactional(readOnly=true)
	@Cacheable(value=CACHE_COURSE,key="#courseid")
	public CourseBasicInfo getCourseBasicInfo(Long courseid) {
		logger.info("get course by courseid {} from database.",courseid);
		return courseBasicInfoRepository.findOne(courseid);
	}
	
	@CacheEvict(value=CACHE_COURSE_ALL,allEntries=true)
	public void saveCourseBasicInfo(CourseBasicInfo course) {
		logger.info("save course and courseid {}",course.getCourseid());
		courseBasicInfoRepository.save(course);
	}
	
	@Caching(evict={
			@CacheEvict(value=CACHE_COURSE,key="#course.courseid"),
			@CacheEvict(value=CACHE_COURSE_ALL,allEntries=true)
	})
	public void updateCourseBasicInfoName(CourseBasicInfo course) {
		logger.info("update course name for courseid {}",course.getCourseid());
		courseBasicInfoRepository.updateCourseBasicInfoName(course.getCourseName(),course.getCourseid());
	}
	
	@Caching(evict={
			@CacheEvict(value=CACHE_COURSE,key="#courseid"),
			@CacheEvict(value=CACHE_COURSE_ALL,allEntries=true)
	})
	public void deleteCourseBasicInfo(Long courseid) {
		logger.info("delete course by courseid {}",courseid);
		courseBasicInfoRepository.delete(courseid);
	}
	
	@Cacheable(value=CACHE_COURSE_ALL,key="#root.")
	public List<CourseBasicInfo> listCourseBasicInfo() {
		logger.info("get all course from database.");
		return courseBasicInfoRepository.findAll();
	}

}
