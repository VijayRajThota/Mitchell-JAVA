package Package1;

import javax.persistence.*;

@Entity
@Table(name="Vehicle")
public class Vehicle {

	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="Id")
	private long id;

	@Column(name="Year")
	private long year;

	@Column(name="Make")	
	private String make;

	@Column(name="Model")
	private String model; 

	public Vehicle(){
		
	}

	public void setId(long id){
		this.id = id;	
	}
	public long getId(){
		return id;
	}

	public void setYear(long year){
		this.year = year;
	}
	public long getYear(){
		return year;
	}

	public void setMake(String make){
		this.make = make;
	}
	public String getMake(){
		return make;
	}

	public void setModel(String model){
		this.model = model;
	}
	public String getModel(){
		return model;
	}

}
