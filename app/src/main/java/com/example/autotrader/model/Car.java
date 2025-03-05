package com.example.autotrader.model;

public class Car {
    private int id;
    private String make;
    private String model;
    private int year;
    private double price;
    private String imageUrl;
    private String mileage;
    private String fuelType;
    private String transmission;
    private String engineSize;
    private boolean isSelected;

    public Car(int id, String make, String model, int year, double price, String imageUrl,
               String mileage, String fuelType, String transmission, String engineSize) {
        this.id = id;
        this.make = make;
        this.model = model;
        this.year = year;
        this.price = price;
        this.imageUrl = imageUrl;
        this.mileage = mileage;
        this.fuelType = fuelType;
        this.transmission = transmission;
        this.engineSize = engineSize;
        this.isSelected = false;
    }

    // Getters
    public int getId() { return id; }
    public String getMake() { return make; }
    public String getModel() { return model; }
    public int getYear() { return year; }
    public double getPrice() { return price; }
    public String getImageUrl() { return imageUrl; }
    public String getMileage() { return mileage; }
    public String getFuelType() { return fuelType; }
    public String getTransmission() { return transmission; }
    public String getEngineSize() { return engineSize; }
    public boolean isSelected() { return isSelected; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setMake(String make) { this.make = make; }
    public void setModel(String model) { this.model = model; }
    public void setYear(int year) { this.year = year; }
    public void setPrice(double price) { this.price = price; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public void setMileage(String mileage) { this.mileage = mileage; }
    public void setFuelType(String fuelType) { this.fuelType = fuelType; }
    public void setTransmission(String transmission) { this.transmission = transmission; }
    public void setEngineSize(String engineSize) { this.engineSize = engineSize; }
    public void setSelected(boolean selected) { isSelected = selected; }

    @Override
    public String toString() {
        return year + " " + make + " " + model;
    }
}
