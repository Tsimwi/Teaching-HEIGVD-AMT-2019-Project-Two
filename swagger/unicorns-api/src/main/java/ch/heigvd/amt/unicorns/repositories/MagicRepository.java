package ch.heigvd.amt.unicorns.repositories;

import ch.heigvd.amt.unicorns.entities.MagicEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

/**
 * Created by Olivier Liechti on 26/07/17.
 */
@Transactional
public interface MagicRepository extends PagingAndSortingRepository<MagicEntity, Long> {
    boolean existsByName(String name);
    List<MagicEntity> getMagicEntitiesByEntityCreator(String entity_creator, Pageable pageable);
    long countByEntityCreator(String creator);
    MagicEntity getMagicEntityByName(String name);
    Integer deleteByName(String name);
}
