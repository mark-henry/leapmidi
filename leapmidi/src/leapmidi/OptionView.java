package leapmidi;

import javax.swing.*;

/**
 * OptionView
 */
public abstract class OptionView
{
   private String name;
   private Option option;

   public OptionView(Option option)
   {
      this.option = option;
   }
   public abstract void fillPanel(JPanel panel);

   public String getName()
   {
      return name;
   }

   public Option getOption() { return option; }
}

