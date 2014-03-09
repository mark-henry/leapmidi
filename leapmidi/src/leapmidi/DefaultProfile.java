package leapmidi;

import com.leapmotion.leap.Frame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * DefaultProfile
 */
public class DefaultProfile
{
   private static Profile defaultControls = null;

   public static Profile getDefaultControls()
   {
      if (DefaultProfile.defaultControls == null)
         DefaultProfile.defaultControls = constructDefaultControlsList();
      return DefaultProfile.defaultControls;
   }

   private static Profile constructDefaultControlsList()
   {
      List<ControlView> views = new ArrayList<ControlView>();

      final OptionView ymin = OptionViewFactory.makeSliderOption(100, 400, 100, "Min value");
      final OptionView ymax = OptionViewFactory.makeSliderOption(100, 400, 400, "Max value");

      Transform handYAxisTransform = new Transform()
      {
         @Override
         public int getValue(Frame frame)
         {
            int min = ymin.getOption().getValue();
            int max = ymax.getOption().getValue();
            // Get y-coordinate of first finger we see
            if (frame.fingers().isEmpty())
               return -1;
            else {
               int pos = (int)frame.fingers().leftmost().tipPosition().getY();
               return 127 * (pos - min) / (max - min);
            }
         }
      };
      Control handYAxisControl = new Control(new MIDIAddress(1, 1), "Hand Y Axis", handYAxisTransform);
      ControlView handYAxisControlView = new ControlView(handYAxisControl);
      handYAxisControlView.setOptionViews(new ArrayList<OptionView>(Arrays.asList(ymin, ymax)));

      views.add(handYAxisControlView);

      return new Profile(views);
   }
}
