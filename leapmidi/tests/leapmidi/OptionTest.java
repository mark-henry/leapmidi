package leapmidi;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * OptionTest
 */
public class OptionTest
{
   @Test
   public void testGetValue() throws Exception
   {
      Option<Integer> opt = new Option<Integer>(23);
      assertTrue(23 == opt.getValue());
   }

   @Test
   public void testSetValue() throws Exception
   {
      Option<Integer> opt = new Option<Integer>(23);
      opt.setValue(11);
      assertTrue(11 == opt.getValue());
   }
}
