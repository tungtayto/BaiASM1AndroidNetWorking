package com.example.baiasm1.model;

import com.google.gson.annotations.SerializedName;

public class Cars {
    @SerializedName("_id")
    private String id;
    @SerializedName("nameCar")
    private String nameCar;
    @SerializedName("colorCar")
    private String colorCar;
    @SerializedName("yearCar")
    private int yearCar;
    @SerializedName("engineTypeCar")
    private String engineTypeCar; // xang diesel dien
    @SerializedName("priceCar")
    private int priceCar;
    @SerializedName("quantityCar")
    private int quantityCar;

    private String imgCar;

    public Cars() {
    }

    public Cars(String id, String nameCar, String colorCar, int yearCar, String engineTypeCar, int priceCar, int quantityCar, String imgCar) {
        this.id = id;
        this.nameCar = nameCar;
        this.colorCar = colorCar;
        this.yearCar = yearCar;
        this.engineTypeCar = engineTypeCar;
        this.priceCar = priceCar;
        this.quantityCar = quantityCar;
        this.imgCar = imgCar;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNameCar() {
        return nameCar;
    }

    public void setNameCar(String nameCar) {
        this.nameCar = nameCar;
    }

    public String getColorCar() {
        return colorCar;
    }

    public void setColorCar(String colorCar) {
        this.colorCar = colorCar;
    }

    public int getYearCar() {
        return yearCar;
    }

    public void setYearCar(int yearCar) {
        this.yearCar = yearCar;
    }

    public String getEngineTypeCar() {
        return engineTypeCar;
    }

    public void setEngineTypeCar(String engineTypeCar) {
        this.engineTypeCar = engineTypeCar;
    }

    public int getPriceCar() {
        return priceCar;
    }

    public void setPriceCar(int priceCar) {
        this.priceCar = priceCar;
    }

    public int getQuantityCar() {
        return quantityCar;
    }

    public void setQuantityCar(int quantityCar) {
        this.quantityCar = quantityCar;
    }

    public String getImgCar() {
        return imgCar;
    }

    public void setImgCar(String imgCar) {
        this.imgCar = imgCar;
    }
}
