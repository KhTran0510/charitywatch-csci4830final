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
@Table(name = "foundation")
public class CarHa {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id")
   private Integer id;

   @Column(name = "first")
   private String first;
   
   @Column(name = "last")
   private String last;

   @Column(name = "Email")
   private String email;

   @Column(name = "address")
   private String address;

   @Column(name = "password")
   private String password;


public CarHa() {
   }

   public CarHa(Integer id, String first, String last, String email, String address, String password) {
      this.id = id;
	  this.first = first;
	  this.last = last;
	  this.email = email;
	  this.address= address;
	  this.password = password;
   }

   public CarHa(String first, String last, String email, String address, String password) {
	   this.first = first;
	   this.last = last;
	   this.email = email;
	   this.address= address;
	   this.password = password;
   }
   
   


   public String getFirst() {
	return first;
}

public void setFirst(String first) {
	this.first = first;
}

public String getLast() {
	return last;
}

public void setLast(String last) {
	this.last = last;
}

public String getEmail() {
	return email;
}

public void setEmail(String email) {
	this.email = email;
}

public String getAddress() {
	return address;
}

public void setAddress(String address) {
	this.address = address;
}

public String getPassword() {
	return password;
}

public void setPassword(String password) {
	this.password = password;
}

@Override
   public String toString() {
      return "Car: " + this.first + ", " + this.last + ", " + this.email + ", " +  this.address +  ", " + this.password;
   }
}