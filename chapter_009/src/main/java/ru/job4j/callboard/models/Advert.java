package ru.job4j.callboard.models;

/**
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 15.10.2018
 */
public class Advert {
    private int vin;
    private String title;
    private boolean status = true;
    private int price;
    private String description;
    private int mileage;
    private String photo;
    private User owner;
    private Color color;
    private Brand brand;
    private Model model;

    public Advert() {

    }

    public Advert(final int vin) {
        this.vin = vin;
    }

    public Advert(final int vin, final String title, final int price, final String desc, final int mileage) {
        this.vin = vin;
        this.title = title;
        this.price = price;
        this.description = desc;
        this.mileage = mileage;
    }

    public int getVin() {
        return vin;
    }

    public String getTitle() {
        return title;
    }

    public boolean isStatus() {
        return status;
    }

    public int getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public int getMileage() {
        return mileage;
    }

    public User getOwner() {
        return owner;
    }

    public Color getColor() {
        return color;
    }

    public Brand getBrand() {
        return brand;
    }

    public Model getModel() {
        return model;
    }

    public String getPhoto() {
        return photo;
    }

    public void setVin(final int vin) {
        this.vin = vin;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public void setStatus(final boolean status) {
        this.status = status;
    }

    public void setPrice(final int price) {
        this.price = price;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public void setMileage(final int mileage) {
        this.mileage = mileage;
    }

    public void setOwner(final User owner) {
        this.owner = owner;
    }

    public void setColor(final Color color) {
        this.color = color;
    }

    public void setBrand(final Brand brand) {
        this.brand = brand;
    }

    public void setModel(final Model model) {
        this.model = model;
    }

    public void setPhoto(final String photo) {
        this.photo = photo;
    }

    public void changeStatus() {
        this.status = !this.status;
    }

    @Override
    public String toString() {
        return "Advert{"
                + "vin=" + vin
                + ", title='" + title + '\''
                + ", status=" + status
                + ", price=" + price
                + ", desc='" + description + '\''
                + ", mileage=" + mileage
                + ", owner=" + owner
                + ", color=" + color
                + ", brand=" + brand
                + ", model=" + model
                + ", photo=" + photo
                + '}';
    }
}
