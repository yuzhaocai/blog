package com.class8.blog.models;

public enum PostFormat {
	
	MARKDOWN("Markdown","markdown");
	
	private String displayName;
	private String slug;
	
	PostFormat(String displayName,String slug) {
		this.displayName = displayName;
        this.slug = slug;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getSlug() {
		return slug;
	}

	public void setSlug(String slug) {
		this.slug = slug;
	}
	
}
