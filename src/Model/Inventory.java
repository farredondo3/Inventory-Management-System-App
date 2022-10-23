package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Inventory class
 */
public class Inventory
{
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * adds part
     * @param newPart
     */
    public static void addPart(Part newPart)
    {
        allParts.add(newPart);

    }

    /**
     * adds part
     * @param newProduct
     */
    public static void addProduct(Product newProduct)
    {
        allProducts.add(newProduct);
    }

    /**
     * looks up part
     * @param PartId
     * @return
     */
    public static Part lookUpPart(int PartId)
    {
        for(int i = 0; i < Inventory.getAllParts().size(); i++)
        {
            Part foundPart = Inventory.getAllParts().get(i);

            if(foundPart.getId() == PartId)
                return foundPart;
        }
        return null;
    }

    /**
     * looks up product
     * @param ProductId
     * @return
     */
    public static Product lookUpProduct(int ProductId)
    {
        for(int i = 0; i < Inventory.getAllParts().size(); i++)
        {
            Product foundProduct = Inventory.getAllProducts().get(i);

            if(foundProduct.getId() == ProductId)
                return foundProduct;
        }
        return null;
    }

    /**
     * observable list of parts
     * @param partName
     * @return
     */
    public static ObservableList<Part> lookUpPart(String partName)
    {
        ObservableList<Part> partSearchFilter = FXCollections.observableArrayList();

        for(Part foundPart: Inventory.getAllParts())
        {
           if(foundPart.getName().contains(partName))
               partSearchFilter.add(foundPart);
        }

        return partSearchFilter;
        //DUPLICATES 2.0
    }

    /**
     * observable list of products
     * @param productName
     * @return
     */
    public static ObservableList<Product> lookUpProduct(String productName)
    {
        ObservableList<Product> productSearchFilter = FXCollections.observableArrayList();

        for(Product foundProduct: Inventory.getAllProducts())
        {
            if(foundProduct.getName().contains(productName))
                productSearchFilter.add(foundProduct);
        }

        return productSearchFilter;
    }

    /**
     * update part
     * @param index
     * @param newPart
     */
    public static void updatePart(int index, Part newPart)
    {
        allParts.set(index, newPart);
    }

    /**
     * update product
     * @param index
     * @param newProduct
     */
    public static void updateProduct(int index, Product newProduct)
    {
        allProducts.set(index, newProduct);

    }

    /**
     * delete part
     * @param selectedPart
     * @return
     */
    public static boolean deletePart(Part selectedPart)
    {
        for (Part p : allParts) {
            if (p.getId() == selectedPart.getId()) {
                allParts.remove(p);
                return true;
            }
        }
        return false;
    }

    /**
     * delete part
     * @param selectedProduct
     * @return
     */
    public static boolean deleteProduct(Product selectedProduct)
    {
        for (Product p : allProducts) {
            if (p.getId() == selectedProduct.getId()) {
                allProducts.remove(p);
                return true;
            }
        }
        return false;
    }

    /**
     * gets list of all parts
     * @return
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
        //COMPLETED
    }

    /**
     * gets list of all products
     * @return
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
        //COMPLETED
    }

    /**
     * new ID of product
     * @return
     */
    public static int newProductId() {
        int newId = 0;
        for (Product product : allProducts) {
            if(newId <= product.getId()) {
                newId = product.getId();
            }
        }
        return ++newId;
    }

    /** new ID of part
     *
     * @return
     */
    public static int newPartId() {
        int newId = 0;
        for (Part part : allParts) {
            if(newId <= part.getId()) {
                newId = part.getId();
            }
        }
        return ++newId;
    }
}
