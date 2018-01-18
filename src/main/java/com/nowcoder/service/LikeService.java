package com.nowcoder.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nowcoder.util.RedisKeyUtil;

@Service
public class LikeService {
	@Autowired
	RedisAdapter jedisAdapter;
	
	
	/**
	 * 用户对该条信息的状态 即 点赞，踩，无标示
	 * @TODO TODO
	 * @Time 2018年1月17日 下午2:43:58
	 * @author WEQ
	 * @return 1:点赞   -1:踩   0:无标示
	 */
	public int getLikeStatus(int userId, int entityType, int entityId) {
		String likeKey = RedisKeyUtil.getLikeKey(entityId, entityType);
		if(jedisAdapter.sismember(likeKey, String.valueOf(userId))) {
			return 1;
		}
		String disLikeKey = RedisKeyUtil.getDisLikeKey(entityId, entityType);
		return jedisAdapter.sismember(disLikeKey, String.valueOf(userId)) ? -1 : 0;
		
	}
	
	/**
	 * 喜欢
	 * @TODO TODO
	 * @Time 2018年1月17日 下午2:50:17
	 * @author WEQ
	 * @return long
	 */
	public long like(int userId, int entityType, int entityId) {
		//再喜欢集合增加
		String likeKey = RedisKeyUtil.getLikeKey(entityId, entityType);
		jedisAdapter.sadd(likeKey, String.valueOf(userId));
		
		 // 从反对里删除
        String disLikeKey = RedisKeyUtil.getDisLikeKey(entityId, entityType);
        jedisAdapter.srem(disLikeKey, String.valueOf(userId));
		return jedisAdapter.scard(likeKey);
	}
	
	/**
	 * 不喜欢
	 * @TODO TODO
	 * @Time 2018年1月17日 下午2:50:43
	 * @author WEQ
	 * @return long
	 */
	public long disLike(int userId, int entityType, int entityId) {
		
		String likeKey = RedisKeyUtil.getLikeKey(entityId, entityType);
		String disLikeKey = RedisKeyUtil.getDisLikeKey(entityId, entityType);
		//再反对集合增加
		jedisAdapter.sadd(disLikeKey, String.valueOf(userId));
		
		// 从喜欢里删除
        jedisAdapter.srem(likeKey, String.valueOf(userId));
		return jedisAdapter.scard(likeKey);
	}
}
