package leapmidi;

import javax.swing.*;

/**
 * OptionView
 */
public abstract class OptionView
{
   private String name;

   public abstract void fillPanel(JPanel panel);

   public String getName()
   {
      return name;
   }
}

