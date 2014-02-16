package leapmidi;

/**
 * Option
 */
public class Option<T>
{
   private T value;

   public Option(T initialValue)
   {
      this.value = initialValue;
   }

   public Option()
   {
   }

   public T getValue()
   {
      return value;
   }

   public void setValue(T value)
   {
      this.value = value;
   }
}
