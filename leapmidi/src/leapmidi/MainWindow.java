package leapmidi;

import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Listener;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.sound.midi.*;

/**
 * MainWindow
 */
public class MainWindow extends Listener
{
    private JPanel panel1;
    private JComboBox comboBox1;
    private JPanel controlPanel;
    private Profile profile = new Profile();
    private Controller controller = new Controller();
    private MIDIInterface midiInterface = new MIDIInterface();

    public MainWindow()
    {
        controlPanel.setLayout(new GridLayout(0,1));
        initMainWindow();
    }

    private void initMainWindow()
    {
        initMIDIDevices();
        loadProfile(DefaultControls.getDefaultControls());
    }

    private void initMIDIDevices()
    {
        // Init comboBox1 with available MIDI devices
        DefaultComboBoxModel model = new DefaultComboBoxModel(midiInterface.getAvailableMIDIDevices());
        comboBox1.setModel(model);

        comboBox1.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    midiInterface.setMidiOutDevice(comboBox1.getSelectedItem());
                }
                catch (MidiUnavailableException e1)
                {
                    JOptionPane.showMessageDialog(panel1, e1.getMessage(), "MIDI Unavailable", JOptionPane.OK_OPTION);
                }
            }
        });
    }

    private void loadProfile(List<Control> controls)
    {
        for (Control c : controls) {
            System.out.println("got control " + c.toString());
            controlPanel.add(new JLabel(c.getName()));
        }
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
        controller.setPolicyFlags(Controller.PolicyFlag.POLICY_BACKGROUND_FRAMES);
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
        MainWindow window = new MainWindow();
        JFrame frame = new JFrame("MainWindow");
        frame.setContentPane(window.panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        window.controller.addListener(window);
    }

}
