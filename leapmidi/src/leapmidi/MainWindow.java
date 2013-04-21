package leapmidi;

import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Listener;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.sound.midi.*;

/**
 * MainWindow
 */
public class MainWindow extends Listener
{
    private JPanel panel1;
    private JSlider slider1;
    private JComboBox comboBox1;
    private MidiDevice midiOutDevice = null;
    private Profile profile = new Profile();
    private static LeapMidiListener listener;
    private static Controller controller;

    public MainWindow()
    {
        listener = new LeapMidiListener();
        controller = new Controller();

        // For testing purposes
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

        // Init comboBox1
        MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();
        final DefaultComboBoxModel model = new DefaultComboBoxModel(infos);
        comboBox1.setModel(model);

        comboBox1.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                System.out.println("Outputting to " + comboBox1.getSelectedItem().toString());

                try
                {
                    // Get the MIDI device
                    midiOutDevice = MidiSystem.getMidiDevice((MidiDevice.Info)comboBox1.getSelectedItem());
                    try
                    {
                        // Open the MIDI device
                        midiOutDevice.open();
                    }
                    catch (MidiUnavailableException e1)
                    {
                        JOptionPane.showMessageDialog(panel1, "Couldn't open device " +
                                comboBox1.getSelectedItem().toString(), "Device Open Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
                catch (MidiUnavailableException e1)
                {
                    JOptionPane.showMessageDialog(panel1, "Couldn't get device " +
                            comboBox1.getSelectedItem().toString(), "Device Open Error", JOptionPane.ERROR_MESSAGE);
                }

            }
        });
    }

    @Override
    public void onFrame(Controller controller)
    {
        Frame frame = controller.frame();

        System.out.println("Frame gotten");

        slider1.setValue(profile.getParameters().get(0).getTransform().getValue(frame));
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

    public static void main(String[] args)
    {
        JFrame frame = new JFrame("MainWindow");
        frame.setContentPane(new MainWindow().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        controller.addListener(listener);
    }
}
