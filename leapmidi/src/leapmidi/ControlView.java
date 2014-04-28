package leapmidi;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * ControlView
 */
public class ControlView implements ControlObserver, ChangeListener
{
   private JLabel nameLabel;
   private JSlider slider;
   private Control control;
   private JButton showOptionsButton;
   private JPanel optionsPanel;
   private List<OptionView> optionViews = new ArrayList<OptionView>();

   public ControlView(Control control)
   {
      this.control = control;
      control.addObserver(this);
      slider = new JSlider(0, 127, 0);
      nameLabel = new JLabel(control.getName());
      slider.addChangeListener(this);
      showOptionsButton = new JButton("→");
      showOptionsButton.addActionListener(new ActionListener()
      {
         @Override
         public void actionPerformed(ActionEvent e)
         {
            fillOptionsPanel();
         }
      });
   }

   public void setOptionsPanel(JPanel optionsPanel)
   {
      this.optionsPanel = optionsPanel;
   }

   public void setOptionViews(List<OptionView> views)
   {
      this.optionViews = views;
   }

   /**
    * Called whenever the Control changes state, for any reason
    */
   @Override
   public void onControlChange(Control control)
   {
      slider.setValue(control.getValue());
      nameLabel.setText(control.getName());
   }

   /**
    * Called only when slider is manually moved by the user.
    *
    * @param e a ChangeEvent object
    */
   @Override
   public void stateChanged(ChangeEvent e)
   {
      control.setValue(slider.getValue());
   }

   private void fillOptionsPanel()
   {
      optionsPanel.removeAll();
      for (OptionView optionView : optionViews)
      {
         JPanel subPanel = new JPanel();
         subPanel.setMaximumSize(new Dimension(Short.MAX_VALUE, 30));

         optionView.fillPanel(subPanel);
         optionsPanel.add(subPanel);
      }
      optionsPanel.validate();
   }

   public void fillPanel(JPanel panel)
   {
      panel.removeAll();
      panel.setLayout(new BorderLayout(5, 5));
      panel.add(nameLabel, BorderLayout.WEST);
      panel.add(slider, BorderLayout.CENTER);
      panel.add(showOptionsButton, BorderLayout.EAST);
   }

   public Control getControl()
   {
      return control;
   }
}
