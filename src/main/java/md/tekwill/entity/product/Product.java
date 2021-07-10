package md.tekwill.entity.product;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn (name="product_type", discriminatorType = DiscriminatorType.STRING)
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
public abstract class Product implements Billable, Printable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name="id")
    protected int id;

    @Column(name = "name")
    protected String name;

    @Column(name="price")
    protected double price;

    @Column(name="best_before")
    protected LocalDate bestBefore;

    @ManyToOne
    @JoinColumn(name="vendor_id")
    private Vendor vendor;

    protected Product(){
        //used by ORM
    }
    protected Product(String name, double price, LocalDate bestBefore, Vendor vendor) {
        this.name = name;
        this.price = price;
        this.bestBefore = bestBefore;
        this.vendor=vendor;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public LocalDate getBestBefore() {
        return bestBefore;
    }

    public void setName(String name){
        this.name=name;
    }
    public void setBestBefore(LocalDate bestBefore){
        this.bestBefore=bestBefore;
    }
    public Vendor getVendor(){
        return vendor;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Product product = (Product) obj;
        return id == product.id && Double.compare(product.price, price) == 0 && name.equals(product.name) && bestBefore.equals(product.bestBefore);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price, bestBefore);
    }


}
