package ch.heigvd.amt.unicorns.entities;

import ch.heigvd.amt.unicorns.api.model.Unicorn;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * Created by Olivier Liechti on 26/07/17.
 */
@Entity
public class MagicEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private int power;
    private String spell;

    @ManyToMany(mappedBy = "magicEntities")
    private List<UnicornEntity> unicornEntities;


    public long getId() {
        return id;
    }

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
}
