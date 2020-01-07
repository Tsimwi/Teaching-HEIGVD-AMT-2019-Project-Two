package ch.heigvd.amt.unicorns.repositories;

import ch.heigvd.amt.unicorns.entities.MagicEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by Olivier Liechti on 26/07/17.
 */
public interface MagicRepository extends PagingAndSortingRepository<MagicEntity, Long> {
    boolean existsByName(String name);
    List<MagicEntity> getMagicEntitiesByEntityCreator(String entity_creator);
    MagicEntity getMagicEntityByName(String name);
}
