package Model;

/**
 * Class of inHouse radio button option
 */
public class InHouse extends Part
{
    /**
     * machine ID of part
     */
    private int machineId;

    /**
     * Constructor of inHouse part object
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     * @param machineId
     */

    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId)
    {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     * sets machineID
     * @param machineId
     */
    public void setMachineId(int machineId)
    {
        this.machineId = machineId;
    }

    /**
     * gets machineID
     * @return
     */
    public int getMachineId()
    {
        return machineId;
    }

}
