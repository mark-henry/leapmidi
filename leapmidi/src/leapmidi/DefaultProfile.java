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

      views.add(ControlViewFactory.makeMinMaxControl("Hand X Axis", -200, -100, 100, 200,
            new ValueExtractor()
            {
               @Override
               public int valueFromFrame(Frame frame)
               {
                  return (int) frame.hands().rightmost().palmPosition().getX();
               }
            }
      ));

      views.add(ControlViewFactory.makeMinMaxControl("Hand Y Axis", 10, 100, 400, 800,
            new ValueExtractor()
            {
               @Override
               public int valueFromFrame(Frame frame)
               {
                  return (int) frame.hands().rightmost().palmPosition().getY();
               }
            }
      ));

      views.add(ControlViewFactory.makeMinMaxControl("Hand Z Axis", -200, -100, 100, 200,
            new ValueExtractor()
            {
               @Override
               public int valueFromFrame(Frame frame)
               {
                  return (int) frame.hands().rightmost().palmPosition().getZ();
               }
            }
      ));

      views.add(ControlViewFactory.makeMinMaxControl("Hand Pitch", -150, -50, 125, 150,
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

      views.add(ControlViewFactory.makeMinMaxControl("Hand Waggle", -300, -100, 100, 300,
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

      views.add(ControlViewFactory.makeMinMaxControl("Hand Radius", 0, 50, 100, 150,
            new ValueExtractor()
            {
               @Override
               public int valueFromFrame(Frame frame)
               {
                  return (int) frame.hands().rightmost().sphereRadius();
               }
            }
      ));

      views.add(ControlViewFactory.makeMinMaxControl("Stabilized Fingertip X", -200, -100, 100, 200,
            new ValueExtractor()
            {
               @Override
               public int valueFromFrame(Frame frame)
               {
                  return (int) frame.pointables().frontmost().stabilizedTipPosition().getX();
               }
            }
      ));

      views.add(ControlViewFactory.makeMinMaxControl("Stabilized Fingertip Y", 10, 100, 400, 800,
            new ValueExtractor()
            {
               @Override
               public int valueFromFrame(Frame frame)
               {
                  return (int) frame.pointables().frontmost().stabilizedTipPosition().getY();
               }
            }
      ));

      views.add(ControlViewFactory.makeMinMaxControl("Stabilized Fingertip Z", -200, -100, 100, 200,
            new ValueExtractor()
            {
               @Override
               public int valueFromFrame(Frame frame)
               {
                  return (int) frame.pointables().frontmost().stabilizedTipPosition().getZ();
               }
            }
      ));

      views.add(ControlViewFactory.makeMinMaxControl("Fingertip X", -200, -100, 100, 200,
            new ValueExtractor()
            {
               @Override
               public int valueFromFrame(Frame frame)
               {
                  return (int) frame.pointables().frontmost().stabilizedTipPosition().getX();
               }
            }
      ));

      views.add(ControlViewFactory.makeMinMaxControl("Fingertip Y", 10, 100, 400, 800,
            new ValueExtractor()
            {
               @Override
               public int valueFromFrame(Frame frame)
               {
                  return (int) frame.pointables().frontmost().tipPosition().getY();
               }
            }
      ));

      views.add(ControlViewFactory.makeMinMaxControl("Fingertip Z", -200, -100, 100, 200,
            new ValueExtractor()
            {
               @Override
               public int valueFromFrame(Frame frame)
               {
                  return (int) frame.pointables().frontmost().tipPosition().getZ();
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
