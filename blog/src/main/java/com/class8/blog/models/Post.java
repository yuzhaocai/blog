package com.class8.blog.models;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.Type;
/**
 * @Entity必须，标示为JPA实体类
 * @Table可选，只有name与数据库表名不一致情况下使用
 *
 */
@Entity
@Table(name="post")
public class Post implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2180301050748415994L;
	/**
	 * @Id必须，标示为主键，只能有一个
	 * @GeneratedValue可选，表示主键的生成方式，默认为@GeneratedValue(strategy=GenerationType.AUTO)
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	/**
	 * fetch:LAZY,EAGER两种表示抓取策略，立即和懒惰
	 */
	@ManyToOne(cascade={CascadeType.PERSIST},fetch=FetchType.EAGER,optional=true)
	@JoinColumn(name="author_id",referencedColumnName="id")
	private User author;
	
	@Column(nullable=false,length=64)
	private String title;
	
	@Column(nullable=false)
	@Enumerated(EnumType.STRING)
	private PostCategory category;
	
	@Column(nullable=false)
	@Enumerated(EnumType.STRING)
	private PostFormat postFormat;
	
	/**
	 * @Column(columnDefinition="TEXT"):映射数据库中的text类型
	 * 或者使用hibernate注解
	 * @Type(type="TEXT")
	 */
	@Column(nullable=false)
	@Type(type="text")
	private String rawContent;
	
	@Column(nullable=false)
	@Type(type="text")
	private String renderedContent;
	
	/**
	 * @Basic可选，没有表示的字段默认为该注解
	 */
	@Column(nullable=false)
	@Type(type="text")
	private String renderdSummary;
	
	/**
	 * @Temporala(TemporalType.TIMESTAMP):表示映射数据库中某一个类型
	 */
	@Column(nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt = new Date();
	
	@Column(nullable=false)
	private boolean draft = true;
	
	@Column(nullable=false)
	private boolean broadcast = false;
	
	@Column(nullable=true,columnDefinition="DATETIME")
	private Date publishAt;
	
	@Column(nullable=true)
	private String publishSlug;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public PostCategory getCategory() {
		return category;
	}

	public void setCategory(PostCategory category) {
		this.category = category;
	}

	public PostFormat getPostFormat() {
		return postFormat;
	}

	public void setPostFormat(PostFormat postFormat) {
		this.postFormat = postFormat;
	}

	public String getRawContent() {
		return rawContent;
	}

	public void setRawContent(String rawContent) {
		this.rawContent = rawContent;
	}

	public String getRenderedContent() {
		return renderedContent;
	}

	public void setRenderedContent(String renderedContent) {
		this.renderedContent = renderedContent;
	}

	public String getRenderdSummary() {
		return renderdSummary;
	}

	public void setRenderdSummary(String renderdSummary) {
		this.renderdSummary = renderdSummary;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public boolean isDraft() {
		return draft;
	}

	public void setDraft(boolean draft) {
		this.draft = draft;
	}

	public boolean isBroadcast() {
		return broadcast;
	}

	public void setBroadcast(boolean broadcast) {
		this.broadcast = broadcast;
	}

	public Date getPublishAt() {
		return publishAt;
	}

	public void setPublishAt(Date publishAt) {
		this.publishAt = publishAt;
	}

	public String getPublishSlug() {
		return publishSlug;
	}

	public void setPublishSlug(String publishSlug) {
		this.publishSlug = publishSlug;
	}

}
