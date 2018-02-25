package service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import message.GetOneResult;
import po.Message;
import service.MessageDao;

/**
 * @author Yuqi Li
 * date: Jan 25, 2018 2:33:01 PM
 */
@Repository("messageDaoImpl")
public class MessageDaoImpl implements MessageDao{
	
	String collectionName = "message";

	@Resource  
    private MongoTemplate mongoTemplate;
	
	@Override
	public void insert(Message object) {
		// TODO Auto-generated method stub
		mongoTemplate.insert(object, collectionName);
	}
	
	@Override
	public List<Message> getMessageBy2Id(Map<String, Object> params) {
		// TODO Auto-generated method stub
		Criteria criteria1 = new Criteria();
		Criteria criteria2 = new Criteria();
		criteria1.andOperator(Criteria.where("fromId").is(params.get("fromId")),Criteria.where("to").is(params.get("to")));
		criteria2.andOperator(Criteria.where("fromId").is(params.get("to")),Criteria.where("to").is(params.get("fromId")));
		Criteria criteria = new Criteria();
		criteria.orOperator(criteria1,criteria2);
		List<Message> messages = mongoTemplate.find(new Query(criteria), Message.class, collectionName);
		
		return messages;
	}

	@Override
	public Message findOne(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Message> findAll(Map<String, Object> params, String collectionName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Map<String, Object> params, String collectionName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void returnresult(GetOneResult result) {
		// TODO Auto-generated method stub
		
	}

}
