package leapmidi;

import com.leapmotion.leap.Frame;

import java.util.Arrays;

/**
 * ControlFactory
 */
public class ControlFactory
{
   public static ControlView makeMinMaxControl(String name, int min, int minInit, int maxInit, int max,
                                               final ValueExtractor valueExtractor)
   {
      final OptionView minOptionView = OptionViewFactory.makeSliderOption(min, minInit, max, "Min");
      final OptionView maxOptionView = OptionViewFactory.makeSliderOption(min, maxInit, max, "Max");

      Transform minMaxTransform = new Transform()
      {
         @Override
         public int getValue(Frame frame)
         {
            int min = minOptionView.getOption().getValue();
            int max = maxOptionView.getOption().getValue();
            // Get y-coordinate of first finger we see
            if (frame.fingers().isEmpty())
               return -1;
            else {
               int pos = valueExtractor.valueFromFrame(frame);
               if (max == min)
                  return -1;
               else
                  return (127 * (pos - min)) / (max - min);
            }
         }
      };
      Control minMaxControl = new Control(name, null, minMaxTransform);
      ControlView minMaxControlView = new ControlView(minMaxControl);
      minMaxControlView.setOptionViews(Arrays.asList(minOptionView, maxOptionView));
      return minMaxControlView;
   }
}
