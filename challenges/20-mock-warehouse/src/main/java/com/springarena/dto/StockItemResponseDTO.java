package com.springarena.dto;

public class StockItemResponseDTO {
    private Long id;
    private String name;
    private String sku;
    private String location;
    private Integer quantity;
    private Double price;
    private Double totalValue;

    public StockItemResponseDTO() {}

    public StockItemResponseDTO(Long id, String name, String sku, String location, Integer quantity, Double price, Double totalValue) {
        this.id = id;
        this.name = name;
        this.sku = sku;
        this.location = location;
        this.quantity = quantity;
        this.price = price;
        this.totalValue = totalValue;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getSku() { return sku; }
    public void setSku(String sku) { this.sku = sku; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
    public Double getTotalValue() { return totalValue; }
    public void setTotalValue(Double totalValue) { this.totalValue = totalValue; }
}
