//Name: Chloe Ngo
//ID  : 501205941

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.TreeMap;
import java.util.Iterator;

/*
 * 
 * This class contains the main logic of the system.
 * 
 *  It keeps track of all users, drivers and service requests (RIDE or DELIVERY)
 * 
 */
public class TMUberSystemManager 
{
  private ArrayList<User> userList;       
  private Map<String, User> users;
  private ArrayList<Driver> drivers;
  private Queue<TMUberService>[] queues;
  public double totalRevenue; // Total revenues accumulated via rides and deliveries
  
  // Rates per city block
  private static final double DELIVERYRATE = 1.2;
  private static final double RIDERATE = 1.5;
  // Portion of a ride/delivery cost paid to the driver
  private static final double PAYRATE = 0.1;

  //These variables are used to generate user account and driver ids
  int userAccountId = 900;
  int driverId = 700;

  public TMUberSystemManager()
  {
    // Map with String id as key, User object as value
    users = new TreeMap<>(); 

    // List of user objects     
    userList = new ArrayList<User>();
    drivers = new ArrayList<Driver>();

    // An array of length 4
    queues = new Queue[4];

    // Initialize empty queues to array
    for (int i = 0; i < queues.length; i++) {
      queues[i] = new LinkedList<>();
    }
    
    totalRevenue = 0;
  }

  // Given user account id, find user in list of users
  // Return null if not found
  public User getUser(String accountId)
  {
    // Fill in the code
    return users.get(accountId);
  }

  // Get list of users
  public ArrayList<User> getUserList() 
  {
    return this.userList;
  }

  // Set list of users
  public void setUserList(ArrayList<User> userList)
  {
    this.userList = userList;
  }

  // Get list of drivers
  public ArrayList<Driver> getDriversList()
  {
    return this.drivers;
  }

  // Given driver id, find driver in list of drivers
  // Return null if not found
  public Driver getDriver(String driverId) 
  {
    for (Driver driver : drivers) 
    {
      if (driver.getId().equals(driverId)) 
      {
        return driver;
      }
    }
    return null;
  }

  // Check for duplicate user
  private void userExists(User user)
  {
    // Fill in the code
    if (users.containsValue(user)) 
    {
      throw new DuplicateException("User Already Exists in System");
    } 
  }
  
 // Check for duplicate driver
 private void driverExists(Driver driver)
 {
   // Fill in the code
  for (Driver aDriver : drivers) 
  {
    if(aDriver.equals(driver)) 
    {
      throw new DuplicateException("Driver Already Exists in System");
    }
  }
 }
  
  // Check if a request is already existed
  private void existingRequest(TMUberService req)
  {
    // Fill in the code
    for (int i = 0; i < queues.length; i++) 
    {
      for (TMUberService service : queues[i] ) 
      {
        if (service.equals(req)) 
        {
          throw new ExistedRequestException("Request Already Existed");
        }
      }
    }
  }

  // Calculate the cost of a ride or of a delivery based on distance 
  private double getDeliveryCost(int distance)
  {
    return distance * DELIVERYRATE;
  }

  private double getRideCost(int distance)
  {
    return distance * RIDERATE;
  }

  // Go through all drivers and see if one is available
  // Choose the first available driver
  // Return null if no available driver
  private Driver getAvailableDriver()
  {
    // Fill in the code
    for (Driver driver : drivers) 
    {
      if (driver.getStatus() == Driver.Status.AVAILABLE) 
      {
        return driver;
      }
    }
    return null ;
  }

  // Print Information (printInfo()) about all registered users in the system
  public void listAllUsers()
  {
    System.out.println();
    int index = 0; 
    
    for (User user : userList) 
    {
      index ++;
      System.out.printf("%-2s. ", index);
      user.printInfo();
      System.out.println(); 
    }
  }

  // Print Information (printInfo()) about all registered drivers in the system
  public void listAllDrivers()
  {
    // Fill in the code
    System.out.println();
    for (int i = 0; i < drivers.size(); i++)
    {
      int index = i + 1;
      System.out.printf("%-2s. ", index);
      drivers.get(i).printInfo();
      System.out.println();
    }
  }

  // Print Information (printInfo()) about all current service requests
  public void listAllServiceRequests()
  {
    // Fill in the code
    System.out.println();
    String dashes = "---------------------------------------------------------------------";
    int index = 0;
    for (int i = 0; i < queues.length; i++) {
      index = 0;
      System.out.println("ZONE " + Integer.toString(i));
      System.out.println("======" + "\n");

      for (TMUberService service : queues[i]) {
        index ++;
        System.out.printf("%-2s. %s", index, dashes);
        service.printInfo();
        System.out.println();
        System.out.println();
      }
    }
  }

  // Add a new user to the system
  public void registerNewUser(String name, String address, double wallet)
  {
    // Fill in the code. Before creating a new user, check paramters for validity
    // See the assignment document for list of possible erros that might apply

    if(name.equals("") || name == null){
      throw new InvalidNameException("Invalid User Name");
    }

    if(!CityMap.validAddress(address)){
      throw new InvalidAddressException("Invalid User Address");
    }

    if(wallet < 0){
      throw new InsufficientFundsException("Invalid Money in Wallet");
    }

    User newUser = new User(TMUberRegistered.generateUserAccountId(userList), name, address, wallet);
    userExists(newUser);            
    userList.add(newUser); 
    users.put(newUser.getAccountId(),newUser);
  }

  // Add a new driver to the system
  public void registerNewDriver(String name, String carModel, String carLicencePlate, String address)
  {
    // Fill in the code - see the assignment document for error conditions
    // that might apply. See comments above in registerNewUser
    if(name.equals("") || name == null){
      throw new InvalidNameException("Invalid Driver Name");
    }
    if(carModel.equals("") || carModel == null){
      throw new InvalidCarModelException("Invalid Car Model");
    }
    if(carLicencePlate.equals("") || carLicencePlate == null){
      throw new InvalidLicensePlateException("Invalid Car Licence Plate");
    }
    // check driver's input address
    if(!CityMap.validAddress(address)){
      throw new InvalidAddressException("Invalid Address");
    }
    Driver newDriver = new Driver(TMUberRegistered.generateDriverId(drivers), name, carModel, carLicencePlate, address); 
    driverExists(newDriver);       
    drivers.add(newDriver);
  }

  // Request a ride. User wallet will be reduced when drop off happens
  public void requestRide(String accountId, String from, String to)
  {
    // Check for valid parameters
	  // Use the account id to find the user object in the list of users
    // Get the distance for this ride
    // Note: distance must be > 1 city block!
    // Find an available driver
    // Create the TMUberRide object
    // Check if existing ride request for this user - only one ride request per user at a time!
    // Change driver status
    // Add the ride request to the list of requests
    // Increment the number of rides for this user
    if(accountId.equals("") || accountId == null || !users.containsKey(accountId) ){
      throw new UserNotFoundException("User Account Not Found");
    }

    if(!CityMap.validAddress(from) || !CityMap.validAddress(to)){
      throw new InvalidAddressException("Invalid Address");                                      
    }

    int distance = CityMap.getDistance(from, to);      
    if(distance <= 1){
      throw new InsufficientTravelDistanceException("Insufficient Travel Distance");
    }   

    User rideUser = getUser(accountId);
    double cost = getRideCost(distance);

    if(rideUser.getWallet() < cost) {
      throw new InsufficientFundsException("Insufficient Funds");
    }

    Driver availableDriver = getAvailableDriver();
    if(availableDriver == null) {
      throw new NoAvailableDriverException("No Drivers Available");
    }

    TMUberRide ride = new TMUberRide(from, to, rideUser , distance, cost);
    int cityZone = CityMap.getCityZone(from);
    TMUberService newRide = (TMUberService) ride;

    existingRequest(newRide); 
    
    // Add ride to appropriate queue
    Queue<TMUberService> currentQueue = queues[cityZone];
    currentQueue.add(newRide);
    rideUser.addRide();
    System.out.printf("RIDE for: %-5s  From: %-5s  To: %-5s", rideUser.getName(), from, to);
    //availableDriver.setStatus(Driver.Status.DRIVING); 
    // return true;
  }

  // Request a food delivery. User wallet will be reduced when drop off happens
  public void requestDelivery(String accountId, String from, String to, String restaurant, String foodOrderId)
  {
    // See the comments above and use them as a guide
    // For deliveries, an existing delivery has the same user, restaurant and food order id
    // Increment the number of deliveries the user has had
    if(accountId.equals("") || accountId == null || !users.containsKey(accountId)){
      throw new UserNotFoundException("User Account Not Found");
    } 

    if(!CityMap.validAddress(from) || !CityMap.validAddress(to)){
      throw new InvalidAddressException("Invalid Address");                                      
    }

    int distance = CityMap.getDistance(from, to);      
    if(distance <= 1){
      throw new InsufficientTravelDistanceException("Insufficient Travel Distance");
    }     

    User theUser = getUser(accountId);
    double cost = getDeliveryCost(distance);

    if(theUser.getWallet() < cost) {
      throw new InsufficientFundsException("Insufficient Funds");
    }

    Driver availableDriver = getAvailableDriver();

    if(availableDriver == null) {
      throw new NoAvailableDriverException("No Drivers Available");
    }

    TMUberDelivery delivery = new TMUberDelivery(from, to, theUser , distance, cost, restaurant, foodOrderId);
    int cityZone = CityMap.getCityZone(from);
    TMUberService theDelivery = (TMUberService) delivery;

    existingRequest(theDelivery); 
  
    Queue<TMUberService> currentQueue = queues[cityZone];
    currentQueue.add(theDelivery);
    theUser.addDelivery();
    System.out.printf("DELIVERY for: %-5s  From: %-5s  To: %-5s", theUser.getName(), from, to);
  }

  // Cancel an existing service request. 
  // parameter int request is the index in the serviceRequests array list
  public void cancelServiceRequest(int zone, int requestNum)
  {
    // Check if valid request #
    // Remove request from list
    // Also decrement number of rides or number of deliveries for this user
    // since this ride/delivery wasn't completed

    int actualRequestNum = requestNum - 1;                              
    if(zone < 0 || zone > 3) {
      throw new InvalidZoneException("Invalid Zone #");
    }

    Queue<TMUberService> currentQueue = queues[zone];
    if (currentQueue.isEmpty()) {
      throw new EmptyQueueException("No Service Request in Zone " + zone);
    }

    if(actualRequestNum >= currentQueue.size() || actualRequestNum < 0){
      throw new InvalidRequestNumException("Invalid Request #");
    }
  
    int count = 0; 
    Iterator<TMUberService> iter = currentQueue.iterator();

    while (iter.hasNext()) {
      TMUberService request = iter.next(); 
      if (count == actualRequestNum) {
        if (request.getServiceType().equals("DELIVERY")) {
          request.getUser().removeDelivery();
        } else if (request.getServiceType().equals("RIDE")) {
          request.getUser().removeRide();
        }
        iter.remove();
        break;
      }
      count++;
    }
  }
  
  // Drop off a ride or a delivery. This completes a service.
  // parameter request is the index in the serviceRequests array list
  public void dropOff(String driverId)
  {
    // See above method for guidance
    // Get the cost for the service and add to total revenues
    // Pay the driver
    // Deduct driver fee from total revenues
    // Change driver status
    // Deduct cost of service from user

    // Get driver given driver id
    Driver driver = getDriver(driverId);

    // Check if driver exists in system
    if (driver == null) {
      throw new DriverNotFoundException("Driver Not Found");
    }

    // check if driver status is driving
    if (driver.getStatus() != Driver.Status.DRIVING) {      
      throw new NotDrivingException("Driver Is Not Driving");                     
    }                                                                            

    // check if driver is doing a request
    if (driver.getService() == null) {
      throw new NullServiceException("Driver Has No Request Available To Drop Off");
    }

    TMUberService service = driver.getService();
    String toAddress = service.getTo();

    User user = service.getUser();
    double serviceCost = service.getCost();
    totalRevenue += serviceCost;
    double driverFee = PAYRATE*serviceCost;
    driver.pay(driverFee);
    totalRevenue -= driverFee;
    user.payForService(serviceCost);

    // if the request is ride/delivery, decrement rides/deliveries number from user
    if (service instanceof TMUberRide) {
      user.removeRide();
    }
    if (service instanceof TMUberDelivery) {
      user.removeDelivery();
    }

    // Set driver's service back to null
    driver.setService(null);
    // Set driver's status back to AVAILABLE
    driver.setStatus(Driver.Status.AVAILABLE);
    // Set driver's address to "To Address" 
    driver.setAddress(toAddress);
    // Update driver's zone
    driver.setZone(CityMap.getCityZone(toAddress));
  }

  // Sort users by name
  // Then list all users
  public void sortByUserName()
  {
    Collections.sort(userList, new NameComparator());
    listAllUsers();
  }

  // Helper class for method sortByUserName
  private class NameComparator implements Comparator<User>
  {
    public int compare(User user1, User user2){
      return user1.getName().compareTo(user2.getName());
    }
  }

  // Sort users by number amount in wallet
  // Then list all users
  public void sortByWallet()
  {
    Collections.sort(userList, new UserWalletComparator());
    listAllUsers();
  }

  // Helper class for use by sortByWallet
  private class UserWalletComparator implements Comparator<User>
  {
    public int compare(User user1, User user2) {
      if(user1.getWallet() < user2.getWallet()) {
        return -1;
      }else if (user1.getWallet() > user2.getWallet()) {
        return 1;
      }
      return 0;
    }                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                
  }

  public void pickup(String driverId)
  {
    Driver driver = getDriver(driverId);

    if (driver == null) {
      throw new DriverNotFoundException("Driver Not Found");
    }

    int currentZone = driver.getZone();
    Queue<TMUberService> currentQueue = queues[currentZone];
    
    if (currentQueue.isEmpty()) {
      throw new EmptyQueueException("No Service Request in Zone " + currentZone);
    }

    TMUberService service = currentQueue.remove();
    System.out.println("Driver " + driverId + " Picking Up in Zone " + currentZone);
    driver.setService(service);
    driver.setStatus(Driver.Status.DRIVING);
    driver.setAddress(service.getFrom());
  }


  public void driveTo(String driveId, String address)
  {
    Driver driver = getDriver(driveId);

    // Check if driver exists
    if (driver == null) {
      throw new DriverNotFoundException("Driver Not Found");
    }

    // Check if driver is available
    if (driver.getStatus() != Driver.Status.AVAILABLE) {
      throw new DriverNotAvailableException("Driver Is Driving");
    }

    // Check valid address
    if (!CityMap.validAddress(address)) {
      throw new InvalidAddressException("Invalid Address");
    }
      
    // Change driver's address to given address
    driver.setAddress(address);
    int zone = CityMap.getCityZone(address);

    // Change zone according to given address
    driver.setZone(zone);
    System.out.println("Driver " + driveId + " Now in Zone 3");
    
  }

  public void setUsers(ArrayList<User> userList) 
  {
    Map<String, User> userMap = new TreeMap<>();
    for (User user : userList) {
      userMap.put(user.getAccountId(), user);
    }
    this.users = userMap; 
    
  }

  public void setDrivers(ArrayList<Driver> drivers) 
  {
    this.drivers = drivers;
  }

}

/* Custom Exception Classes
 * 
 */
class EmptyQueueException extends NoSuchElementException
{
  public EmptyQueueException() {}
  public EmptyQueueException(String message) {
    super(message);
  }
}

class DriverNotFoundException extends RuntimeException
{
  public DriverNotFoundException() {}
  public DriverNotFoundException(String message) 
  {
    super(message);
  }
}

class DriverNotAvailableException extends RuntimeException 
{
  public DriverNotAvailableException() {}
  public DriverNotAvailableException(String message) 
  {
    super(message);
  }
}

class InvalidNameException extends RuntimeException
{
  public InvalidNameException () {}
  public InvalidNameException(String message) {
    super(message);
  }
}

class UserNotFoundException extends RuntimeException
{
  public UserNotFoundException() {}
  public UserNotFoundException(String message) 
  {
    super(message);
  }
}

class DuplicateException extends RuntimeException
{
  public DuplicateException() {}
  public DuplicateException(String message) 
  {
    super(message);
  }
}
class InvalidAddressException extends RuntimeException
{
  public InvalidAddressException() {}
  public InvalidAddressException(String message) 
  {
    super(message);
  }
}

class InvalidZoneException extends RuntimeException
{
  public InvalidZoneException() {}
  public InvalidZoneException(String message) {
    super(message);
  }
}

class InsufficientFundsException extends RuntimeException 
{
  public InsufficientFundsException() {}
  public InsufficientFundsException(String message) 
  {
    super(message);
  } 
}

class InsufficientTravelDistanceException extends RuntimeException 
{
  public InsufficientTravelDistanceException() {}
  public InsufficientTravelDistanceException(String message) 
  {
    super(message);
  } 
}

class InvalidRequestNumException extends RuntimeException
{
  public InvalidRequestNumException() {}
  public InvalidRequestNumException(String message) 
  {
    super(message);
  }
}

class NoAvailableDriverException extends RuntimeException 
{
  public NoAvailableDriverException() {}
  public NoAvailableDriverException(String message) 
  {
    super(message);
  } 
}

class NullServiceException extends RuntimeException
{
  public NullServiceException () {}
  public NullServiceException (String message) {
    super(message);
  }
}
class NotDrivingException extends RuntimeException 
{
  public NotDrivingException () {}
  public NotDrivingException (String message) {
    super(message);
  }
}

class ExistedRequestException extends RuntimeException 
{
  public ExistedRequestException() {}
  public ExistedRequestException(String message) 
  {
    super(message);
  } 
}

class InvalidCarModelException extends RuntimeException
{
  public InvalidCarModelException() {}
  public InvalidCarModelException(String message) 
  {
    super(message);
  }
}

class InvalidLicensePlateException extends RuntimeException
{
  public InvalidLicensePlateException () {}
  public InvalidLicensePlateException (String message) 
  {
    super(message);
  }
}
