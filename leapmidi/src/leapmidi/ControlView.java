package leapmidi;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.Observable;
import java.util.Observer;

/**
 * ControlView
 */
public class ControlView implements Observer, ChangeListener
{
   private JLabel nameLabel;
   private JSlider slider;
   private Control control;

   public ControlView(Control control)
   {
      this.control = control;
      control.addObserver(this);
      slider = new JSlider(0, 127, 0);
      nameLabel = new JLabel(control.getName());
      slider.addChangeListener(this);
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
      // Called when the Control changes state
      slider.setValue(control.getValue());
      nameLabel.setText(control.getName());
   }

   /**
    * Invoked when the target of the listener has changed its state.
    *
    * @param e a ChangeEvent object
    */
   @Override
   public void stateChanged(ChangeEvent e)
   {
      // Called when slider is manually moved by user
      control.setValue(slider.getValue());
   }

   public void fillPanel(JPanel panel)
   {
      panel.add(nameLabel);
      panel.add(slider);
   }
}
