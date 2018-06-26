package certyficate.entitys;

public class Client{
	  public String name;
	  public String address;
	  public String postalCode;
	  public String town;
	  public String toString(){
	      return address+", "+postalCode+" "+town;
	  }
}
