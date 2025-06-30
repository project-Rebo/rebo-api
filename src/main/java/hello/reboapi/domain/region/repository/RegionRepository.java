package hello.reboapi.domain.region.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import hello.reboapi.domain.region.entity.Region;

public interface RegionRepository extends JpaRepository<Region, Long> {

    List<Region> findByStoreInputId(Long storeInputId);
}
