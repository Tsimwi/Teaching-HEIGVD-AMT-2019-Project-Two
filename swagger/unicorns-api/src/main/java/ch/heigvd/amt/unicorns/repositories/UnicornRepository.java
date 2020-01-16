package ch.heigvd.amt.unicorns.repositories;

import ch.heigvd.amt.unicorns.entities.UnicornEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Olivier Liechti on 26/07/17.
 */
@Transactional
public interface UnicornRepository extends PagingAndSortingRepository<UnicornEntity, Long> {
    boolean existsByName(String name);
//    List<UnicornEntity> getUnicornEntitiesByEntityCreator(String entity_creator);
    List<UnicornEntity> getUnicornEntitiesByEntityCreator(String entity_creator, Pageable pageable);
    long countByEntityCreator(String creator);
    UnicornEntity getUnicornEntityByName(String name);
    Integer deleteByName(String name);
}
