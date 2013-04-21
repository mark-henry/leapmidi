package leapmidi;

import com.leapmotion.leap.Frame;

import java.io.Serializable;

/**
 * A Parameter is a CC channel and a Transform.
 */
public class Parameter implements Serializable
{
    private Transform transform;
    protected int CCChannel;
    protected int CCNumber;

    public Parameter(int CCChannel, int CCNumber, Transform transform)
    {
        this.transform = transform;
        this.CCChannel = CCChannel;
        this.CCNumber = CCNumber;
    }

    public Transform getTransform()
    {
        return this.transform;
    }

    public int getCCNumber()
    {
        return CCNumber;
    }

    public int getCCChannel()
    {
        return CCChannel;
    }
}
