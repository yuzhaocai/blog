package com.class8.blog.cache.memcached;

import java.util.Collection;
import net.spy.memcached.MemcachedClient;
import org.springframework.cache.Cache;
import org.springframework.cache.support.AbstractCacheManager;

public class MemcachedCacheManager extends AbstractCacheManager {
	
	private MemcachedClient memcachedClient;
	
	/**
	 * 注入memcached的spymemcached客户端
	 * @param memcachedClient
	 */
	public void setMemcachedClient(MemcachedClient memcachedClient){
		this.memcachedClient = memcachedClient;
	}
	
	/**
	 * spring cache与memcached的集成，没法返回所有预先定义好的cache，所以直接返回null
	 */
	@Override
	protected Collection<? extends Cache> loadCaches() {
		return null;
	}
	
	/**
	 * AbstractCacheManager中afterPropertiesSet方法中要求loadCaches不能返回null,所以重写该方法
	 */
	@Override
	public void afterPropertiesSet() {
		
	}
	
	/**
	 * 首先根据名称来获取cache，如果没有对应的cache，则创建cache，并加入到cache列表中
	 */
	@Override
	public Cache getCache(String name) {
		Cache cache = super.getCache(name);
		if(cache == null){
			cache = new MemcachedCache(name,memcachedClient);
			super.addCache(cache);
		}
		return super.getCache(name);
	}
}
