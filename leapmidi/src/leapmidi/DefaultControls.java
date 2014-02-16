package leapmidi;

import com.leapmotion.leap.Frame;
import oracle.jrockit.jfr.Options;

import java.util.ArrayList;
import java.util.List;

/**
 * DefaultControls
 */
public class DefaultControls
{
   private static List<Control> defaultControls = null;

   public static List<Control> getDefaultControls()
   {
      if (DefaultControls.defaultControls == null)
         DefaultControls.defaultControls = constructDefaultControlsList();
      return DefaultControls.defaultControls;
   }

   private static ArrayList<Control> constructDefaultControlsList()
   {
      ArrayList<Control> result = new ArrayList<Control>();

      Transform handYAxisTransform = new Transform()
      {
         public Option<Integer> min = new Option<Integer>(100);
         public Option<Integer> max = new Option<Integer>(400);

         @Override
         public int getValue(Frame frame)
         {
            int min = this.min.getValue();
            int max = this.max.getValue();
            // Get y-coordinate of first finger we see
            if (frame.fingers().isEmpty())
               return -1;
            else {
               int pos = (int)frame.fingers().leftmost().tipPosition().getY();
               return 127 * (pos - min) / (max - min);
            }
         }

         /**
          * Implementers are to override this with the Transform's user-adjustable parameters.
          *
          * @return a list of Options for the user to adjust
          */
         @Override
         public List<Option> getOptions()
         {
            List<Option> options = new ArrayList<Option>();
            options.add(min);
            options.add(max);
            return options;
         }
      };
      result.add(new Control(new MIDIAddress(1, 1), "Hand Y Axis", handYAxisTransform));

      return result;
   }
}
