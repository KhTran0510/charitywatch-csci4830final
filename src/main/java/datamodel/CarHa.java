package datamodel;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @since J2SE-1.8
 CREATE TABLE employee (
  id INT NOT NULL AUTO_INCREMENT,    
  name VARCHAR(30) NOT NULL,   
  age INT NOT NULL,    
  PRIMARY KEY (id));
 */
@Entity
@Table(name = "Cars")
public class CarHa {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id")
   private Integer id;

   @Column(name = "Makes")
   private String make;
   
   @Column(name = "Models")
   private String model;
   
   @Column(name = "VIN")
   private String vin;

   @Column(name = "Year")
   private String year;
   
   @Column(name = "Price")
   private String price;
   
   @Column(name = "Phone")
   private String phone;
   
   @Column(name = "Email")
   private String email;



public CarHa() {
   }

   public CarHa(Integer id, String make, String model, String vin, String year, String price, String phone, String email) {
      this.id = id;
      this.make = make;
      this.model = model;
      this.vin = vin;
      this.year= year;
      this.price = price;
      this.phone = phone;
      this.email = email;
   }

   public CarHa(String make, String model, String vin, String year, String price, String phone, String email) {
	   this.make = make;
	   this.model = model;
	   this.vin = vin;
	   this.year= year;
	   this.price = price;
	   this.phone = phone;
	   this.email = email;
   }

   public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id = id;
   }

   public String getMake() {
      return make;
   }

   public void setMake(String make) {
	   this.make = make;
   }
   
   public String getModel() {
	   return model;
	   }

   public void setModel(String model) {
	   this.model = model;
	   }
   
   public String getVin() {
	   return vin;
	   }

   public void setVin(String vin) {
	   this.vin = vin;
	   }
   

   public String getYear() {
      return year;
   }

   public void setYear(String year) {
      this.year = year;
   }
   
   public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
   @Override
   public String toString() {
      return "Car: " + this.id + ", " + this.make + ", " + this.model + ", " +  this.vin +  ", " + this.year +  ", " + this.price +  ", " + this.phone +  ", " + this.email;
   }
}