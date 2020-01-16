package ch.heigvd.amt.unicorns.entities;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@OnDelete(action = OnDeleteAction.CASCADE)
public class UnicornEntity implements Serializable {

    @Id
    private String name;

    private String color;
    private boolean hasWings;
    private int speed;
    private String entityCreator;

    @ManyToMany (fetch = FetchType.LAZY)
    /*@JoinTable(
            name = "magicorn",
            joinColumns = @JoinColumn(name = "unicorn_name"),
            inverseJoinColumns = @JoinColumn(name = "magic_name"))*/
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

    public List<MagicEntity> getMagicEntities() {
        return magicEntities;
    }
}
