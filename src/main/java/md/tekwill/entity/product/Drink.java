package md.tekwill.entity.product;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.LocalDate;
import java.util.Objects;
@Entity
@DiscriminatorValue("DRINK")
public class Drink extends Product {
    private static final double DISCOUNT = 0.5;
    @Column (name="volume")
    private double volume;

    public Drink(){
        //used by ORM
    }

    public Drink(String name, double price, LocalDate bestBefore, double volume, Vendor vendor) {
        super(name, price, bestBefore, vendor);
        this.volume = volume;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    @Override
    public double getPriceOnBill() {

        return (bestBefore.equals(LocalDate.now()) || bestBefore.equals(LocalDate.now().plusDays(1))) ? price * DISCOUNT : price;

    }


    @Override
    public String getPrintText() {
        return "[" + id + "]" + "DRINK: " + name + " | " + price + " | " + bestBefore + " | " + volume;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        if (!super.equals(obj)) return false;
        Drink drink = (Drink) obj;
        return Double.compare(drink.DISCOUNT, DISCOUNT) == 0 && Double.compare(drink.volume, volume) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), DISCOUNT, volume);
    }

    @Override
    public String toString() {

        return getPrintText();
    }


}
