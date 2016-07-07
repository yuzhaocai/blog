package com.class8.blog.models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="course_basic_info")
public class CourseBasicInfo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6921183871430669552L;
	
	@Id
	@Column(name="course_id")
	private Long courseid;
	
	@Column(name="course_name",nullable=false,length=128)
	private String courseName;
	
	@Column(name="online_type")
	private Integer onlineType;
	
	@Column(name="target",nullable=true,length=1024)
	private String target;
	
	@Column(name="people",nullable=true,length=1024)
	private String people;
	
	@Column(name="cover_url",nullable=true,length=128)
	private String coverUrl;
	
	@Column(name="price",nullable=false)
	private Double price;
	
	/**
	 * 默认创建表的数据类型为datetime,当设定为TemporalType.DATE，则创建表的类型为date类型
	 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;

	public Long getCourseid() {
		return courseid;
	}

	public void setCourseid(Long courseid) {
		this.courseid = courseid;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public Integer getOnlineType() {
		return onlineType;
	}

	public void setOnlineType(Integer onlineType) {
		this.onlineType = onlineType;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getPeople() {
		return people;
	}

	public void setPeople(String people) {
		this.people = people;
	}

	public String getCoverUrl() {
		return coverUrl;
	}

	public void setCoverUrl(String coverUrl) {
		this.coverUrl = coverUrl;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "CourseBasicInfo [courseid=" + courseid + ", courseName="
				+ courseName + ", onlineType=" + onlineType + ", target="
				+ target + ", people=" + people + ", coverUrl=" + coverUrl
				+ ", price=" + price + ", createTime=" + createTime + "]";
	}
	
	
}
