//Name: Chloe Ngo
//ID  : 501205941

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class TMUberRegistered
{
    // These variables are used to generate user account and driver ids
    private static int firstUserAccountID = 900;
    private static int firstDriverId = 700;

    // Generate a new user account id
    public static String generateUserAccountId(ArrayList<User> current)
    {
        return "" + firstUserAccountID + current.size();
    }

    // Generate a new driver id
    public static String generateDriverId(ArrayList<Driver> current)
    {
        return "" + firstDriverId + current.size();
    }

    // Database of Preregistered users
    // In Assignment 2 these will be loaded from a file
    // The test scripts and test outputs included with the skeleton code use these
    // users and drivers below. You may want to work with these to test your code (i.e. check your output with the
    // sample output provided). 
    public static ArrayList<User> loadPreregisteredUsers(String filename) throws FileNotFoundException, IOException
    {
        ArrayList<User> userslist = new ArrayList<>();
        Scanner scanner = new Scanner(new File(filename));

        while (scanner.hasNextLine()) {
            String name = scanner.nextLine();
            String address = scanner.nextLine();
            String wallet = scanner.nextLine();
            userslist.add(new User(generateUserAccountId(userslist), name, address, Double.parseDouble(wallet)));
        }
        scanner.close();
        return userslist;
    }

    // Database of Preregistered users
    // In Assignment 2 these will be loaded from a file
    public static ArrayList<Driver> loadPreregisteredDrivers(String filename) throws FileNotFoundException, IOException
    {   
        ArrayList<Driver> drivers = new ArrayList<>();
        Scanner scanner = new Scanner(new File(filename));
        while (scanner.hasNextLine()) {
            String name = scanner.nextLine();
            String carModel = scanner.nextLine();
            String carLicense = scanner.nextLine();
            String address = scanner.nextLine();
            drivers.add(new Driver(generateDriverId(drivers), name, carModel, carLicense, address));
        }
        scanner.close();
        return drivers;
    }
}

