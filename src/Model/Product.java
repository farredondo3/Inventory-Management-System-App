package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * product class
 */
public class Product
{
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /**
     *
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     */
    public Product(int id, String name, double price, int stock, int min, int max)
    {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * @return gets ID
     */
    public int getId() {
        return id;
    }

    /**
     * @param id sets ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return gets name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name sets name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return gets price
     */
    public double getPrice() {
        return price;
    }

    /**
     * @param price sets price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @return gets inventory
     */
    public int getStock() {
        return stock;
    }

    /**
     * @param stock returns inventory
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * @return the min
     */
    public int getMin() {
        return min;
    }

    /**
     * @param min sets min
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * @return gets max
     */
    public int getMax() {
        return max;
    }

    /**
     * @param max sets max
     */
    public void setMax(int max) {
        this.max = max;
    }
    /**
     * Adds a Part object to an ObservableList.
     *
     * @param part
     */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }


    /**
     * Removes a Part object from ObservableList.
     *
     * @param selectedAssociatedPart
     * @return
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        return associatedParts.remove(selectedAssociatedPart);
    }


    /**
     * Returns an ObservableList of all associated parts.
     *
     * @return
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }
}
