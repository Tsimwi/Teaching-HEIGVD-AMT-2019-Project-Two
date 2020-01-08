package ch.heigvd.amt.unicorns.entities;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@OnDelete(action = OnDeleteAction.CASCADE)
public class MagicEntity implements Serializable {

    @Id
    private String name;

    private int power;
    private String spell;
    private String entityCreator;

    @ManyToMany(mappedBy = "magicEntities")
    private List<UnicornEntity> unicornEntities;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public String getSpell() {
        return spell;
    }

    public void setSpell(String spell) {
        this.spell = spell;
    }

    public String getEntityCreator() {
        return entityCreator;
    }

    public void setEntityCreator(String entityCreator) {
        this.entityCreator = entityCreator;
    }

    public List<UnicornEntity> getUnicornEntities() {
        return unicornEntities;
    }
}
