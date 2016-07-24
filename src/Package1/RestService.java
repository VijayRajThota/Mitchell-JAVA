package Package1;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.cedarsoftware.util.io.JsonWriter;

@Path("/vehicles")
public class RestService {
	
	VehicleServices service = new VehicleServices();
	
	@GET
	@Path("/")
	@Produces("application/json")
	public Response getAllVehciles(){
		ArrayList<Vehicle>  temp = service.getAllVehicles();
		if(temp!=null){
		   ArrayList<JSONObject> result = new ArrayList<JSONObject>();
		   for(int i=0;i<temp.size();i++){
			   JSONObject v = new JSONObject();
			   v.put("id", temp.get(i).getId());
			   v.put("year", temp.get(i).getYear());
			   v.put("make",temp.get(i).getMake());
			   v.put("model", temp.get(i).getModel());
			   result.add(v);
		   }
		   return Response.status(200).entity(result.toString()).build();
		}
		else{
			JSONObject result = new JSONObject();
			result.put("message", "error");
			return Response.status(200).entity(result.toJSONString()).build();
		}
	}
	
	@GET
	@Path("/{id}")
	@Produces("application/json")
	public Response getVehicle(@PathParam("id") long id){
		Vehicle temp = service.getVehicle(id);
		if(temp!=null)
			return Response.status(200).entity(JsonWriter.objectToJson(temp)).build();
		else{
			JSONObject result = new JSONObject();
			result.put("message", "error");
			return Response.status(200).entity(result.toJSONString()).build();
		}
	}
	
	@POST
	@Path("/")
	@Consumes("application/json")
	@Produces("application/json")
	public Response insertVehicle(InputStream input) throws Exception{
		BufferedReader in = new BufferedReader(new InputStreamReader(input));
		StringBuffer temp = new StringBuffer();
		String line = null;
		while((line = in.readLine()) != null) {		
			temp.append(line);
		}
		String s = new String(temp);
		JSONParser parser = new JSONParser();
		JSONObject x = (JSONObject)parser.parse(s);
		long year;
		String make;
		String model;
		if(x.get("Year")!=null){
			year = (long)x.get("Year");
			if (year<1950 || year>2050){
				JSONObject error = new JSONObject();
				error.put("message", "Year value not in range");
				return Response.status(200).entity(error.toJSONString()).build();
			}
		}
		else{
			JSONObject error = new JSONObject();
			error.put("message", "Year value not provided");
			return Response.status(200).entity(error.toJSONString()).build();
		}
		if(x.get("Make")!=null){
			make = (String)x.get("Make");
			if(make.isEmpty()|| make==" "){
				JSONObject error = new JSONObject();
				error.put("message", "make value cannot be empty");
				return Response.status(200).entity(error.toJSONString()).build();
			}
		}
		else{
			JSONObject error = new JSONObject();
			error.put("message", "Make value not provided");
			return Response.status(200).entity(error.toJSONString()).build();
		}
		if(x.get("Model")!=null){
			model = (String)x.get("Model");
			if(model.isEmpty()||model == null || model==" "){
				JSONObject error = new JSONObject();
				error.put("message", "model value cannot be empty");
				return Response.status(200).entity(error.toJSONString()).build();
			}
		}
		else{
			JSONObject error = new JSONObject();
			error.put("message", "Model value not provided");
			return Response.status(200).entity(error.toJSONString()).build();
		}
    	boolean result = service.insertVehicle(year,make,model);
		JSONObject finalresult = new JSONObject();
		if(result == true)
			finalresult.put("message", "success");
		else
			finalresult.put("message", "error");
		
		return Response.status(200).entity(finalresult.toJSONString()).build();
	}
	
	@PUT
	@Path("/")
	@Consumes("application/json")
	@Produces("application/json")
	public Response updateVehicle(InputStream input)throws Exception{
		BufferedReader in = new BufferedReader(new InputStreamReader(input));
		StringBuffer temp = new StringBuffer();
		String line = null;
		while((line = in.readLine())!=null){
			temp.append(line);
		}
		String s = new String(temp);
		boolean result = service.updateVehicle(s);
		JSONObject finalresult = new JSONObject();
		if(result==true)
			finalresult.put("message", "success");
		else
			finalresult.put("message", "error");
		
		return Response.status(200).entity(finalresult.toJSONString()).build();
	}
	
	@DELETE
	@Path("/{id}")
	@Produces("application/json")
	public Response deleteVehicle(@PathParam("id") long id){
		boolean temp = service.deleteVehicle(id);
		JSONObject finalresult = new JSONObject();
		if(temp==true)
			finalresult.put("message", "success");
		else
			finalresult.put("message", "error");
		
		return Response.status(200).entity(finalresult.toJSONString()).build();
	}

}
