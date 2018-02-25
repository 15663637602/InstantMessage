package service;

import java.util.List;
import java.util.Map;

import message.GetOneResult;
import po.User;

/**
 * @author Yuqi Li
 * date: Dec 2, 2017 10:48:43 PM
 */
public interface MongoBase<T> {  
    public void insert(T object);    
    public T findOne(Map<String,Object> params);    

    public List<T> findAll(Map<String,Object> params,String collectionName);    
    
    public void update(Map<String,Object> params,String collectionName);   

    /*public void createCollection(String collectionName);  

    public void remove(Map<String,Object> params,String collectionName);  */


    public void returnresult(GetOneResult result);
      
}  
