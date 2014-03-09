package leapmidi;

import javax.swing.*;

/**
 * An Option encapulates an integer
 */
public class Option
{
   private int value;

   public Option(int initialValue)
   {
      this.value = initialValue;
   }

   public Option()
   {
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
