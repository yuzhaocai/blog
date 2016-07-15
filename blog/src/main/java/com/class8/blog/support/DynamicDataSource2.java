package com.class8.blog.support;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.sql.DataSource;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.datasource.AbstractDataSource;
import org.springframework.jdbc.datasource.lookup.DataSourceLookup;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.util.CollectionUtils;

public class DynamicDataSource2 extends AbstractDataSource implements InitializingBean {
	
	private DataSourceLookup dataSourceLookup;
	private DataSource writeDataSource;
	private Map<Object,Object> readDataSources;
	private Map<Object,DataSource> resolvedReadDataSources;
	private int readDataSourceCount;
	private List<Object> readDataSourcelookupKeys;
	
	private Random random = new Random();
	
	public DynamicDataSource2(){
		dataSourceLookup = new JndiDataSourceLookup();
	}
	
	public void setWriteDataSource(DataSource writeDataSource) {
		this.writeDataSource = writeDataSource;
	}
	
	public void setReadDataSources(Map<Object, Object> readDataSources) {
		this.readDataSources = readDataSources;
	}
	
	public Connection getConnection() throws SQLException {
		return determineTargetDataSource().getConnection();
	}

	public Connection getConnection(String username, String password)
			throws SQLException {
		return determineTargetDataSource().getConnection(username, password);
	}

	public void afterPropertiesSet() throws Exception {
		if(writeDataSource == null) {
			throw new IllegalArgumentException("property 'writeDataSource' is required");
	    }
	    if(CollectionUtils.isEmpty(readDataSources)) {
	    	throw new IllegalArgumentException("property 'readDataSources' is required");
	    }
	    resolvedReadDataSources = new HashMap(readDataSources.size());
	    Object lookupKey;
	    DataSource dataSource;
	    for(Iterator iterator = readDataSources.entrySet().iterator(); iterator.hasNext(); resolvedReadDataSources.put(lookupKey, dataSource))
        {
            Map.Entry entry = (Map.Entry)iterator.next();
            lookupKey = resolveSpecifiedLookupKey(entry.getKey());
            dataSource = resolveSpecifiedDataSource(entry.getValue());
            readDataSourcelookupKeys.add(lookupKey);
        }
	    readDataSourceCount = resolvedReadDataSources.size();
	}
	
	 protected Object resolveSpecifiedLookupKey(Object lookupKey){
        return lookupKey;
	 }

	 protected DataSource resolveSpecifiedDataSource(Object dataSource) throws IllegalArgumentException {
        if(dataSource instanceof DataSource)
            return (DataSource)dataSource;
        if(dataSource instanceof String)
            return dataSourceLookup.getDataSource((String)dataSource);
        else
            throw new IllegalArgumentException((new StringBuilder()).append("Illegal data source value - only [javax.sql.DataSource] and String supported: ").append(dataSource).toString());
	 }
	
	 protected DataSource determineTargetDataSource() {
		if(DynamicDataSourceReadWriteHolder.isChoiceWrite()){
			return writeDataSource;
		}
		if(DynamicDataSourceReadWriteHolder.isChoiceRead()){
			Object lookupKey = readDataSourcelookupKeys.get(random.nextInt(readDataSourceCount));
			return resolvedReadDataSources.get(lookupKey);
		}
		return writeDataSource;
	}

}
