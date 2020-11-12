package com.jt.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

@Service
public class RedisService {

	/*//有的工程需要，有的工程不需要。设置required=false，有就注入，没有就不注入。
	//这个"required=false" 代表用到的时候才把这个属性添加进去,
	//不然没有创建ShardsJedisPool时，自动注入不了，服务器就启动报错
    @Autowired(required = false)
    private ShardedJedisPool shardedJedisPool;
    
    public void set(String key,String value) {
    	ShardedJedis jedis=shardedJedisPool.getResource();
    	jedis.set(key, value);
    	shardedJedisPool.returnResource(jedis);    	
    }
    
    // 添加超时时间
    public void set(String key,String value,int seconds) {
    	ShardedJedis jedis=shardedJedisPool.getResource();
    	jedis.setex(key, seconds, value);
    	shardedJedisPool.returnResource(jedis);    	
    }
    
    //编辑get方法
    public String get(String key) {
    	ShardedJedis jedis=shardedJedisPool.getResource();
    	String result=jedis.get(key);
    	shardedJedisPool.returnResource(jedis); 
    	return result;
    }*/
    
	//引入哨兵的配置
	@Autowired(required=false)
	private JedisSentinelPool jedisSentinelPool;
	
	public void set(String key,String value) {
		Jedis jedis = jedisSentinelPool.getResource();
		jedis.set(key, value);
		jedisSentinelPool.returnResource(jedis);			
	}
	
	public void set(String key,String value,int seconds) {
		Jedis jedis = jedisSentinelPool.getResource();
		jedis.setex(key, seconds, value);
		jedisSentinelPool.returnResource(jedis);			
	}
	
	public String get(String key) {
		Jedis jedis = jedisSentinelPool.getResource();
		String result = jedis.get(key);
		jedisSentinelPool.returnResource(jedis);	
		return result;
	}
	
    
    

}
