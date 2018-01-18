package com.nowcoder.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service
public class RedisAdapter implements InitializingBean {
	
	private static final Logger logger = LoggerFactory.getLogger(RedisAdapter.class);
	
    private JedisPool pool = null;

	@Override
	public void afterPropertiesSet() throws Exception {
		pool=new JedisPool("localhost", 6379);
	}
	
	private Jedis getJedis() {
        return pool.getResource();
    }
	
	
	public String get(String key) {
		Jedis jedis = null;
		try {
			jedis=getJedis();
			return jedis.get(key);
		} catch (Exception e) {
			logger.error("发生异常" + e.getMessage());
            return null;
		}finally {
			if(jedis != null) {
				jedis.close();
			}
		}
	}
	
	 public void set(String key, String value) {
	        Jedis jedis = null;
	        try {
	            jedis = getJedis();
	            jedis.set(key, value);
	        } catch (Exception e) {
	            logger.error("发生异常" + e.getMessage());
	        } finally {
	            if (jedis != null) {
	                jedis.close();
	            }
	        }
	    }
	 
	
	 public long sadd(String key,String value) {
		 Jedis jedis = null;
	        try {
	            jedis = getJedis();
	           //将一个或多个 member 元素加入到集合 key 当中，已经存在于集合的 member 元素将被忽略。
	            return jedis.sadd(key, value);
	        } catch (Exception e) {
	            logger.error("发生异常" + e.getMessage());
	            return 0;
	        } finally {
	            if (jedis != null) {
	                jedis.close();
	            }
	        }
	 }
	 
	 
	 public long srem(String key,String value) {
		 Jedis jedis = null;
	        try {
	            jedis = getJedis();
		//	            移除集合 key 中的一个或多个 member 元素，不存在的 member 元素会被忽略。
		//	            当 key 不是集合类型，返回一个错误。
	    //	            被成功移除的元素的数量，不包括被忽略的元素。
	            return jedis.srem(key, value);
	        } catch (Exception e) {
	            logger.error("发生异常" + e.getMessage());
	            return 0;
	        } finally {
	            if (jedis != null) {
	                jedis.close();
	            }
	        }
	 }
	 
	 
	 public boolean sismember(String key, String value) {
	        Jedis jedis = null;
	        try {
	            jedis = getJedis();
//	            判断 member 元素是否集合 key 的成员。
//	            如果 member 元素是集合的成员，返回 1 。
//	                              如果 member 元素不是集合的成员，或 key 不存在，返回 0 。
	            return jedis.sismember(key, value);
	        } catch (Exception e) {
	            logger.error("发生异常" + e.getMessage());
	            return false;
	        } finally {
	            if (jedis != null) {
	                jedis.close();
	            }
	        }
	    }
	 
	 
	 public long scard(String key) {
	        Jedis jedis = null;
	        try {
	            jedis = getJedis();
	            //返回集合 key 的基数(集合中元素的数量)。
			     //return:集合的基数。
			      //  当 key 不存在时，返回 0 。
	            return jedis.scard(key);
	        } catch (Exception e) {
	            logger.error("发生异常" + e.getMessage());
	            return 0;
	        } finally {
	            if (jedis != null) {
	                jedis.close();
	            }
	        }
	    }
	 
	 public long lpush(String key,String value) {
	        Jedis jedis = null;
	        try {
	            jedis = getJedis();
	          /* 
	           *  将一个或多个值 value 插入到列表 key 的表头
			              如果 key 不存在，一个空列表会被创建并执行 LPUSH 操作。
			              当 key 存在但不是列表类型时，返回一个错误。
	                                  执行 LPUSH 命令后，列表的长度。
	           */
	            return jedis.lpush(key, value);
	        } catch (Exception e) {
	            logger.error("发生异常" + e.getMessage());
	            return 0;
	        } finally {
	            if (jedis != null) {
	                jedis.close();
	            }
	        }
	    }
	 
	 public List<String> brpop(int timeout, String key) {
	        Jedis jedis = null;
	        try {
	            jedis = pool.getResource();
//	            假如在指定时间内没有任何元素被弹出，则返回一个 nil 和等待时长。
//	                              反之，返回一个含有两个元素的列表，第一个元素是被弹出元素所属的 key ，第二个元素是被弹出元素的值
	            return jedis.brpop(timeout, key);
	        } catch (Exception e) {
	            logger.error("发生异常" + e.getMessage());
	            return null;
	        } finally {
	            if (jedis != null) {
	                jedis.close();
	            }
	        }
	    }
	 
	 public <T> T getObject(String key,Class<T> clazz) {
		 String value=get(key);
		 if(value!=null) {
			 return JSON.parseObject(value,clazz);
		 }
		 return null;
	 }
	 
	 

}
