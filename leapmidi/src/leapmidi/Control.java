package leapmidi;

import com.leapmotion.leap.Frame;

import java.io.Serializable;
import java.util.Observable;

/**
 * A Control is a CC channel and a Transform.
 */
public class Control extends Observable
{
   private Transform transform;
   private MIDIAddress address;
   private String name;
   private int value;

   public Control(MIDIAddress address, Transform transform)
   {
      this.transform = transform;
      this.address = address;
   }

   public Control(int CCChannel, int CCNumber, String name, Transform transform)
   {
      this.transform = transform;
      this.address = new MIDIAddress(CCChannel, CCNumber);
      this.name = name;
   }

   public Control(int CCChannel, int CCNumber, Transform transform)
   {
      this.transform = transform;
      this.address = new MIDIAddress(CCChannel, CCNumber);
   }

   public void setValue(int newValue)
   {
      if (this.value != newValue) {
         value = newValue;
         setChanged();
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
         setChanged();
         notifyObservers();
      }
   }

   public void setAddress(MIDIAddress address)
   {
      this.address = address;
   }

   public MIDIAddress getMIDIAdress()
   {
      return this.address;
   }

   public void acceptFrame(Frame frame)
   {
      int newValue = transform.getValue(frame);
      this.setValue(newValue);
   }

   public int getValue()
   {
      clearChanged();
      return value;
   }
}
