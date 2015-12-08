import java.util.Scanner;
import java.io.*;
import java.text.DecimalFormat;

public class TestTimeOfDay {
   static Scanner keyboard;
   
   static final char QUIT = 'Q';
   static final char DISPLAY = 'D';
   static final char SET_HOUR = 'H';
   static final char SET_HOUR_AM = 'A';
   static final char SET_HOUR_PM = 'P';
   static final char SET_MINUTE = 'M';
   static final char TOGGLE_HOUR_MODE = 'T';
   static final char CHANGE_HOUR_BY= 'h';
   static final char CHANGE_MINUTE_BY = 'm';
   
   static boolean interactive;
   
   public static void main(String[] args) throws FileNotFoundException {
   
      String prompt = "Command:";
      // Recognize and handle run argument
      if(args.length > 0) {
         interactive = false;
         keyboard = new Scanner(new File("tc0.txt"));
      }  
      else {
         interactive = true;
         keyboard = new Scanner(System.in);
      }
      
      TimeOfDay dawn = new TimeOfDay();  dawn.setHourAM(6);  dawn.setMinuteOfHour(26);
      TimeOfDay dusk = new TimeOfDay(false);  dusk.setHourPM(5);  dusk.setMinuteOfHour(9);
      TimeOfDay X    = new TimeOfDay();
      
      System.out.println("Dawn is at " + dawn.toString());
      System.out.println("Dusk is at " + dusk.toString());
      
      // Process commands
      display(X,dawn,dusk);
      char command = '?';
      command = getCommand(prompt,interactive); 
      while(command != QUIT) {
         processCommand(X,command,dawn,dusk);
         display(X,dawn,dusk);
         command = getCommand(prompt,interactive);  
      }  
   }
   
   public static void display(TimeOfDay TOD, TimeOfDay dawn, TimeOfDay dusk) {
      DecimalFormat TWO_DIGITS = new DecimalFormat("00");
      String value1 = TOD.toString();
      System.out.print(value1);
      String value2 = TWO_DIGITS.format(TOD.getHourOfDay()) + ":" +
             TWO_DIGITS.format(TOD.getMinuteOfHour()) +
             TOD.getAMPMDesignation();
      if(!value2.equals(value1)) {
         System.out.print(" << !!! >> " + value2);
      }
      System.out.println(" {"+TOD.compareTo(dawn)+"} ["+TOD.compareTo(dusk)+"]");
   }
 
   public static void processCommand(TimeOfDay TOD, char command, TimeOfDay dawn, TimeOfDay dusk) {
      try {
         if(command == SET_HOUR) {
            TOD.setHour(getIntFromUser("Enter 24 hour:",interactive));
         } 
         else if(command == SET_HOUR_AM) {
            TOD.setHourAM(getIntFromUser("Enter AM hour:",interactive));
         } 
         else if(command == SET_HOUR_PM) {
            TOD.setHourPM(getIntFromUser("Enter PM hour:",interactive));
         } 
         else if(command == SET_MINUTE) {
            TOD.setMinuteOfHour(getIntFromUser("Enter minute:",interactive));
         } 
         else if(command == TOGGLE_HOUR_MODE) {
            TOD.toggleHourMode();
         } 
         else if(command == CHANGE_HOUR_BY) {
            TOD.changeHourBy(getIntFromUser("Enter hour change:",interactive));
         } 
         else if(command == CHANGE_MINUTE_BY) {
            TOD.changeMinuteBy(getIntFromUser("Enter minute change:",interactive));
         } 
         else if(command == DISPLAY) {
            display(TOD,dawn,dusk);
         } 
         else {
            System.out.println("--> undefined command\n" +
               "QUIT='Q', " +
               "DISPLAY='D', " +
               "SET_HOUR='H', " +
               "SET_HOUR_AM='A', " +
               "SET_HOUR_PM='P', " + "\n" +
               "SET_MINUTE='M', " +
               "TOGGLE_MODE24='T', " +
               "CHANGE_HOUR_BY= 'h', " +
               "CHANGE_MINUTE_BY = 'm'");
         }
     }
      catch (IllegalArgumentException e){
         System.out.println("==> CAUGHT IllegalArgumentException; no harm");
      }
   }
   
   private static char getCommand(String prompt, boolean interactive) {
      char result = getCharFromUser(prompt,interactive);
      while (result == ' ') {
         result = getCharFromUser("",interactive);
      }
      if(!interactive) { System.out.println(result); }
      return result;
   }
   
   /* Displays the specified string (i.e., 'prompt', which is intended to be
   ** a prompt to the user) and then reads and returns the user's response,
   ** which is expected to be a single char.  
   */
   private static char getCharFromUser(String prompt, boolean interactive) {
      System.out.print(prompt);       // Prompt user to enter input value
      String s = keyboard.nextLine() + ' ';
      return s.charAt(0);             // Return user's response
   }
   
   /* Displays the specified string (i.e., 'prompt', which is intended to be
   ** a prompt to the user) and then reads and returns the user's response,
   ** which is expected to be an int value.  
   */
   private static int getIntFromUser(String prompt, boolean interactive) {
      System.out.print(prompt);     // Prompt user to enter input value
      String input = keyboard.nextLine().trim();
      int result;
      try {
         result = Integer.parseInt(input);
      }
      catch (IllegalArgumentException e) {
         result = 0;;
      }
      if(!interactive) { System.out.println(result); }
      return result;
   }
}
