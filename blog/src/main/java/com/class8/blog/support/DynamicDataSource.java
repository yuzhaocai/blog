package com.class8.blog.support;

import java.util.List;
import java.util.Random;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicDataSource extends AbstractRoutingDataSource {
	
	private static final Logger logger = LoggerFactory.getLogger(DynamicDataSource.class);
	private Random random = new Random();
	
	private String writeDataSourceKey;
	private List<String> readDataSourceKeys;
	
	public void setWriteDataSourceKey(String writeDataSourceKey) {
		this.writeDataSourceKey = writeDataSourceKey;
	}
	
	public String getWriteDataSourceKey() {
		return writeDataSourceKey;
	}
	
	public void setReadDataSourceKeys(List<String> readDataSourceKeys) {
		this.readDataSourceKeys = readDataSourceKeys;
	}
	
	public List<String> getReadDataSourceKeys() {
		return readDataSourceKeys;
	}

	@Override
	protected Object determineCurrentLookupKey() {
		DynamicDataSourceType dataSourceType = DynamicDataSourceReadWriteHolder.getType();
		String dataSourceKey = null;
		switch (dataSourceType) {
		case WRITE:
			dataSourceKey = writeDataSourceKey;
			break;
		case READ:
			dataSourceKey = readDataSourceKeys.get(random.nextInt(readDataSourceKeys.size()));
			break;
		}
		logger.info("Determine target DataSource for lookup key [{}]",dataSourceKey);
		return dataSourceKey;
	}
	
	@Override
	public void afterPropertiesSet() {
		super.afterPropertiesSet();
		if(StringUtils.isEmpty(writeDataSourceKey)){
			throw new IllegalArgumentException("Property 'targetDataSources' is not null or empty");
		}
		if(readDataSourceKeys == null){
			throw new IllegalArgumentException("Property 'readDataSourceKeys' is required");
		}
	}

}
