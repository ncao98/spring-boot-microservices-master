package com.course.client.beans;



public class OrderItemBean {

    private Long id;
    private Long productId;
    private String illustration;
    private String description;
    private Double price;
    private Integer quantity;

    @Override
    public String toString() {
        return "OrderItemBean{" +
                "id=" + id +
                ", productId=" + productId +
                ", illustration='" + illustration + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }

    public OrderItemBean(Long id, Long productId, String illustration, String description, Double price, Integer quantity) {
        this.id = id;
        this.productId = productId;
        this.illustration = illustration;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getIllustration() {
        return illustration;
    }

    public void setIllustration(String illustration) {
        this.illustration = illustration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
