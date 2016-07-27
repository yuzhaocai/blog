package com.class8.blog.datasource;

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
/**
 * 读写分离数据源
 * @author Administrator
 *
 */
public class ReadWriteDataSource extends AbstractDataSource implements InitializingBean {

	private DataSourceLookup dataSourceLookup;
	private DataSource writeDataSource;
	private Map<Object,Object> readDataSources;
	private Map<Object,DataSource> resolvedReadDataSources;
	private int readDataSourceCount;
	private List<Object> readDataSourcelookupKeys;
	
	private Random random = new Random();
	
	public ReadWriteDataSource(){
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

	@SuppressWarnings({ "rawtypes", "unchecked" })
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
	 
	 /**
	  * 获取真实的数据源(多个读数据源采用随机选择)
	  * @return
	  */
	 protected DataSource determineTargetDataSource() {
		ReadWriteType type = ReadWriteDataSourceDecision.getType();
		switch (type) {
		case READ:
			Object lookupKey = readDataSourcelookupKeys.get(random.nextInt(readDataSourceCount));
			return resolvedReadDataSources.get(lookupKey);
		case WRITE:
			return writeDataSource;
		}
		return writeDataSource;
	}

}
