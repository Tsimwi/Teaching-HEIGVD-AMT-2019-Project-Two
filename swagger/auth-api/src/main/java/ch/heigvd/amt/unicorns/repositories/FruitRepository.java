package ch.heigvd.amt.unicorns.repositories;

import ch.heigvd.amt.unicorns.entities.FruitEntity;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Olivier Liechti on 26/07/17.
 */
public interface FruitRepository extends CrudRepository<FruitEntity, Long>{

}