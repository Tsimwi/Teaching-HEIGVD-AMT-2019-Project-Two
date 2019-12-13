package ch.heigvd.amt.unicorns.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Olivier Liechti on 26/07/17.
 */
@Entity
public class UnicornEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String color;
    private boolean hasWings;
    private int speed;

    @ManyToMany
    private List<MagicEntity> magicEntities;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public boolean getHasWings() {
        return hasWings;
    }

    public void setHasWings(boolean hasWings) {
        this.hasWings = hasWings;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
