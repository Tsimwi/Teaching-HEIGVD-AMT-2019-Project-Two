package ch.heigvd.amt.unicorns.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class UnicornEntity implements Serializable {

    @Id
    private String name;

    private String color;
    private boolean hasWings;
    private int speed;
    private String entityCreator;

    @ManyToMany
    private List<MagicEntity> magicEntities;

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

    public String getEntityCreator() {
        return entityCreator;
    }

    public void setEntityCreator(String entityCreator) {
        this.entityCreator = entityCreator;
    }
}
