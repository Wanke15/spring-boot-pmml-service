package com.pmml.learn.model;

public class IrisFeature {
    private Double sepalLength;
    private Double sepalWidth;
    private Double petalLength;
    private Double petalWidth;

    public IrisFeature() {
    }

    public IrisFeature(Double sepalLength, Double sepalWidth, Double petalLength, Double petalWidth) {
        this.sepalLength = sepalLength;
        this.sepalWidth = sepalWidth;
        this.petalLength = petalLength;
        this.petalWidth = petalWidth;
    }
}
