package main.Entities;

import javax.persistence.*;

@Entity
@Table(name = "materials")
public class Material {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User userID;

    private int materialNumber;
    private int materialCount = 1;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUserID() {
        return userID;
    }

    public void setUserID(User userID) {
        this.userID = userID;
    }

    public int getMaterialNumber() {
        return materialNumber;
    }

    public void setMaterialNumber(int materialNumber) {
        this.materialNumber = materialNumber;
    }

    public Material() {
    }

    public Material(User userID, int materialNumber) {
        this.userID = userID;
        this.materialNumber = materialNumber;
    }

    public int getMaterialCount() {
        return materialCount;
    }

    public void setMaterialCount(int materialCount) {
        this.materialCount = materialCount;
    }
    public String getPhotoAddress(){
        return "images/img_"+String.valueOf(materialNumber) + ".png";
    }

    public String getPriceForOneMaterial() {
        return switch (this.materialNumber) {
            case 1 -> String.valueOf(340) + " руб.";
            case 2 -> String.valueOf(1790) + " руб.";
            case 3 -> String.valueOf(1368) + " руб.";
            case 4 -> String.valueOf(12014) + " руб.";
            case 5 -> String.valueOf(7262) + " руб.";
            case 6 -> String.valueOf(1998) + " руб.";
            case 7 -> String.valueOf(1890) + " руб.";
            case 8 -> String.valueOf(320) + " руб.";
            case 9 -> String.valueOf(2029) + " руб.";
            default -> "";
        };
    }

    public String getPriceForManyMaterials() {
        return switch (this.materialNumber) {
            case 1 -> String.valueOf(340 * materialCount) + " руб.";
            case 2 -> String.valueOf(1790 * materialCount) + " руб.";
            case 3 -> String.valueOf(1368 * materialCount) + " руб.";
            case 4 -> String.valueOf(12014 * materialCount) + " руб.";
            case 5 -> String.valueOf(7262 * materialCount) + " руб.";
            case 6 -> String.valueOf(1998 * materialCount) + " руб.";
            case 7 -> String.valueOf(1890 * materialCount) + " руб.";
            case 8 -> String.valueOf(320 * materialCount) + " руб.";
            case 9 -> String.valueOf(2029 * materialCount) + " руб.";
            default -> "";
        };
    }
    public String getItemName(){
        return switch (this.materialNumber) {
            case 1 -> "Stone artificial Artens gray";
            case 2 -> "Stone artificial White Hillsy";
            case 3 -> "Facade tiles HAUBERK terracotta brick";
            case 4 -> "Double plastic window";
            case 5 -> "Wooden window";
            case 6 -> "Aluminum balcony railing";
            case 7 -> "Laminate Artens \"Oak Gravis\"";
            case 8 -> "Linoleum \"Honey Oak\"";
            case 9 -> "Laminate Artens Mongolian Oak grey";
            default -> "";
        };
    }
}
