package org.santiago.springcloud.msvcitems.DTOentities;

public class Item {
        //Atributos de Item
    private ProductDTO product;
    private int quantity;

        //Constructores de Item
    public Item() {    }
    public Item(ProductDTO product, int quantity) {
        this();
        this.product = product;
        this.quantity = quantity;
    }

    //Asignadores de atributos de Item (setters)
    public void setProduct(ProductDTO product) {
        this.product = product;
    }
        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        //Lectores de atributos de Item (getters)
    public ProductDTO getProduct() {
        return this.product;
    }
        public int getQuantity() {
            return this.quantity;
        }

        //Métodos de Item
    public Double getLineTotal() {
        return this.product.getPrice() * this.quantity;
    }
}
