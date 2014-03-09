package leapmidi;

import javax.swing.*;

/**
 * An Option encapsulates an integer
 */
public class Option
{
   private int value;

   public Option(int initialValue)
   {
      this.value = initialValue;
   }

   public int getValue()
   {
      return value;
   }

   public void setValue(int value)
   {
      this.value = value;
   }
}
