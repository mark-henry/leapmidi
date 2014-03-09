package leapmidi;

import javax.swing.*;

/**
 * OptionViewFactory
 */
public class OptionViewFactory
{
   // A numerical Option inside of a slider View
   public static OptionView makeSliderOption(final int min, final int max, final int initial)
   {
      Option intOption = new Option(initial);

      OptionView view = new OptionView(intOption)
      {
         JSlider slider = new JSlider(min, max, initial);
         JLabel label = new JLabel(this.getName());

         @Override
         public void fillPanel(JPanel panel)
         {
            panel.add(slider);
         }
      };

      return view;
   }
}
