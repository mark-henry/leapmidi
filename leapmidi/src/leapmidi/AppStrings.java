package leapmidi;

import java.util.Dictionary;
import java.util.Hashtable;

/**
 * AppStrings
 */
public class AppStrings
{
   private static Hashtable<String, String> strings = new Hashtable<String, String>();

   static {
      strings.put("buttonSaveProfileText", "Save Profile...");
      strings.put("buttonLoadProfileText", "Load Profile...");
      strings.put("midiComboBoxLabelText", "MIDI Out Device:");
   }

   public static String get(String key)
   {
      if (strings.containsKey(key))
         return strings.get(key);
      else
         return "'" + key + "' not found";
   }
}
