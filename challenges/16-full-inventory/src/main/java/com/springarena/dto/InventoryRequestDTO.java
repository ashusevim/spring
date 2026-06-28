package com.springarena.dto;

public class InventoryRequestDTO {
    private String name;
    private String category;
    private Integer quantity;
    private Double unitPrice;
    private String supplier;

    public InventoryRequestDTO() {}

    public InventoryRequestDTO(String name, String category, Integer quantity, Double unitPrice, String supplier) {
        this.name = name;
        this.category = category;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.supplier = supplier;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public Double getUnitPrice() { return unitPrice; }
    public void setUnitPrice(Double unitPrice) { this.unitPrice = unitPrice; }
    public String getSupplier() { return supplier; }
    public void setSupplier(String supplier) { this.supplier = supplier; }
}
