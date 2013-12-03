package leapmidi;

import com.leapmotion.leap.Frame;

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

        result.add(new Control(1, 1, "Hand Y Axis", new Transform()
        {
            @Override
            public int getValue(Frame frame)
            {
                int min = 100;
                int max = 400;
                // Get y-coordinate of first finger we see
                if (frame.fingers().isEmpty())
                    return -1;
                else {
                    int pos = (int)frame.fingers().leftmost().tipPosition().getY();
                    return 127 * (pos - min) / (max - min);
                }
            }
        }));

        result.add(new Control(1, 2, "Hand X Axis", new Transform()
        {
            @Override
            public int getValue(Frame frame)
            {
                int min = -400;
                int max = 400;
                // Get y-coordinate of first finger we see
                if (frame.fingers().isEmpty())
                    return -1;
                else {
                    int pos = (int)frame.fingers().leftmost().tipPosition().getX();
                    return 127 * (pos - min) / (max - min);
                }
            }
        }));

        return result;
    }
}
