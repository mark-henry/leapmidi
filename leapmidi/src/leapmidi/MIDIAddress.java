package leapmidi;

/**
 * A MIDIAddress contains a CC channel and controller number. It's essentially an ordered pair of numbers.
 */
public class MIDIAddress
{
   private static final int MAX_CHANNEL = 15;
   private static final int MAX_CONTROLLER = 127;
   public int channel;
    public int controller;

    public MIDIAddress(int channel, int controller)
    {
        this.channel = channel;
        this.controller = controller;
    }

   public void increment()
   {
      this.controller++;
      if (this.controller > MAX_CONTROLLER) {
         this.controller = 0;
         this.channel++;
      }
      if (this.channel > MAX_CHANNEL) {
         this.channel = 0;
      }
   }
}
