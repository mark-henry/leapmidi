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

    /**
     * Adds the given Control to this Profile
     */
    public void add(Control control)
    {
        controls.add(control);
    }

    private boolean areCCNumbersFree(int CCChannel, int CCNumber)
    {
        for (Control p : controls)
        {
            if (p.CCNumber == CCNumber && p.CCChannel == CCChannel)
                return false;
        }

        return true;
    }

    public List<Control> getControls()
    {
        return controls;
    }

    public Profile()
    {
    }
}
