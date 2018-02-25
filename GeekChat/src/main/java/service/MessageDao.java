package service;

import java.util.List;
import java.util.Map;

import po.Message;

/**
 * @author Yuqi Li
 * date: Jan 25, 2018 2:03:24 PM
 */
public interface MessageDao extends MongoBase<Message>{
	List<Message> getMessageBy2Id(Map<String,Object> params);
}
