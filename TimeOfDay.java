import java.text.DecimalFormat;

/* An instance of this Java class represents a time of day.
** Examples include 9:45AM and 11:23PM (expressed in 12-hour mode)
** and 17:23 (expressed in 24-hour mode).  
**
** By: Alex Thoennes
*/

public class TimeOfDay {

   // named constants
   // ---------------

   private static final int HOURS_PER_DAY = 24;
   private static final int HOURS_PER_HALF_DAY = HOURS_PER_DAY / 2;
   private static final int MINUTES_PER_HOUR = 60;
   private static final int MINUTES_PER_DAY = MINUTES_PER_HOUR * HOURS_PER_DAY;
   
   public static final String AM_DESIGNATION = "AM";  //Public constant
   public static final String PM_DESIGNATION = "PM";  //Public constant

   private static final DecimalFormat TWO_DIGITS = new DecimalFormat("00");

   // instance variables
   // ------------------

   private int minutesSinceMidnight;   // number of minutes since beginning of day
   private boolean mode24Hour;         // true if in 24-hour mode, false if in 12-hour mode

// -------------------------------------------------------------------------------------
// C o n s t r u c t o r    M e t h o d s
// -------------------------------------------------------------------------------------

   /* Initializes this object's value to midnight and sets its mode to
   ** 24-hour or 12-hour, respectively, in accord with whether the 
   ** argument's value is true or false, respectively.
   */
   public TimeOfDay(boolean inMode24) {
      mode24Hour = inMode24;
      minutesSinceMidnight = 0;
   }

   /* Initializes this object's value to midnight and its mode to 24-hour.
   */
   public TimeOfDay() 
   { 
	   this(true); // RWM: Couldn't resist!
   }   

// -------------------------------------------------------------------------------------
// O b s e r v e r   M e t h o d s
// -------------------------------------------------------------------------------------

   /* Returns the hour value associated to this time of day.
   ** If 24-hour mode is in effect, the result will be the number of full
   ** hours that have passed since midnight.  Otherwise, the result will
   ** be in the range 1..12 and correspond to the hour in either the AM
   ** or PM. Note: Normally used in conjunction with getMinuteOfDay().
   */
   public int getHourOfDay() { 
      int result = 0;
      
      if (isInMode24Hour())
      {
         result = minutesSinceMidnight / 60;
      }
      else if (!isInMode24Hour())
      {
         if ( getAMPMDesignation().equals(AM_DESIGNATION))
         {
            result = minutesSinceMidnight / 60;
         }
         else if (getAMPMDesignation().equals(PM_DESIGNATION))
         {
            result = (minutesSinceMidnight / 60) - 12;
         }
      }
      return result;
   }

   /* Returns the minute value associated to this time of day, which is the
   ** number of minutes that have passed since the current hour of the day 
   ** began.  Note: Normally used in conjunction with getHourOfDay().
   */
   public int getMinuteOfHour() { 
      int result = 0;
      result = minutesSinceMidnight % MINUTES_PER_HOUR;
      return result;
   }

   /* Returns the empty string ("") if this time of day is in 24-hour mode;
   ** otherwise returns either "AM" or "PM" according to the current hour 
   ** of the day.
   */
   public String getAMPMDesignation() {
      String result = "";
      if (isInMode24Hour())
      {
         result = "";
      }
      else if (!isInMode24Hour())
      {
         if ((minutesSinceMidnight / MINUTES_PER_HOUR) < HOURS_PER_HALF_DAY)
         {
            result = AM_DESIGNATION;
         }
         else
         {
            result = PM_DESIGNATION;
         }
      }
      return result;
   }

   /* Returns true if this time of day is in 24-hour mode, and false if 
   ** it is in 12-hour mode. 
   */
   public boolean isInMode24Hour() {
      return mode24Hour; 
   }

   /* Returns a string describing this time of day, formatted in a way
   ** consistent with its hour-mode.  For example, if the time represented
   ** is 2:15PM, the returned string will be "02:15PM" if this time is in
   ** 12-hour mode but will be "14:15" if in 24-hour mode.
   */
   public String toString() { 
      String result = "??:??";
      result = TWO_DIGITS.format(getHourOfDay()) + ":" + TWO_DIGITS.format(getMinuteOfHour()) + getAMPMDesignation();
      return result;
   }

   /* Returns by how many minutes the specified time of day would have to be
   ** changed in order to become equal to this time of day.  For example,
   ** if the specified time were 14:15 and this time were 17:33, the result
   ** would be 198.  If the two values were reversed, the result would be -198.
   */
   public int compareTo(TimeOfDay time) { 
	   int result = 0;
      result = minutesSinceMidnight - time.minutesSinceMidnight;
      return result;
   }

// -------------------------------------------------------------------------------------
// M u t a t o r   M e t h o d s
// -------------------------------------------------------------------------------------

   /* Sets this time's hour to that specified, interpreted as a 24-hour
   ** mode hour value.
   */
   public void setHour(int hour) { 
      if (hour < 0  ||  hour >= HOURS_PER_DAY) {
         throw new IllegalArgumentException("hour = " + hour + " out of range");
      } 
      else {
         minutesSinceMidnight = hour * MINUTES_PER_HOUR;
      }
   }

   /* Sets this time's hour to that specified, interpreted as a 12-hour
   ** mode hour value in the AM.
   */
   public void setHourAM(int hour) { 
      if (hour < 1  ||  hour > HOURS_PER_HALF_DAY) {
         throw new IllegalArgumentException("hour = " + hour + " out of range");
      }
      else {  
         minutesSinceMidnight = hour * MINUTES_PER_HOUR;
      }
   }

   /* Sets this time's hour to that specified, interpreted as a 12-hour
   ** mode hour value in the PM.
   */
   public void setHourPM(int hour) { 
      if (hour < 1  ||  hour > HOURS_PER_HALF_DAY) {
         throw new IllegalArgumentException("hour = " + hour + " out of range");
      }
      else {
         minutesSinceMidnight = getMinuteOfHour() + ((hour * MINUTES_PER_HOUR) + (MINUTES_PER_HOUR * 12));
      }
   }

   /* Sets this time's minute-after-the-hour to that specified.
   */
   public void setMinuteOfHour(int minute) {
      if (minute < 0  ||  minute >= MINUTES_PER_HOUR) {
         throw new IllegalArgumentException("minute = " + minute + " out of range");
      }
      else {
         minutesSinceMidnight = (minutesSinceMidnight - getMinuteOfHour()) + minute;
      }
   }

   /* Toggles this time's mode between 24-hour mode and 12-hour mode.
   */
   public void toggleHourMode() { 
      mode24Hour = !mode24Hour; 
   }

   /* Changes this time by the specified number of hours.  A positive value
   ** indicates a forward movement in time (into the future).  A negative
   ** value indicates a backward movement in time (into the past).
   ** Example: If this time of day were 15:20 and it was to be changed by 17
   ** hours, its final value should be 8:20 (which wraps around to the next
   ** day).  If, instead, it were to be changed by -17 hours, its final value
   ** should be 22:20 (which wraps around to the previous day).
   */
   public void changeHourBy(int hoursDelta) {
      minutesSinceMidnight = minutesSinceMidnight + (hoursDelta * MINUTES_PER_HOUR);
      if (minutesSinceMidnight > MINUTES_PER_DAY)
      {
         minutesSinceMidnight = minutesSinceMidnight - MINUTES_PER_DAY;
      }
   }

   /* Changes this time by the specified number of minutes.  A positive value
   ** indicates a forward movement in time (into the future).  A negative
   ** value indicates a backward movement in time (into the past).
   ** Example: If this time of day were 15:20 and it was to be changed by 137
   ** minutes, its final value should be 17:37.  If, instead, it were to be
   ** changed by -137 minutes, its final value  should be 13:03.
   */
   public void changeMinuteBy(int minutesDelta) {
      minutesSinceMidnight = minutesSinceMidnight + (minutesDelta + MINUTES_PER_HOUR);
      if (minutesSinceMidnight >= MINUTES_PER_HOUR)
      {
         minutesSinceMidnight = minutesSinceMidnight - MINUTES_PER_HOUR;
      }
   }

// ------------------------------------------------------------------------------
// P r i v a t e   M e t h o d s 
// ------------------------------------------------------------------------------
}
