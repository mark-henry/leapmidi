package leapmidi;

import com.leapmotion.leap.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * LeapMidiListener
 */
public class LeapMidiListener extends Listener
{
    private Profile profile;

    public LeapMidiListener()
    {
        profile = new Profile();

        profile.add(new Transform()
        {
            @Override
            public int getValue(Frame frame)
            {
                // Get y-coordinate of first available finger
                if (frame.fingers().isEmpty())
                    return -1;
                else
                    return (int)frame.finger(0).tipPosition().getY();
            }
        });
    }

    @Override
    public void onFrame(Controller controller)
    {
        Frame frame = controller.frame();
    }

    @Override
    public void onInit(Controller controller)
    {
        System.out.println("Controller Initialized");
    }

    @Override
    public void onConnect(Controller controller)
    {
        System.out.println("Controller Connnected");
    }

    @Override
    public void onExit(Controller controller)
    {
        System.out.println("Controller Exited");
    }

    @Override
    public void onDisconnect(Controller controller)
    {
        System.out.println("Controller Disconnected");
    }
}
