package Model;

/**
 * Class of outsourced radio button option
 */
public class Outsourced extends Part
{
    /**
     * company name of part
     */
    private String companyName;

    /**
     * Constructor of inHouse part object
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     * @param companyName
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName)
    {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * sets company name
     * @param companyName company name
     */
    public void setCompanyName(String companyName)
    {
        this.companyName = companyName;
    }

    /**
     * gets company name
     * @return company name
     */
    public String getCompanyName()
    {
        return companyName;
    }

}
