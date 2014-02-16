package leapmidi;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SliderOptionView extends OptionView
{
   private JLabel label;
   private JSlider slider;
   private Option<Integer> option;

   public SliderOptionView(Option<Integer> option)
   {
      this.option = option;
      label = new JLabel("slideroptionview");
      slider = new JSlider(0,500);

      connectSliderToOption();
   }

   private void connectSliderToOption()
   {
      slider.addChangeListener(new ChangeListener()
      {
         @Override
         public void stateChanged(ChangeEvent e)
         {
            option.setValue(slider.getValue());
         }
      });
   }

   public void fillPanel(JPanel panel)
   {
      panel.add(label);
      panel.add(slider);
   }
}
