//Name: Chloe Ngo
//ID  : 501205941

import java.util.Arrays;
import java.util.Scanner;

// The city consists of a grid of 9 X 9 City Blocks

// Streets are east-west (1st street to 9th street)
// Avenues are north-south (1st avenue to 9th avenue)

// Example 1 of Interpreting an address:  "34 4th Street"
// A valid address *always* has 3 parts.
// Part 1: Street/Avenue residence numbers are always 2 digits (e.g. 34).
// Part 2: Must be 'n'th or 1st or 2nd or 3rd (e.g. where n => 1...9)
// Part 3: Must be "Street" or "Avenue" (case insensitive)

// Use the first digit of the residence number (e.g. 3 of the number 34) to determine the avenue.
// For distance calculation you need to identify the the specific city block - in this example 
// it is city block (3, 4) (3rd avenue and 4th street)

// Example 2 of Interpreting an address:  "51 7th Avenue"
// Use the first digit of the residence number (i.e. 5 of the number 51) to determine street.
// For distance calculation you need to identify the the specific city block - 
// in this example it is city block (7, 5) (7th avenue and 5th street)
//
// Distance in city blocks between (3, 4) and (7, 5) is then == 5 city blocks
// i.e. (7 - 3) + (5 - 4) 

public class CityMap 
{
  // Checks for string consisting of all digits
  // An easier solution would use String method matches()
  private static boolean allDigits(String s)
  {
    for (int i = 0; i < s.length(); i++)
      if (!Character.isDigit(s.charAt(i)))
        return false;
    return  true;
  }

  // Get all parts of address string
  // An easier solution would use String method split()
  // Other solutions are possible - you may replace this code if you wish
  private static String[] getParts(String address)
  {
    String parts[] = new String[3];
    
    if (address == null || address.length() == 0)
    {
      parts = new String[0];       
      return parts;
    }

    int numParts = 0;
    Scanner sc = new Scanner(address);

    while (sc.hasNext())
    {
      if (numParts >= 3)                                          
        parts = Arrays.copyOf(parts, parts.length+1);  

      parts[numParts] = sc.next();
      numParts++;
    }
    if (numParts == 1)
      parts = Arrays.copyOf(parts, 1);
    else if (numParts == 2)
      parts = Arrays.copyOf(parts, 2);
    return parts;
  }

  // Checks for a valid address
  public static boolean validAddress(String address)
  {
    // Fill in the code
    // Make use of the helper methods above if you wish
    // There are quite a few error conditions to check for 
    // e.g. number of parts != 3

    if (address == null || address.length() == 0){
      return false;
    }

    String[] addressParts = getParts(address);

    // check if address has 3 parts
    if(addressParts.length != 3) {
      return false;
    }

    String part0 = addressParts[0];
    String part1 = addressParts[1];
    String part2 = addressParts[2];

    // check if address part0 has length of 2
    if(part0.length() != 2) {
        return false;
    }

    // check if address part0 is a positive 2-digit number
    if(!allDigits(part0) || Integer.parseInt(part0) < 0) {
      return false;
    } 

    // check if first digit of 2-digit number is 0
    if(Integer.parseInt(part0.substring(0, 1)) == 0) {
      return false;
    }

    // check if address part1 has length of 3
    if(part1.length() != 3) {
        return false;
    }

    // if address part1 has length of 3
    if(part1.length() == 3) {
        int n = Integer.parseInt(part1.substring(0, 1));
        
        // then check if "n"th is from 1 to 9
        if(n < 1 || n > 9) {
          return false;
        }

        // check if residence number is "1st", "2nd", "3rd", "nth" (3<n<=9)
        if(!(part1.substring(1, 3).equals("th") && n > 3) && !part1.equals("1st") && !part1.equals("2nd") && !part1.equals("3rd")) {
          return false;
        }
    }

    // check if last part of address equals "Street" or "Avenue"
    if(!part2.equalsIgnoreCase("STREET") && !part2.equalsIgnoreCase("AVENUE")) {
      return false;
    }
    return true;
  }

  // Computes the city block coordinates from an address string
  // returns an int array of size 2. e.g. [3, 4] 
  // where 3 is the avenue and 4 the street
  // See comments at the top for a more detailed explanation
  public static int[] getCityBlock(String address)
  {
    int[] block = {-1, -1};
    String[] addressParts = getParts(address);
    // Fill in the code

    String part0 = addressParts[0];
    String part1 = addressParts[1];
    String part2 = addressParts[2];
    if(validAddress(address)) {

      if(part2.equalsIgnoreCase("STREET")) {
        int avenue = Integer.parseInt(part0.substring(0, 1));
        int street = Integer.parseInt(part1.substring(0, 1));
        block = new int[] {avenue, street};

      } else if(part2.equalsIgnoreCase("AVENUE")) {
        int street = Integer.parseInt(part0.substring(0, 1));
        int avenue = Integer.parseInt(part1.substring(0, 1));
        block = new int[] {avenue, street};
      }
    }
    return block;
  }
   
  // Calculates the distance in city blocks between the 'from' address and 'to' address
  // Hint: be careful not to generate negative distances
  
  // This skeleton version generates a random distance
  // If you do not want to attempt this method, you may use this default code
  public static int getDistance(String from, String to)
  {
    // Fill in the code or use this default code below. If you use
    // the default code then you are not eligible for any marks for this part
    int[] fromAddress = getCityBlock(from);
    int[] toAddress = getCityBlock(to);
    int distance = Math.abs(toAddress[0] - fromAddress[0]) + Math.abs(toAddress[1] - fromAddress[1]);   
    return distance;  
  }
  
  // A static method that returns the zone given a valid address. 
  public static int getCityZone(String address) 
  {
    int zone = 0;
    // return -1 if address is not valid
    if (!validAddress(address)) {
      return -1;
    }

    int[] cityBlock = getCityBlock(address);
    int avenue = cityBlock[0];
    int street = cityBlock[1];

    // Zone 0: extends from 1st avenue to 5th avenue and 6th to 9th street  
    if ((avenue >= 1 && avenue <= 5) && (street >= 6 && street <= 9)) {
      zone = 0;
    // Zone 1: extends from 6th avenue to 9th avenue and 6th to 9th street
    } else if ((avenue >= 6 && avenue <= 9) && (street >= 6 && street <= 9)) {
      zone = 1;
    // Zone 2: extends from 6th avenue to 9th avenue and 1st to 5th street
    } else if ((avenue >= 6 && avenue <= 9) && (street >= 1 && street <=5)) {
      zone = 2;
    // Zone 3: extends from 1st avenue to 5th avenue and 1st to 5th street
    } else if ((avenue >= 1 && avenue <= 5) && (street >= 1 && street <= 5)) {
      zone = 3;
    }
    return zone;
  }
}
