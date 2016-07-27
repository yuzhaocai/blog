package com.class8.blog.datasource;
/**
 * 读写数据源判别器
 * @author Administrator
 *
 */
public class ReadWriteDataSourceDecision {
	
	private static final ThreadLocal<ReadWriteType> readWriteTypeHolder = new ThreadLocal<ReadWriteType>();
	
	public static void setType(ReadWriteType type){
		readWriteTypeHolder.set(type);
	}
	
	public static ReadWriteType getType(){
		return readWriteTypeHolder.get();
	}
	
	public static void clear(){
		readWriteTypeHolder.remove();
	}
}
