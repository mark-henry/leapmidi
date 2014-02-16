package leapmidi;

import com.leapmotion.leap.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import javax.sound.midi.*;
import com.leapmotion.leap.Frame;
import sun.awt.HorizBagLayout;

/**
 * MainWindow
 */
public class MainWindow extends Listener implements Observer
{
   private JPanel panel1;
   private JComboBox comboBox1;
   private JPanel controlPanel;
   private JPanel optionPanel;
   private Profile profile;
   private MIDIInterface midiInterface = new MIDIInterface();

   public MainWindow()
   {
      Controller controller = new Controller();
      controller.addListener(this);

      initMainWindow();
   }

   private void initMainWindow()
   {
      controlPanel.setLayout(new GridLayout(0,1));
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
               JOptionPane.showMessageDialog(panel1, e1.getMessage(), "MIDI Unavailable", JOptionPane.ERROR_MESSAGE);
            }
         }
      });
   }

   private void loadProfile(List<Control> controls)
   {
      profile = new Profile(controls);
      for (Control c : controls) {
         ControlView view = new ControlView(c);
         JPanel subPanel = new JPanel();

         c.addObserver(this);
         controlPanel.add(subPanel);
         view.fillPanel(subPanel);
      }
   }

   public static void main(String[] args)
   {
      MainWindow window = new MainWindow();
      JFrame frame = new JFrame("MainWindow");
      frame.setContentPane(window.panel1);
      frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
      frame.pack();
      frame.setVisible(true);
   }

   @Override
   public void onFrame(Controller controller)
   {
      Frame frame = controller.frame();

      for (Control c : profile.getControls()) {
         c.acceptFrame(frame);
      }
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

   /**
    * This method is called whenever the observed object is changed. An
    * application calls an <tt>Observable</tt> object's
    * <code>notifyObservers</code> method to have all the object's
    * observers notified of the change.
    *
    * @param o   the observable object.
    * @param arg an argument passed to the <code>notifyObservers</code>
    */
   @Override
   public void update(Observable o, Object arg)
   {
      try {
         Control control = (Control)o;
         midiInterface.sendMessage(control.getMIDIAdress(), control.getValue());
      } catch (ClassCastException e) { /* Do nothing */ }
   }
}
