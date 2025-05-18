package hello.reboapi.domain.store_input.repository;

import hello.reboapi.domain.store_input.entity.StoreInput;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreInputRepository extends JpaRepository<StoreInput, Long> {
}
