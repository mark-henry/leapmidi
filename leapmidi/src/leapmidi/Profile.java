package leapmidi;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A Profile aggregates Controls.
 */
public class Profile implements Serializable
{
   private List<Control> controls = new ArrayList<Control>();

   public Profile(List<Control> controls)
   {
      this.controls = controls;
   }

   /**
    * Adds the given Control to this Profile
    */
   public void add(Control control)
   {
      controls.add(control);
   }

   private boolean areCCNumbersFree(int CCChannel, int CCNumber)
   {
      for (Control c : controls)
      {
         MIDIAddress addr = c.getMIDIAdress();
         if (addr.number == CCNumber && addr.channel == CCChannel)
            return false;
      }

      return true;
   }

   public List<Control> getControls()
   {
      return controls;
   }
}
