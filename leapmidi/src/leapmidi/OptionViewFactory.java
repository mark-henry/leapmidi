package leapmidi;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

/**
 * OptionViewFactory
 */
public class OptionViewFactory
{
   // A numerical Option inside of a slider View
   public static OptionView makeSliderOption(final int min, final int max, final int initial, String name)
   {
      final Option intOption = new Option(initial);

      OptionView view = new OptionView(intOption, name)
      {
         JSlider slider = new JSlider(min, max, initial);
         JLabel label = new JLabel();

         {
            slider.addChangeListener(new ChangeListener()
            {
               @Override
               public void stateChanged(ChangeEvent e)
               {
                  intOption.setValue(slider.getValue());
               }
            });
         }

         @Override
         public void fillPanel(JPanel panel)
         {
            panel.removeAll();
            panel.setLayout(new BorderLayout(5, 5));
            label.setText(getName());
            panel.add(label, BorderLayout.WEST);
            panel.add(slider, BorderLayout.CENTER);
         }
      };

      return view;
   }
}
