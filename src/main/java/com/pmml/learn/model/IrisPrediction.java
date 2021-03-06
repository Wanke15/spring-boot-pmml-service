package com.pmml.learn.model;

public class IrisPrediction {
    public Double probability;
    public Integer categoryId;
    public String category;

    public IrisPrediction() {
    }

    public IrisPrediction(Integer categoryId, String category, Double probability) {
        this.categoryId = categoryId;
        this.category = category;
        this.probability = probability;
    }

    @Override
    public String toString() {
        return String.format("CategoryId: %d, Category: %s, Probability: %f ", categoryId, category, probability);
    }
}
