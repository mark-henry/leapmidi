package leapmidi;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A Profile aggregates ControlViews, each of which aggregates a Control and a list of OptionViews.
 */
public class Profile implements Serializable
{
   private List<ControlView> controlViews = new ArrayList<ControlView>();

   public Profile(List<ControlView> controls)
   {
      this.controlViews = controls;
   }

   public void add(ControlView control)
   {
      controlViews.add(control);
   }

   private boolean areCCNumbersFree(int CCChannel, int CCNumber)
   {
      for (ControlView controlView : controlViews)
      {
         MIDIAddress addr = controlView.getControl().getMIDIAdress();
         if (addr.number == CCNumber && addr.channel == CCChannel)
            return false;
      }

      return true;
   }

   public List<ControlView> getControlViews()
   {
      return controlViews;
   }
}
