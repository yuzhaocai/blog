package com.class8.blog.support;

public class DynamicDataSourceReadWriteHolder {
	
	private static final ThreadLocal<DtataSourceType> typeHolder = new ThreadLocal<DtataSourceType>();
	
	public static void clear(){
		typeHolder.remove();
	}
	
	public static void markRead(){
		typeHolder.set(DtataSourceType.READ);
	}
	
	public static void markWrite(){
		typeHolder.set(DtataSourceType.WRITE);
	}
	
	public static boolean isChoiceWrite(){
		return DtataSourceType.WRITE == typeHolder.get();
	}
	
	public static boolean isChoiceRead(){
		return DtataSourceType.READ == typeHolder.get();
	}
	
	private enum DtataSourceType {
		READ,WRITE
	}
}
