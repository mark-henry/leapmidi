package leapmidi;

import com.leapmotion.leap.Frame;

import java.io.Serializable;

/**
 * A Control is a CC channel and a Transform.
 */
public class Control implements Serializable
{
    protected Transform transform;
    protected int CCChannel;
    protected int CCNumber;
    protected String name;

    public Control(int CCChannel, int CCNumber, String name, Transform transform)
    {
        this.transform = transform;
        this.CCChannel = CCChannel;
        this.CCNumber = CCNumber;
        this.name = name;
    }

    public Control(int CCChannel, int CCNumber, Transform transform)
    {
        this.transform = transform;
        this.CCChannel = CCChannel;
        this.CCNumber = CCNumber;
    }

    public int getCCChannel()
    {
        return CCChannel;
    }

    public void setCCChannel(int CCChannel)
    {
        this.CCChannel = CCChannel;
    }

    public int getCCNumber()
    {
        return CCNumber;
    }

    public void setCCNumber(int CCNumber)
    {
        this.CCNumber = CCNumber;
    }

    public Transform getTransform()
    {
        return this.transform;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

}
