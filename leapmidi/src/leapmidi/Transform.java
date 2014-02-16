package leapmidi;

import com.leapmotion.leap.Frame;

import java.util.List;

/**
 * Defines a transform between the data in a Leap Frame object and a [0-127] value.
 */
public abstract class Transform
{
   /**
    * Interprets the Frame data and returns the [0-127] that the Frame represents.
    * @param frame the Frame to interpret
    * @return [0-127], or -1 if no data is available.
    */
   abstract public int getValue(Frame frame);

   /**
    * Implementers are to override this with the Transform's user-adjustable parameters.
    * @return a list of Options for the user to adjust
    */
   abstract public List<Option> getOptions();
}
