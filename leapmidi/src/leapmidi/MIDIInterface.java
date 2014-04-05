package leapmidi;

import javax.sound.midi.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * MIDIInterface
 */
public class MIDIInterface
{
   public static final int MIDI_MAXVAL = 127;
   private static final int MIDI_PERIOD_MILLISECONDS = 5;
   private static final int MAX_MESSAGES_PER_PERIOD = 10;
   public static int messagesThisPeriod = 0;
   private static Queue<MidiMessage> messageQueue = new LinkedList<MidiMessage>();
   private static MidiDevice midiOutDevice = null;
   private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
   private static ScheduledFuture<?> periodHandle;

   {
      final Runnable midiPeriodRunner = new Runnable()
      {
         @Override
         public void run()
         {
            everyPeriod();
         }
      };

      periodHandle = scheduler.scheduleAtFixedRate(midiPeriodRunner, 0, MIDI_PERIOD_MILLISECONDS, TimeUnit.MILLISECONDS);
   }


   public Object[] getAvailableMIDIDevices()
   {
      return MidiSystem.getMidiDeviceInfo();
   }

   public void setMidiOutDevice(Object newDevice) throws MidiUnavailableException
   {
      this.midiOutDevice = MidiSystem.getMidiDevice((MidiDevice.Info) newDevice);
      this.midiOutDevice.open();
      System.out.println("Outputting to " + newDevice.toString());
   }

   public void sendMessage(MIDIAddress address, int value)
   {
      try
      {
         messageQueue.add(new ShortMessage(ShortMessage.CONTROL_CHANGE,
               address.channel, address.controller, value));
      }
      catch (InvalidMidiDataException e)
      {
         System.err.println("MIDI interface error: Invalid MIDI data: " + e.getLocalizedMessage());
      }
   }

   private static void everyPeriod()
   {
      messagesThisPeriod = 0;

      if (midiOutDevice == null) {
         return;
      }

      long timeStamp = -1;
      while (!messageQueue.isEmpty() && messagesThisPeriod <= MAX_MESSAGES_PER_PERIOD) {
         MidiMessage message = messageQueue.remove();

         try {
            midiOutDevice.getReceiver().send(message, timeStamp);
            messagesThisPeriod++;
            //System.out.println("MIDI (" + address.channel + ", " + address.controller + ") = " + value);
            //System.out.println(messagesThisPeriod + " sent, " + messageQueue.size() + " queued");
         }
         catch (MidiUnavailableException e)
         {
            System.err.println("MIDI interface error: MIDI Unavailable: " + e.getLocalizedMessage());
            return;
         }
      }
   }

   public static void close()
   {
      periodHandle.cancel(true);
   }
}
