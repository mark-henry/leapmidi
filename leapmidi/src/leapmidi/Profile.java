package leapmidi;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A Profile contains many Parameters.
 */
public class Profile implements Serializable
{
    private List<Parameter> parameters = new ArrayList<Parameter>();

    /**
     * Adds a paramter to this Profile with the given Transform
     * @param transform the Transform that the new Parameter represents
     */
    public void add(Transform transform)
    {
        // Find first available CC channel and number.
        int firstAvailCCChannel = 1;
        int firstAvailCCNumber = 1;
        while (!areCCNumbersFree(firstAvailCCChannel, firstAvailCCNumber))
        {
            firstAvailCCNumber++;
            if (firstAvailCCNumber >= 127)
            {
                firstAvailCCNumber = 0;
                firstAvailCCChannel++;
            }
        }

        parameters.add(new Parameter(firstAvailCCChannel, firstAvailCCNumber, transform));
    }

    private boolean areCCNumbersFree(int CCChannel, int CCNumber)
    {
        for (Parameter p : parameters)
        {
            if (p.CCNumber == CCNumber && p.CCChannel == CCChannel)
                return false;
        }

        return true;
    }

    public List<Parameter> getParameters()
    {
        return parameters;
    }

    public Profile()
    {
    }
}
