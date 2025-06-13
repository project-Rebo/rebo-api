package hello.reboapi.domain.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import hello.reboapi.domain.store.entity.Store;

public interface StoreRepository extends JpaRepository<Store, Long> {
    
    @Query(value = """
            SELECT DISTINCT * FROM store 
            WHERE (
                category_large LIKE %:category% 
                OR category_middle LIKE %:category% 
                OR category_small LIKE %:category% 
                OR name LIKE %:category%
            ) 
            AND ST_DWithin(
                ST_SetSRID(ST_MakePoint(longitude, latitude), 4326)::geography,
                ST_SetSRID(ST_MakePoint(:longitude, :latitude), 4326)::geography,
                :radius
            )
            """, nativeQuery = true)
List<Store> findStoresByLocationAndCategory(
    @Param("latitude") Double latitude,
    @Param("longitude") Double longitude,
    @Param("radius") Integer radius,
    @Param("category") String category
);
}
