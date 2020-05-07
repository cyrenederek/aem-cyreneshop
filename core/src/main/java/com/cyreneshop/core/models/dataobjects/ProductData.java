package com.cyreneshop.core.models.dataobjects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductData {
    @JsonProperty("product_id")
    private String productId;
    @JsonProperty("product_name")
    private String productName;
    private String description;
    private String price;
    @JsonProperty("category_id")
    private String categoryId;
    @JsonProperty("category_name")
    private String categoryName;

    public String getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        return price;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }
}
