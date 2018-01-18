package com.nowcoder.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.nowcoder.model.Message;

@Mapper
public interface MessageDAO {
	String TABLE_NAME = " message ";
	String INSERT_FIELDS = " from_id, to_id, content, has_read, conversation_id, created_date ";
	String SELECT_FIELDS = " id, " + INSERT_FIELDS;

	@Insert({ "insert into ", TABLE_NAME, "(", INSERT_FIELDS,") values (#{fromId},#{toId},#{content},#{hasRead},#{conversationId},#{createdDate})" })
	int addMessage(Message message);
	
	@Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where conversation_id=#{conversationId} order by id desc limit #{offset},#{limit}"})
    List<Message> getConversationDetail(@Param("conversationId") String conversationId, @Param("offset") int offset, @Param("limit") int limit);
	
	@Select({"select count(id) from ", TABLE_NAME, " where has_read = 0 and to_id=#{userId} and conversation_id=#{conversationId}"})
    int getConversationUnReadCount(@Param("userId") int userId, @Param("conversationId") String conversationId);
	
	@Select({"select count(id) from ", TABLE_NAME, " where has_read = 0 and to_id=#{userId}"})
    int getConversationTotalCount(@Param("userId") int userId, @Param("conversationId") String conversationId);
	
	 @Select({"SELECT from_id, to_id, content, has_read, ms.conversation_id, created_date ,tt.countNum as id from message ms ,(select COUNT(id) AS countNum,conversation_id,MAX(id) as maxId from message where from_id=#{userId} or to_id=#{userId} GROUP BY conversation_id) tt WHERE ms.id = tt.maxId ORDER BY ms.id DESC limit #{offset},#{limit}"})
	    List<Message> getConversationList(@Param("userId") int userId, @Param("offset") int offset, @Param("limit") int limit);
	
}
