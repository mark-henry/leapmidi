import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.sound.midi.*;

/**
 * MainWindow
 */
public class MainWindow
{
    private JPanel panel1;
    private JSlider slider1;
    private JComboBox comboBox1;
    private MidiDevice midiOut = null;

    public MainWindow()
    {
        // Init comboBox1
        MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();
        final DefaultComboBoxModel model = new DefaultComboBoxModel(infos);
        comboBox1.setModel(model);

        slider1.addChangeListener(new ChangeListener()
        {
            @Override
            public void stateChanged(ChangeEvent e)
            {
                JSlider source = (JSlider) e.getSource();
                // Send MIDI CC
                if (midiOut != null && midiOut.isOpen())
                {
                    try
                    {
                        midiOut.getReceiver().send(new ShortMessage(ShortMessage.CONTROL_CHANGE,
                                1, 1, slider1.getValue()), -1);
                    }
                    catch (Exception e1)
                    {
                        JOptionPane.showMessageDialog(panel1, "Couldn't send message: " + e1.getLocalizedMessage(),
                                "MIDI Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        comboBox1.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                System.out.println("Outputting to " + comboBox1.getSelectedItem().toString());

                // Get the MIDI device
                try
                {
                    midiOut = MidiSystem.getMidiDevice((MidiDevice.Info)comboBox1.getSelectedItem());
                    // Open the device
                    try
                    {
                        midiOut.open();
                    }
                    catch (MidiUnavailableException e1)
                    {
                        JOptionPane.showMessageDialog(panel1, "Couldn't open device " + comboBox1.getSelectedItem().toString(),
                                "Device Open Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
                catch (MidiUnavailableException e1)
                {
                    JOptionPane.showMessageDialog(panel1, "Couldn't get device " + comboBox1.getSelectedItem().toString(),
                            "Device Open Error", JOptionPane.ERROR_MESSAGE);
                }

            }
        });
    }

    public static void main(String[] args)
    {
        JFrame frame = new JFrame("MainWindow");
        frame.setContentPane(new MainWindow().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
