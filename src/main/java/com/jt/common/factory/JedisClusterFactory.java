package com.jt.common.factory;

import java.io.IOException;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.core.io.Resource;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

public class JedisClusterFactory implements FactoryBean<JedisCluster>{
	
	//引入pro配置文件
	private Resource propertySource;
	private JedisPoolConfig poolConfig;
	private String redisNodePrefix;

	
	public Resource getPropertySource() {
		return propertySource;
	}

	public void setPropertySource(Resource propertySource) {
		this.propertySource = propertySource;
	}

	public JedisPoolConfig getPoolConfig() {
		return poolConfig;
	}

	public void setPoolConfig(JedisPoolConfig poolConfig) {
		this.poolConfig = poolConfig;
	}

	public String getRedisNodePrefix() {
		return redisNodePrefix;
	}

	public void setRedisNodePrefix(String redisNodePrefix) {
		this.redisNodePrefix = redisNodePrefix;
	}

	//获取Set集合信息
	public Set<HostAndPort> getNodes(){
		//操作properties
		Properties pro = new Properties();
		//ip:端口号
		Set<HostAndPort> nodes = new HashSet<HostAndPort>();
		try {
			pro.load(propertySource.getInputStream());
			
			//遍历获取nodes节点信息
			for (Object key : pro.keySet()) {
				//将key变为String类型，目的判断字符串方便
				String strKey = (String) key;
				//判断那个是redis节点数据
				if(strKey.startsWith(redisNodePrefix)) {					
					String value=pro.getProperty(strKey);
					String[] args=value.split(":");
					
					HostAndPort hostAndPort = new HostAndPort(args[0],Integer.valueOf(args[1]));
					nodes.add(hostAndPort);
				}				
			}			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return nodes;
	}	
	
	@Override
	public JedisCluster getObject() throws Exception {
		Set<HostAndPort> nodes = getNodes();		
		
		return new JedisCluster(nodes,poolConfig);
	}

	@Override
	public Class<?> getObjectType() {
		return JedisCluster.class;
	}

	@Override
	public boolean isSingleton() {
		return false;
	}
	

}
