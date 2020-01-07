package ch.heigvd.amt.unicorns.repositories;

import ch.heigvd.amt.unicorns.entities.UnicornEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by Olivier Liechti on 26/07/17.
 */
public interface UnicornRepository extends PagingAndSortingRepository<UnicornEntity, Long> {
    boolean existsByName(String name);
    List<UnicornEntity> getUnicornEntitiesByEntityCreator(String entity_creator);
    UnicornEntity getUnicornEntityByName(String name);
}
