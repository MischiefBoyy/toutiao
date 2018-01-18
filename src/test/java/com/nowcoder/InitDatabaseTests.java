package com.nowcoder;

import java.util.Date;
import java.util.Random;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nowcoder.dao.CommentDAO;
import com.nowcoder.dao.MessageDAO;
import com.nowcoder.dao.NewsDAO;
import com.nowcoder.dao.UserDAO;
import com.nowcoder.model.Comment;
import com.nowcoder.model.EntityType;
import com.nowcoder.model.News;
import com.nowcoder.model.User;
import com.nowcoder.service.MessageService;
import com.nowcoder.util.ToutiaoUtil;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ToutiaoApplication.class)
//@Sql("/init-schema.sql")
public class InitDatabaseTests {
	@Autowired
    NewsDAO newsDAO;
	
	@Autowired
    UserDAO userDAO;

	@Autowired
    CommentDAO commentDAO;
	
	@Autowired
	MessageService messageService;
    @Test
    public void initData() {
    	Random random=new Random();
    	
    	for(int i=0;i<30;i++) {
    		
    		User user = new User();
            user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png", random.nextInt(1000)));
            user.setName(String.format("USER%d", i));
            user.setSalt("qwe");
            user.setPassword(ToutiaoUtil.MD5("123123qwe"));
            userDAO.addUser(user);
    		
    		News news=new News();
    		news.setCommentCount(0);
            Date date = new Date();
            date.setTime(date.getTime() + 1000*3600*5*i);
            news.setCreatedDate(date);
            news.setImage(String.format("http://images.nowcoder.com/head/%dm.png", random.nextInt(1000)));
            news.setLikeCount(0);
            news.setUserId(i+1);
            news.setTitle(String.format("TITLE{%d}", i));
            news.setLink(String.format("http://www.nowcoder.com/%d.html", i));
            newsDAO.addNew(news);
            
            
         // 给每个资讯插入3个评论
            for(int j = 0; j < 3; ++j) {
                Comment comment = new Comment();
                comment.setUserId(i+1);
                comment.setCreatedDate(new Date());
                comment.setStatus(0);
                comment.setContent("这里是一个评论啊！" + String.valueOf(j));
                comment.setEntityId(news.getId());
                comment.setEntityType(EntityType.ENTITY_NEWS);
                commentDAO.addComment(comment);
            }
    	}
    	
    	
    }
    
    @Test
    public void addMessage() {
    	for(int i=2;i<10;i++) {
    		for(int j=0;j<3;j++) {
    			messageService.addMessage("接收"+i, 2, i+1);
        		messageService.addMessage("发送"+i, i+1, 2);
    		}
    		
    	}
    }

}
