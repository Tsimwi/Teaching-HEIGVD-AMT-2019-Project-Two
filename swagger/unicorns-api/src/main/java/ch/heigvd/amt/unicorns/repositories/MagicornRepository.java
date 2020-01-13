package ch.heigvd.amt.unicorns.repositories;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Olivier Liechti on 26/07/17.
 */
@Transactional
public interface MagicornRepository extends PagingAndSortingRepository<MagicornEntity, Long> {

}
