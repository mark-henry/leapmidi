package leapmidi;

import com.leapmotion.leap.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Observable;
import java.util.Observer;
import javax.sound.midi.*;
import javax.swing.border.Border;

import com.leapmotion.leap.Frame;

/**
 * MainWindow
 */
public class MainWindow extends Listener implements ControlObserver
{
   private JPanel windowPanel = new JPanel();
   private JSplitPane windowSplitPane;
      private JScrollPane optionsScrollPane;
      private JPanel optionsPanel = new JPanel();
      private JPanel leftPanel = new JPanel();
         private JPanel topPanel = new JPanel();
            private JButton buttonLoadProfile = new JButton(AppStrings.get("buttonLoadProfileText"));
            private JButton buttonSaveProfile = new JButton(AppStrings.get("buttonSaveProfileText"));
            private JLabel midiComboBoxLabel = new JLabel(AppStrings.get("midiComboBoxLabelText"));
            private JComboBox midiComboBox = new JComboBox();
         private JScrollPane controlsScrollPane;
            private JPanel controlsPanel = new JPanel();

   private Profile currentProfile;
   private MIDIInterface midiInterface = new MIDIInterface();
   private Controller controller;

   private void createUIComponents()
   {
      topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
      //topPanel.add(buttonLoadProfile);
      //topPanel.add(Box.createRigidArea(new Dimension(2, 0)));
      //topPanel.add(buttonSaveProfile);
      //topPanel.add(Box.createRigidArea(new Dimension(7, 0)));
      topPanel.add(midiComboBoxLabel);
      topPanel.add(Box.createRigidArea(new Dimension(2, 0)));
      topPanel.add(midiComboBox);
      midiComboBox.setMinimumSize(new Dimension(100, 0));

      controlsPanel.setLayout(new BoxLayout(controlsPanel, BoxLayout.PAGE_AXIS));
      controlsScrollPane = new JScrollPane(controlsPanel);

      leftPanel.setLayout(new BorderLayout(5,5));
      leftPanel.add(topPanel, BorderLayout.NORTH);
      leftPanel.add(controlsScrollPane, BorderLayout.CENTER);

      optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.PAGE_AXIS));
      optionsScrollPane = new JScrollPane(optionsPanel);
      optionsScrollPane.setPreferredSize(new Dimension(300, 0));

      windowSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, optionsScrollPane);
      windowPanel.setLayout(new BorderLayout());
      windowPanel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
      windowPanel.add(windowSplitPane, BorderLayout.CENTER);
   }

   public MainWindow()
   {
      initMainWindow();
      initController();
   }

   private void initController()
   {
      this.controller = new Controller(this);
      controller.setPolicyFlags(Controller.PolicyFlag.POLICY_BACKGROUND_FRAMES);
   }

   private void initMainWindow()
   {
      createUIComponents();
      initMIDIDevices();
      loadProfile(DefaultProfile.getDefaultControls());
   }

   private void initMIDIDevices()
   {
      // Init midiComboBox with available MIDI devices
      DefaultComboBoxModel model = new DefaultComboBoxModel(midiInterface.getAvailableMIDIDevices());
      midiComboBox.setModel(model);

      midiComboBox.addActionListener(new ActionListener()
      {
         @Override
         public void actionPerformed(ActionEvent e)
         {
            try
            {
               midiInterface.setMidiOutDevice(midiComboBox.getSelectedItem());
            }
            catch (MidiUnavailableException e1)
            {
               JOptionPane.showMessageDialog(windowPanel, e1.getMessage(), "MIDI Unavailable", JOptionPane.ERROR_MESSAGE);
            }
         }
      });
   }

   private void loadProfile(Profile profile)
   {
      this.currentProfile = profile;
      for (ControlView cv : profile.getControlViews()) {
         Control c = cv.getControl();
         c.addObserver(this);

         renderControlViewToControlsPane(cv);
      }
   }

   private void renderControlViewToControlsPane(ControlView cv)
   {
      cv.setOptionsPanel(optionsPanel);

      JPanel subPanel = new JPanel();
      subPanel.setBorder(BorderFactory.createEmptyBorder(2,4,2,4));
      subPanel.setMaximumSize(new Dimension(Short.MAX_VALUE, 30));

      controlsPanel.add(subPanel);
      cv.fillPanel(subPanel);
   }

   public static void main(String[] args)
   {
      MainWindow window = new MainWindow();
      JFrame frame = new JFrame(AppStrings.get("MainWindowTitle"));
      frame.setContentPane(window.windowPanel);
      frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
      frame.pack();
      frame.setVisible(true);
   }

   @Override
   public void onFrame(Controller controller)
   {
      Frame frame = controller.frame();

      for (ControlView cv : currentProfile.getControlViews()) {
         cv.getControl().acceptFrame(frame);
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

   @Override
   public void onControlChange(Control control)
   {
      midiInterface.sendMessage(control.getMIDIAddress(), control.getValue());
   }
}