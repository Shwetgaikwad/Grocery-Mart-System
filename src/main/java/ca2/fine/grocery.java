package ca2.fine;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "grocery")
public class grocery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "gname")
    private String gname;

    @Column(name = "price")
    private int price;

    @Column(name = "quantity")
    private String quantity;

    public grocery() {
        // Default constructor required by Hibernate
    }

    public grocery(String gname, int price, String quantity) {
        this.gname = gname;
        this.price = price;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getgname() {
        return gname;
    }

    public void setgame(String gname) {
        this.gname = gname;
    }

    public int getprice() {
        return price;
    }

    public void setprice(int aprice) {
        this.price = price;
    }

    public String getGender() {
        return quantity;
    }

    public void setGender(String gender) {
        this.quantity = gender;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + gname + ", price: " + price + ", quantity: " + quantity;
    }
}


