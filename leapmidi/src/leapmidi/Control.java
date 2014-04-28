package leapmidi;

import com.leapmotion.leap.Frame;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * A Control is a CC channel and a Transform.
 */
public class Control
{
   private Transform transform;
   private MIDIAddress address;
   private String name;
   private int value;
   private List<ControlObserver> obesrvers;

   public Control(String name, MIDIAddress address, Transform transform)
   {
      if (address == null)
         address = new MIDIAddress(1, 1);

      this.address = address;
      this.transform = transform;
      this.name = name;

      this.obesrvers = new ArrayList<ControlObserver>();
   }

   public void setValue(int newValue)
   {
      newValue = Math.max(0, Math.min(newValue, MIDIInterface.MIDI_MAXVAL));
      if (this.value != newValue) {
         value = newValue;
         notifyObservers();
      }
   }

   public String getName()
   {
      return name;
   }

   public void setName(String name)
   {
      if (!name.equals(this.name)) {
         this.name = name;
         notifyObservers();
      }
   }

   private void notifyObservers()
   {
      for (ControlObserver co : this.obesrvers) {
         co.onControlChange(this);
      }
   }

   public void setAddress(MIDIAddress address)
   {
      this.address = address;
   }

   public MIDIAddress getMIDIAddress()
   {
      return this.address;
   }

   public void acceptFrame(Frame frame)
   {
      int newValue = transform.getValue(frame);
      if (newValue != -1)
         this.setValue(newValue);
   }

   public int getValue()
   {
      return value;
   }

   public void addObserver(ControlObserver controlObserver)
   {
      this.obesrvers.add(controlObserver);
   }
}
