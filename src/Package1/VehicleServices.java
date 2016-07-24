package Package1;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ws.rs.core.Response;

import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.cedarsoftware.util.io.JsonWriter;

public class VehicleServices {
	public Configuration cfg;
	public SessionFactory factory;
	
	public VehicleServices(){
		try{
		cfg=new Configuration().configure("hibernate.cfg.xml");
		factory=cfg.buildSessionFactory();
		}
		catch(HibernateException e){
			System.err.println("Failed to create sessionFactory object." + e);
	         throw new ExceptionInInitializerError(e); 
		}
	}
	
    public ArrayList<Vehicle> getAllVehicles() {
    	ArrayList<Vehicle> result;
    	try{
    	Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		Query query = session.createQuery("From Vehicle");
		List list = query.list();
		if(list.size()>0){
			result = new ArrayList<Vehicle>();
		    Iterator itr = list.iterator();
		    while(itr.hasNext()){
			   Vehicle v = (Vehicle)itr.next();
			   result.add(v);
			   itr.remove();
		    }
		}
		else
			result = null;
		
		t.commit();
		session.close();
    	}
		catch(HibernateException e){
			System.err.println("Failed get vehicles information." + e);
			result = null;
		}
    	
    	return result;	
    }
    
    public Vehicle getVehicle(long id){
   		Vehicle result = null;
    	try{
    		Session session = factory.openSession();
    		Transaction t = session.beginTransaction();
    		Query query = session.createQuery("From Vehicle where Id= :id");
    		query.setParameter("id", id);
    		List list = query.list();
    		if(list.size()>0){
    			Iterator itr = list.iterator();
    		    if(itr.hasNext()){
    			   Vehicle v = (Vehicle)itr.next();
    			   result = v;
    			   itr.remove();
    		    }
    		}
    		t.commit();
    		session.close();
    	}
    	catch(HibernateException e){
    		System.err.println("Failed to get Vechile Information." + e);
    	}
    	return result;
    }
    
    public boolean insertVehicle( long year, String make, String model){
    	boolean result = false;
    	try{
    		Session session = factory.openSession();
    		Transaction t = session.beginTransaction();
    		Vehicle v = new Vehicle();
    		v.setYear(year);
    		v.setMake(make);
    		v.setModel(model);
    		session.persist(v);
    		result = true;
    		t.commit();
    		session.close();
    	}
    	catch(HibernateException e){
    		System.err.println("Failed to create  Vehicle Entry." + e);
    	}
    	return result;
    }
   
    public boolean updateVehicle(String S) throws Exception{
    	boolean result = false;
    	try{
    		Session session = factory.openSession();
    		Transaction t = session.beginTransaction();
        	JSONParser parser = new JSONParser();
        	JSONObject temp = (JSONObject)parser.parse(S);
        	long id = (long)temp.get("Id");
        	Query query = session.createQuery("From Vehicle where Id= :id");
        	query.setParameter("id",id);
        	List list =query.list();
        	if(list.size()==0)
        		return result;
        	Iterator itr = list.iterator();
        	Vehicle v = null;
        	if(itr.hasNext()){
        	    v = (Vehicle)itr.next();
        		itr.remove();
        	}
        	v.setYear((long)temp.get("Year"));
        	v.setMake((String)temp.get("Make"));
        	v.setModel((String)temp.get("Model"));
        	session.persist(v);
        	t.commit();
        	session.close();
        	result = true;
    	}
    	catch(HibernateException e){
    		System.err.println("Failed to update Vehicle entry."+ e);
    	}
    	return result;
    }
    
   public boolean deleteVehicle(long id){
	   boolean result = false;
	   try{
		   Session session = factory.openSession();
   		   Transaction t = session.beginTransaction();
   		   Query query = session.createQuery("From Vehicle where Id = :id");
   		   query.setParameter("id", id);
   		   List list = query.list();
   		   if(list.size()==0)
   			   return result;
   		   Iterator itr = list.iterator();
   		   Vehicle v = null;
   		   if(itr.hasNext()){
   			   v = (Vehicle)itr.next();
   			   itr.remove();
   		   }
   		   
   		   session.remove(v);
   		   t.commit();
   		   session.close();
   		   result = true;
	   }
	   catch(HibernateException e){
		   System.err.println("failed to delete Vehicle entry."+ e);
	   }
	   return result;
   }
    
    

}
