/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

/**
 *
 * @author leith
 */

public class Evenement {
    private int id;
    private String name;
    private double price;
    private String date;
    private String description;
    private String image;
    private String place;

    // Constructors
   public Evenement(int id, String name, double price, String date, String description, String image, String place) {
    this.id = id;
    this.name = name;
    this.price = price;
    this.date = date;
    this.description = description;
    this.image = image;
    this.place = place;
}

public Evenement(String name, double price, String date, String description, String image, String place) {
    this.name = name;
    this.price = price;
    this.date = date;
    this.description = description;
    this.image = image;
    this.place = place;
}
    public Evenement() {
    }

    // Getter and Setter methods
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    
    public String getPlace() {
        return place;
    }

    public void setPlace(String image) {
        this.place = place;
    }

    @Override
    public String toString() {
        return "Evenement{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", date='" + date + '\'' +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                ", place='" + place + '\'' +
                '}';
    }
}
