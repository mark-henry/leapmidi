package leapmidi;

import com.sun.javaws.exceptions.InvalidArgumentException;

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
   public static final int BOOLEAN_FALSE = 0;
   public static final int BOOLEAN_TRUE = 1;

   // A numerical Option inside of a slider View
   public static OptionView makeSliderOption(final int min, final int initial, final int max, String name)
   {
      final Option intOption = new Option(initial);

      return new OptionView(intOption, name)
      {
         JSlider slider = new JSlider(min, max, initial);
         JLabel label = new JLabel(getName());

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
            slider.setValue(this.getOption().getValue());
            label.setText(this.getName());

            panel.removeAll();
            panel.setLayout(new BorderLayout(5, 5));
            panel.add(label, BorderLayout.WEST);
            panel.add(slider, BorderLayout.CENTER);
         }
      };
   }

   public static OptionView makeBooleanOption(final int initial, String name)
   {
      final Option boolOption = new Option(initial);

      if (initial != BOOLEAN_FALSE && initial != BOOLEAN_TRUE)
         throw new IllegalArgumentException("Initial value for boolean option must be one of " +
            "OptionViewFactory.BOOLEAN_TRUE or BOOLEAN_FALSE");

      return new OptionView(boolOption, name)
      {
         JCheckBox checkBox = new JCheckBox(getName(), initial == BOOLEAN_TRUE);

         {
            checkBox.addChangeListener(new ChangeListener()
            {
               @Override
               public void stateChanged(ChangeEvent e)
               {
                  if (checkBox.isSelected())
                     boolOption.setValue(BOOLEAN_TRUE);
                  else
                     boolOption.setValue(BOOLEAN_FALSE);
               }
            });
         }
         @Override
         public void fillPanel(JPanel panel)
         {
            checkBox.setText(this.getName());
            panel.removeAll();
            panel.setLayout(new BorderLayout());
            panel.add(checkBox, BorderLayout.CENTER);
         }
      };
   }
}
