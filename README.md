**Phase 1**

**Overview:**

This TMUber program simulates a simple ride-sharing and delivery service like Uber App. 
It allows users to register as drivers or customers, request rides or food deliveries, view and cancel existing service requests, 
and manage users and drivers. The program provides a basic user interface for interacting with the TMUber system manager,
which handles the core functionality of the application.

User and Driver Registration:
- Validate all input data
- Users can register as drivers or customers by providing their name, address, and other required details.
- Drivers can register by providing their name, car model, and license plate information.
- Users and drivers cannot register if their records already exist in the system.

Requesting Rides:
- Validate all input data
- Users can request rides by specifying their account ID, pickup, and drop-off addresses.
- Only one ride request is allowed per user.
- Find the first available driver when a request is made.
- A minimum distance of more than one block is checked for a ride request.

Requesting Deliveries:
- Validate all input data
- Users can request food deliveries by providing restaurant and food order details.
- Users are prevented from making duplicate delivery requests.
- Find the first available driver when a request is made.
- A minimum distance of more than one block is checked for a delivery request.

Listing Drivers, Users, and Service Requests:
- Users can view all registered drivers and customers, as well as current ride and delivery requests.
- Functions for sorting by name, wallet, and distance work efficiently and display accurate output.

Cancelling Requests:
- Users can cancel their ride or delivery requests using the index in the request list.

Dropping Off:
- The request is removed from the request list after completion.
- Service costs are added to the total revenue.
- Drivers receive payment based on the payment rate, which is added to their wallets and the service cost is deducted from users' wallets 
after a service is completed.
- Driver statuses are updated accurately.

Revenues:
- Total revenue is updated and calculated correctly every time a service is completed.

Unit Testing:
- Unit tests for validating addresses and calculating distances function as expected.

All Java files were compiled successfully without encountering any errors, and the program's outputs matched the expected results as per test 
scripts. No discrepancies or issues were found during the testing phase.

------------------------------------------------------------------------------------------------------------------------------------------
**Phase 2**

**Overview:**
Additional functionality and modifications are incorporated into assignment 1 to more accurately emulate a real sharing system like Uber app.

1. City Map: successfully implemented new method int GetCityZone(String address) that takes a valid address and returns zone number
accordingly

2. TMUberUI: successfully implemented 4 new actions and modified existing actions. All exceptions were also handled correctly using try{} catch{} blocks
    - PICKUP: effectively calls pickup() method in TMUberSystemManager.

    - LOADUSERS: effectively calls loadPreregisteredUsers() and setUser(ArrayList<User>)
    loadPreregisteredUsers(String filename) correctly reads information from a file and returns an array list of user objects
    setUser(ArrayList<User>) correctly creates a Map<String, User> using that given array list 

    - LOADDRIVERS: Same as LOADUSERS, successfully calls loadPreregisteredDrivers(String filename)
    setDriver(ArrayList<Driver>) correctly uses given array list to set the drivers instance variable

    - DRIVETO: effectively calls driveTo() in TMUberSystemManager using given driver id and address

    - REGDRIVER: successfully modified with additional parameter address
    - CANCELREQ: takes zone number and request number
    - DROPOFF: takes driver id instead of request number. The service request that driver is doing will be dropped off as driver now has 
    reference to the service request
    - REQUESTS: visibly shows zone and request number

3. Driver: 
    - successfully created instance variable referring to TMUberService object in Driver class
    - successfully created instance variable address
    - successfully created instance variable zone to keep track of driver's current zone

4. TMUberService: 
    - successfully removed reference to the Driver object in TMUberService and its subclasses TMUberRide, TMUberDelivery
    
5. TMUberSystemManager
    - Map<String, User> users was created using TreeMap. The map maps String userid key to a User object value
    - array list of TMUberService objects was correctly replaced with an array of 4 queues. Each queue represents each zone contains
    TMUberService objects

    - errMsg was replaced, boolean type methods in TMUberSystemManager was switched to void type. 
    Exceptions are thrown in these methods if occur

    - Validity of parameters of these methods are checked using exceptions as well

    - void pickup(String driverId) was successfully created and called when PICKUP command in TMUberUI is entered. Chosen driver's zone
    is retrieved. Queue is chosen according to driver's zone, first request of this queue will be removed. Driver's instance variable
    service will refer to this service request. Then driver's status set to DRIVING. Driver's address set to FROM address of this request
    (Exception is checked in case queue is empty)

    - void driveTo(String driverId, String address) correctly operated. Driver's availability and address is checked.
    Driver's address will then change to given address, zone is also updated based on given address

    -cancelServiceRequest(int zone, int requestNum) method was modified. It takes zone and number request to cancel a request off 
    a appropriate queue. iteration() is used to fine request number and remove.

6. TMUberRegistered
    - loadPreregisteredUsers() and loadPreregisteredDrivers() correctly modified to read input files and load users/drivers accordingly
    It loads 3 lines for each user's record: name, address, wallet and loads 4 lines for driver's record: name, car model, car license
    and address 
    - These 2 methods do not handle any exceptions, "throws FileNotFoundException, IOException" is used to tell compiler these 2 exceptions
    will be handled separately in TMUberUI

7. Sorting functionality: work as usual
- sortByUserName(), sortByWallet() use array list to sort
- sortByDistance() was removed 

Custom Exception Classes in TMUberSystemManager to replace errMsg:
EmptyQueueException
DriverNotFoundException 
DriverNotAvailableException
InvalidNameException 
UserNotFoundException 
DuplicateException 
InvalidAddressException 
InvalidZoneException 
InsufficientFundsException 
InsufficientTravelDistanceException 
InvalidRequestNumException
NoAvailableDriverException 
NullServiceException 
NotDrivingException 
ExistedRequestException 
InvalidCarModelException 
InvalidLicensePlateException


All Java files compiled successfully without encountering any errors, and the program's outputs matched the expected results as shown
in the demo video. However, if register a new user/driver and then load preregistered users or drivers. The new registered user/driver won't be
saved. If register after loading preregistered users/drivers, the program works as expected. 
Other than that, no discrepancies or issues were found during the testing phase.













        
