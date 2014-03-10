package leapmidi;

import com.leapmotion.leap.FingerList;
import com.leapmotion.leap.Frame;

import java.util.ArrayList;
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

      views.add(ControlFactory.makeMinMaxControl("Hand Y Axis", 10, 100, 400, 800,
            new ValueExtractor()
            {
               @Override
               public int valueFromFrame(Frame frame)
               {
                  return (int) frame.hands().rightmost().palmPosition().getY();
               }
            }
      ));

      views.add(ControlFactory.makeMinMaxControl("Hand X Axis", -200, -100, 100, 200,
            new ValueExtractor()
            {
               @Override
               public int valueFromFrame(Frame frame)
               {
                  return (int) frame.hands().rightmost().palmPosition().getX();
               }
            }
      ));

      views.add(ControlFactory.makeMinMaxControl("Hand Tilt - Pitch", -150, -50, 125, 150,
            new ValueExtractor()
            {
               @Override
               public int valueFromFrame(Frame frame)
               {
                  float pitch = frame.hands().rightmost().direction().pitch();
                  return (int) (100 * pitch);
               }
            }
      ));

      views.add(ControlFactory.makeMinMaxControl("Hand Waggle", -300, -100, 100, 300,
            new ValueExtractor()
            {
               @Override
               public int valueFromFrame(Frame frame)
               {
                  FingerList fingers = frame.hands().rightmost().fingers();
                  float leftHeight = fingers.leftmost().tipPosition().getY();
                  float rightHeight = fingers.rightmost().tipPosition().getY();
                  return (int) (leftHeight - rightHeight);
               }
            }
      ));

      views.add(ControlFactory.makeMinMaxControl("Hand Radius", 0, 50, 100, 150,
            new ValueExtractor()
            {
               @Override
               public int valueFromFrame(Frame frame)
               {
                  float radius = frame.hands().rightmost().sphereRadius();
                  return (int) radius;
               }
            }
      ));


      return new Profile(views);
   }

   private static int valueFromFrame(Frame frame)
   {
      return (int)frame.fingers().leftmost().tipPosition().getY();
   }
}
