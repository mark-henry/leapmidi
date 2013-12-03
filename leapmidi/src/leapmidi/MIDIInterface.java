package leapmidi;

import javax.sound.midi.*;
import javax.swing.*;
import java.io.Console;

/**
 * MIDIInterface
 */
public class MIDIInterface
{
    private MidiDevice midiOutDevice = null;

    public Object[] getAvailableMIDIDevices ()
    {
        MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();
        return infos;
    }
    public void setMidiOutDevice(Object newDevice) throws MidiUnavailableException
    {
        // Get the MIDI device
        this.midiOutDevice = MidiSystem.getMidiDevice((MidiDevice.Info) newDevice);
        // Open the MIDI device
        this.midiOutDevice.open();
        System.out.println("Outputting to " + newDevice.toString());
    }

    public void sendMessage(MIDIAddress address, int value)
    {
        if (midiOutDevice == null) {
            throw new RuntimeException("MIDI Interface must have an output device");
        }

        try {
            midiOutDevice.getReceiver().send(new ShortMessage(ShortMessage.CONTROL_CHANGE,
                    address.channel, address.number, value), -1);
        }
        catch (InvalidMidiDataException e)
        {
            System.err.print("MIDI interface error: Invalid MIDI data: " + e.getLocalizedMessage());
        }
        catch (MidiUnavailableException e)
        {
            System.err.print("MIDI interface error: MIDI Unavailable: " + e.getLocalizedMessage());
        }
    }
}
