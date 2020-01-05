package ch.heigvd.amt.users.repositories;

import ch.heigvd.amt.users.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by Olivier Liechti on 26/07/17.
 */
public interface UserRepository extends CrudRepository<UserEntity, Long> {

    UserEntity findByMail(String mail);

    boolean existsByMail(String mail);
}
