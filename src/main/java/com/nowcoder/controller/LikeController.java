package com.nowcoder.controller;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nowcoder.model.EntityType;
import com.nowcoder.model.HostHolder;
import com.nowcoder.service.LikeService;
import com.nowcoder.service.NewsService;
import com.nowcoder.util.ToutiaoUtil;

@Controller
public class LikeController {
	@Autowired
	LikeService likeService;

	@Autowired
	HostHolder hostHolder;

	@Autowired
	NewsService newsService;
	
	
	@RequestMapping(path = {"/like"}, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String like(@Param("newId") int newsId) {
		long likeCount=likeService.like(hostHolder.getUser().getId(), EntityType.ENTITY_NEWS, newsId);
		
		// 更新喜欢数
        newsService.updateLikeCount(newsId, (int)likeCount);
        return ToutiaoUtil.getJSONString(0, String.valueOf(likeCount));
	}
	
	 @RequestMapping(path = {"/dislike"}, method = {RequestMethod.GET, RequestMethod.POST})
	    @ResponseBody
	    public String dislike(@Param("newId") int newsId) {
	        long likeCount = likeService.disLike(hostHolder.getUser().getId(), EntityType.ENTITY_NEWS, newsId);
	        // 更新喜欢数
	        newsService.updateLikeCount(newsId, (int) likeCount);
	        return ToutiaoUtil.getJSONString(0, String.valueOf(likeCount));
	    }

}
