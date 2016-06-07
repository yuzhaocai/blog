package com.class8.blog.models;

import java.io.Serializable;

public class Post implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2180301050748415994L;
	
	private Long id;
	
	private User author;
	
	private String title;
	
	private PostCategory category;
	
	private PostFormat postFormat;
	
	private String rawContent;
	
	private String renderedContent;
	
	

}
