package leapmidi;

import com.leapmotion.leap.Frame;

/**
 * Created by Mark Henry on 3/9/14.
 */
public interface ValueExtractor
{
   int valueFromFrame(Frame frame);
}
