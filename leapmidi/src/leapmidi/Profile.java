package leapmidi;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A Profile aggregates ControlViews, each of which aggregates a Control and a list of OptionViews.
 */
public class Profile implements Serializable
{
   private List<ControlView> controlViews;

   public Profile(Collection<ControlView> controlViews)
   {
      this.controlViews = new ArrayList<ControlView>();
      for (ControlView controlView : controlViews) {
         this.add(controlView);
      }
   }

   public void add(ControlView controlView)
   {
      MIDIAddress midiAddress = controlView.getControl().getMIDIAddress();

      while (!midiAddressFree(midiAddress))
         midiAddress.increment();

      controlView.getControl().setAddress(midiAddress);
      controlViews.add(controlView);
   }

   private boolean midiAddressFree(MIDIAddress midiAddress)
   {
      return midiAddressFree(midiAddress.channel, midiAddress.controller);
   }

   private boolean midiAddressFree(int CCChannel, int CCController)
   {
      for (ControlView controlView : controlViews)
      {
         MIDIAddress addr = controlView.getControl().getMIDIAddress();
         if (addr.controller == CCController && addr.channel == CCChannel)
            return false;
      }

      return true;
   }

   public List<ControlView> getControlViews()
   {
      return controlViews;
   }
}
