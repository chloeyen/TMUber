//Name: Chloe Ngo
//ID  : 501205941

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.StringTokenizer;

// Simulation of a Simple Command-line based Uber App 

// This system supports "ride sharing" service and a delivery service

public class TMUberUI
{
  public static void main(String[] args)
  {
    // Create the System Manager - the main system code is in here 

    TMUberSystemManager tmuber = new TMUberSystemManager();
    
    Scanner scanner = new Scanner(System.in);
    System.out.print(">");

    // Process keyboard actions
    while (scanner.hasNextLine())
    {
      String action = scanner.nextLine();

      if (action == null || action.equals("")) 
      {
        System.out.print("\n>");
        continue;
      }
      // Quit the App
      else if (action.equalsIgnoreCase("Q") || action.equalsIgnoreCase("QUIT"))
        return;
      // Print all the registered drivers
      else if (action.equalsIgnoreCase("DRIVERS"))  // List all drivers
      {
        tmuber.listAllDrivers(); 
      }
      // Print all the registered users
      else if (action.equalsIgnoreCase("USERS"))  // List all users
      {
        tmuber.listAllUsers(); 
      }
      // Print all current ride requests or delivery requests
      else if (action.equalsIgnoreCase("REQUESTS"))  // List all requests
      {
        tmuber.listAllServiceRequests(); 
      }
      // Register a new driver
      else if (action.equalsIgnoreCase("REGDRIVER")) 
      {
        String name = "";
        String carModel = "";
        String license = "";
        String address = "";

        try {
          System.out.print("Name: ");
          if (scanner.hasNextLine())
          {
            name = scanner.nextLine();
          }

          System.out.print("Car Model: ");
          if (scanner.hasNextLine())
          {
            carModel = scanner.nextLine();
          }

          System.out.print("Car License: ");
          if (scanner.hasNextLine())
          {
            license = scanner.nextLine();
          }

          System.out.print("Address: ");
          if (scanner.hasNextLine()) 
          {
            address = scanner.nextLine();
          }
          tmuber.registerNewDriver(name, carModel, license, address);
          System.out.printf("Driver: %-15s Car Model: %-15s License Plate: %-10s Address: %-15s", name, carModel, license, address);

        } catch (InvalidNameException e) {
          System.out.println(e.getMessage());
        } catch (InvalidCarModelException e) {
          System.out.println(e.getMessage());
        } catch (InvalidLicensePlateException e) {
          System.out.println(e.getMessage());
        } catch (InvalidAddressException e) {
          System.out.println(e.getMessage());
        } catch (DuplicateException e) {
          System.out.println(e.getMessage());
        }
      }

      // Register a new user
      else if (action.equalsIgnoreCase("REGUSER")) 
      {
        String name = "";
        String address = "";
        double wallet = 0.0;
        try {
          System.out.print("Name: ");
          if (scanner.hasNextLine())
          {
            name = scanner.nextLine();
          }

          System.out.print("Address: ");
          if (scanner.hasNextLine())
          {
            address = scanner.nextLine();
          }

          System.out.print("Wallet: ");
          if (scanner.hasNextDouble())
          {
            wallet = scanner.nextDouble();
            scanner.nextLine(); // consume nl!! Only needed when mixing strings and int/double
          }
          tmuber.registerNewUser(name, address, wallet);
          System.out.printf("User: %-15s Address: %-15s Wallet: %2.2f", name, address, wallet);

        } catch (InvalidNameException e) {
          System.out.println(e.getMessage());
        } catch (InvalidAddressException e) {
          System.out.println(e.getMessage());
        } catch (InsufficientFundsException e) {
          System.out.println(e.getMessage());
        } catch (DuplicateException e) {
          System.out.println(e.getMessage());
        }
      }


      // Request a ride
      else if (action.equalsIgnoreCase("REQRIDE")) 
      {
        // Get the following information from the user (on separate lines)
        // Then use the TMUberSystemManager requestRide() method properly to make a ride request
        // "User Account Id: "      (string)
        // "From Address: "         (string)
        // "To Address: "           (string)
        try {
          System.out.print("User Account Id:   ");
          String accountID = scanner.nextLine();
          System.out.print("From Address:      ");
          String fromAddress = scanner.nextLine();
          System.out.print("To Address:        ");
          String toAddress = scanner.nextLine();
          tmuber.requestRide(accountID, fromAddress, toAddress);

        } catch (UserNotFoundException e) {
          System.out.println(e.getMessage());
        } catch (InvalidAddressException e) {
          System.out.println(e.getMessage());
        } catch (InsufficientTravelDistanceException e) {
          System.out.println(e.getMessage());
        } catch (InsufficientFundsException e) {
          System.out.println(e.getMessage());
        } catch (NoAvailableDriverException e) {
          System.out.println(e.getMessage());
        } catch (ExistedRequestException e) {
          System.out.println(e.getMessage());
        }
      }

      // Request a food delivery
      else if (action.equalsIgnoreCase("REQDLVY")) 
      {
        // Get the following information from the user (on separate lines)
        // Then use the TMUberSystemManager requestDelivery() method properly to make a ride request
        // "User Account Id: "      (string)
        // "From Address: "         (string)
        // "To Address: "           (string)
        // "Restaurant: "           (string)
        // "Food Order #: "         (string)
        try {
          System.out.print("User Account Id:   ");
          String accountID = scanner.nextLine();
          System.out.print("From Address:      ");
          String fromAddress = scanner.nextLine();
          System.out.print("To Address:        ");
          String toAddress = scanner.nextLine();
          System.out.print("Restaurant:        ");
          String restaurant = scanner.nextLine();
          System.out.print("Food Order #:      ");
          String foodOrderNum = scanner.nextLine();
          tmuber.requestDelivery(accountID, fromAddress, toAddress, restaurant, foodOrderNum);

        } catch (UserNotFoundException e) {
          System.out.println(e.getMessage());
        } catch (InvalidAddressException e) {
          System.out.println(e.getMessage());
        } catch (InsufficientTravelDistanceException e) {
          System.out.println(e.getMessage());
        } catch (InsufficientFundsException e) {
          System.out.println(e.getMessage());
        } catch (NoAvailableDriverException e) {
          System.out.println(e.getMessage());
        } catch (ExistedRequestException e) {
          System.out.println(e.getMessage());
        }

      }

      // Sort users by name
      else if (action.equalsIgnoreCase("SORTBYNAME")) 
      {
        tmuber.sortByUserName();
      }
      // Sort users by number of ride they have had
      else if (action.equalsIgnoreCase("SORTBYWALLET")) 
      {
        tmuber.sortByWallet();
      }

      // Sort current service requests (ride or delivery) by distance
      // else if (action.equalsIgnoreCase("SORTBYDIST")) 
      // {
      //   tmuber.sortByDistance();
      // }

      // Cancel a current service (ride or delivery) request
      else if (action.equalsIgnoreCase("CANCELREQ")) 
      {
        int zone = -1;
        int request = -1;

        try {
          System.out.print("Request #: ");
          if (scanner.hasNextInt())
          {
            request = scanner.nextInt();
            scanner.nextLine(); // consume nl character
          }

          System.out.print("Zone #: ");
          if (scanner.hasNextInt()) 
          {
            zone = scanner.nextInt();
            scanner.nextLine(); 
          }
          tmuber.cancelServiceRequest(zone, request);
          System.out.println("Service request #" + request + " cancelled");

        } catch (InvalidRequestNumException e) {
          System.out.println(e.getMessage());
        } catch (InvalidZoneException e) {
          System.out.println(e.getMessage());
        } catch (EmptyQueueException e) {
          System.out.println(e.getMessage());
        }
      }

      // Drop-off the user or the food delivery to the destination address
      else if (action.equalsIgnoreCase("DROPOFF")) 
      {
        String driverId = "";
        try {
          System.out.print("Driver Id: ");
          if (scanner.hasNextLine())
          {
            driverId = scanner.nextLine();
          }
          tmuber.dropOff(driverId);      
          System.out.println("Driver " + driverId + " Dropping Off");
        } catch (DriverNotFoundException e) {
          System.out.println(e.getMessage());
        } catch (NotDrivingException e) {
          System.out.println(e.getMessage());
        } catch (NullServiceException e) {
          System.out.println(e.getMessage());
        }
      }
      
      // Get the Current Total Revenues
      else if (action.equalsIgnoreCase("REVENUES")) 
      {
        System.out.println("Total Revenue: " + tmuber.totalRevenue);
      }

      // Unit Test of Valid City Address 
      else if (action.equalsIgnoreCase("ADDR")) 
      {
        String address = "";
        System.out.print("Address: ");
        if (scanner.hasNextLine())
        {
          address = scanner.nextLine();
        }
        System.out.print(address);
        if (CityMap.validAddress(address))
          System.out.println("\nValid Address"); 
        else
          System.out.println("\nBad Address"); 
      }

      // Unit Test of CityMap Distance Method
      else if (action.equalsIgnoreCase("DIST")) 
      {
        String from = "";
        System.out.print("From: ");
        if (scanner.hasNextLine())
        {
          from = scanner.nextLine();
        }
        String to = "";
        System.out.print("To: ");
        if (scanner.hasNextLine())
        {
          to = scanner.nextLine();
        }
        System.out.print("\nFrom: " + from + " To: " + to);
        System.out.println("\nDistance: " + CityMap.getDistance(from, to) + " City Blocks");
      }


      // Pick up user or delivery order
      else if (action.equalsIgnoreCase("PICKUP")) 
      {
        String driverId = "";
        System.out.print("Driver Id: "); 
        try {
          if(scanner.hasNextLine()) 
          {
            driverId = scanner.nextLine();
          }
          tmuber.pickup(driverId);
        } catch (EmptyQueueException e) {
          System.out.println(e.getMessage());
        } catch (DriverNotFoundException e) {
          System.out.println(e.getMessage());
        }
      }

      // Driver move to specific zone given by address
      else if (action.equalsIgnoreCase("DRIVETO")) 
      {
        String driverId = "";
        System.out.print("Driver Id: ");
        try {
          if (scanner.hasNextLine()) 
          {
            driverId = scanner.nextLine();
          }

          String address = "";
          System.out.print("To Address: ");
          if (scanner.hasNextLine()) 
          {
            address = scanner.nextLine();
          }
          tmuber.driveTo(driverId, address);
        } catch (DriverNotFoundException e) {
          System.out.println(e.getMessage());
        } catch (DriverNotAvailableException e) {
          System.out.println(e.getMessage());
        } catch (InvalidAddressException e) {
          System.out.println(e.getMessage());
        }
      }
      
      // Load users 
      else if (action.equalsIgnoreCase("LOADUSERS"))
      {
        String filename = "";
        try {
          System.out.print("User File: ");
          if (scanner.hasNextLine()) 
          {
            filename = scanner.nextLine();
            ArrayList<User> users = TMUberRegistered.loadPreregisteredUsers(filename);
            tmuber.setUserList(users);           // update user list (Array List)
            tmuber.setUsers(users);                       // update users (Map)
            System.out.println("Users Loaded");
          }
          
        // If FileNotFoundException is thrown, user can try again
        } catch (FileNotFoundException e) {
          System.out.println("Users File: " + filename + " Not Found");
          
        // if any other IOException is thrown, exit entire program
        } catch (IOException e) {
          return;
        }
      }

      // Load drivers
      else if (action.equalsIgnoreCase("LOADDRIVERS"))
      {
        String filename = "";   
        try {
          System.out.print("User File: ");
          if (scanner.hasNextLine()) 
          {
            filename = scanner.nextLine();
            ArrayList<Driver> drivers = TMUberRegistered.loadPreregisteredDrivers(filename);
            tmuber.setDrivers(drivers);
            System.out.println("Drivers Loaded");
          }
        
        // If FileNotFoundException is thrown, user can try again
        } catch (FileNotFoundException e) {
          System.out.println("Drivers File " + filename + " Not Found");
   
        // if any other IOException is thrown, exit entire program
        } catch (IOException e) {
          return;
        }
      }
      System.out.print("\n>");
    }
  }
}

