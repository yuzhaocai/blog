package com.class8.blog.models;

public enum PostCategory {
	
	ENGINEERING("Engineering", "engineering"),
    RELEASES("Releases", "releases"),
    NEWS_AND_EVENTS("News and Events", "news");
	
	private String displayName;
	
	private String urlSlug;
	
	PostCategory(String displayName, String urlSlug) {
		this.displayName = displayName;
		this.urlSlug = urlSlug;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getUrlSlug() {
		return urlSlug;
	}

	public void setUrlSlug(String urlSlug) {
		this.urlSlug = urlSlug;
	}
	
}
