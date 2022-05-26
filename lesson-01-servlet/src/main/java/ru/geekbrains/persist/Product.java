package ru.geekbrains.persist;

public class Product {

    private Long id;
    private int cost;
    private String title;
    public Product(String title, int cost) {
        this.title = title;
        this.cost = cost;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public int getProductCost() {
        return cost;
    }
    public void setProductCost(int cost) {
        this.cost = cost;
    }
    public String getProductTitle() {
        return title;
    }
    public void setProductTitle(String title) {
        this.title = title;
    }
}
