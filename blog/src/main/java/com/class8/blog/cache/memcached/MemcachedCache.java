package com.class8.blog.cache.memcached;

import java.util.LinkedHashMap;
import java.util.Map;
import net.spy.memcached.MemcachedClient;
import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;
import org.springframework.util.Assert;

public class MemcachedCache implements Cache {
	/**
	 * 作为所有key的value
	 */
	private static final String PRESENT = new String();
	
	/**
	 * 单个cache存储的key的最大数量
	 */
	private static final int MAX_ELEMENTS = 10000;
	
	/**
	 * 默认过期时间7天
	 */
	private static final int expire = 7 * 24 * 60 * 60;
	
	private String name;
	private MemcachedClient  memcachedClient;	
	private FixedSizeLinkedHashMap keys;
	
	public MemcachedCache(String name,MemcachedClient memcachedClient){
		Assert.hasText(name,"Cache name must not be null or empty");
		Assert.notNull(memcachedClient, "MemcachedClient must not be null");
		this.name = name;
		this.memcachedClient = memcachedClient;
		this.keys = new FixedSizeLinkedHashMap(MAX_ELEMENTS);
	}
	
	/**
	 * 实现了只清除对应cache中的所有key，而不是所有的数据
	 */
	public void clear() {
		for (String key : keys.keySet()) {
			memcachedClient.delete(key);
		}
		keys.clear();
	}
	
	/**
	 * 从keys中移除key，并从缓存中移除
	 */
	public void evict(Object key) {
		String cacheKey = this.getCacheKey(key);
		keys.remove(cacheKey);
		memcachedClient.delete(cacheKey);
	}
	
	/**
	 * 从缓存中获取值
	 */
	public ValueWrapper get(Object key) {
		String cacheKey = this.getCacheKey(key);
		if (keys.containsKey(cacheKey)) {
			Object value = memcachedClient.get(cacheKey);
			if(value == null)
				keys.remove(cacheKey);
	        return toWrapper(value);
	    } else {
	        return null;
	    }
	}
	/**
	 * 根据key获取对应的值
	 */
	public <T> T get(Object key, Class<T> type) {
		String cacheKey = this.getCacheKey(key);
		if (keys.containsKey(cacheKey)) {
			Object value = memcachedClient.get(cacheKey);
			if(value == null){
				keys.remove(cacheKey);
				return null;
			} else if(type != null && !type.isInstance(value)){
	            throw new IllegalStateException((new StringBuilder()).append("Cached value is not of required type [").append(type.getName()).append("]: ").append(value).toString());
			} else
	            return (T)value;
	    } else {
	        return null;
	    }
	}
	/**
	 * 获取缓存的名称
	 */
	public String getName() {
		return this.name;
	}
	/**
	 * 获取memcached的客户端
	 */
	public Object getNativeCache() {
		return this.memcachedClient;
	}
	/**
	 * 将值存入缓存中(如果value为空，将出现不能Serializable序列化的问题)
	 */
	public void put(Object key, Object value) {
		if(value != null){
			String cacheKey = this.getCacheKey(key);
			memcachedClient.set(cacheKey, expire, value);
			keys.put(cacheKey, PRESENT);
		}
	}
	
	/**
	 * 如果不存在则存入
	 */
	public ValueWrapper putIfAbsent(Object key, Object value) {
		String cacheKey = this.getCacheKey(key);
		if(!keys.containsKey(cacheKey)){//表示一定不存在
			memcachedClient.set(cacheKey, expire, value);
			keys.put(cacheKey, PRESENT);
		} else {//表示可能不存在
			ValueWrapper valueWrapper = this.get(cacheKey);
			if(valueWrapper != null){
				return valueWrapper;
			} else {
				memcachedClient.set(cacheKey, expire, value);
				keys.put(cacheKey, PRESENT);//主要为表示key为新添加的，不至于排在map的前边而被移除
			}
		}
		return toWrapper(value);
	}
	
	/**
	 * 获取最终的key，通过cache的名称和key
	 * @param key
	 * @return
	 */
	private String getCacheKey(Object key){
		return name + "." + String.valueOf(key);
	}
	
	private ValueWrapper toWrapper(Object value){
		return value != null ? new SimpleValueWrapper(value) : null;
	}
	
	/**
	 * key的保存方案：
	 * 1.本地通过集合进行存储，并实现lru算法，效率快，但在集群环境中不适用
	 * 2.将key保存到set中，然后将set保存到分布式缓存中，然后每次操作缓存时先取出该key，虽然效率会慢一些，但能支持集群环境
	 * 
	 * 	实现固定大小的map集合，当元素超过固定大小，当removeEldestEntry返回true,put进新的值时，便移除该map中最老的键和值。
	 * @author Administrator
	 *
	 */
	class FixedSizeLinkedHashMap extends LinkedHashMap<String, String>{
		/**
		 * 
		 */
		private static final long serialVersionUID = 3367517980476207664L;
		private int maxSize;
		
		public FixedSizeLinkedHashMap(int initSize) {
			super(initSize, 0.75F, true);
		    this.maxSize = initSize;
		}

		public boolean removeEldestEntry(Map.Entry<String, String> eldest) {
			boolean overflow = size() > this.maxSize;
			if (overflow) {
				MemcachedCache.this.memcachedClient.delete(eldest.getKey());
		    }
		    return overflow;
		}
	}
	
}
