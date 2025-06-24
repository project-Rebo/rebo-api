package hello.reboapi.domain.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import hello.reboapi.domain.store.entity.Store;


import org.springframework.stereotype.Repository;


@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {

    // 카테고리(대분류) density 계산용 count
    @Query(value = """
        SELECT COUNT(*)
        FROM store
        WHERE
            ST_DWithin(
                ST_SetSRID(ST_MakePoint(longitude, latitude), 4326)::geography,
                ST_SetSRID(ST_MakePoint(:longitude, :latitude), 4326)::geography,
                :radius
            )
            AND category_large = :categoryLarge
            AND name LIKE CONCAT('%', :searchName, '%')
            AND is_deleted = FALSE
        """, nativeQuery = true)
    Long countStoresByCategoryLarge(
        @Param("latitude") Double latitude,
        @Param("longitude") Double longitude,
        @Param("radius") Integer radius,
        @Param("categoryLarge") String categoryLarge,
        @Param("searchName") String searchName
    );

    // 카테고리(중분류) density 계산용 count
    @Query(value = """
        SELECT COUNT(*)
        FROM store
        WHERE
            ST_DWithin(
                ST_SetSRID(ST_MakePoint(longitude, latitude), 4326)::geography,
                ST_SetSRID(ST_MakePoint(:longitude, :latitude), 4326)::geography,
                :radius
            )
            AND category_middle = :categoryMiddle
            AND name LIKE CONCAT('%', :searchName, '%')
            AND is_deleted = FALSE
        """, nativeQuery = true)
    Long countStoresByCategoryMiddle(
        @Param("latitude") Double latitude,
        @Param("longitude") Double longitude,
        @Param("radius") Integer radius,
        @Param("categoryMiddle") String categoryMiddle,
        @Param("searchName") String searchName
    );

    // 카테고리(소분류) density 계산용 count
    @Query(value = """
        SELECT COUNT(*)
        FROM store
        WHERE
            ST_DWithin(
                ST_SetSRID(ST_MakePoint(longitude, latitude), 4326)::geography,
                ST_SetSRID(ST_MakePoint(:longitude, :latitude), 4326)::geography,
                :radius
            )
            AND category_small = :categorySmall
            AND name LIKE CONCAT('%', :searchName, '%')
            AND is_deleted = FALSE
        """, nativeQuery = true)
    Long countStoresByCategorySmall(
        @Param("latitude") Double latitude,
        @Param("longitude") Double longitude,
        @Param("radius") Integer radius,
        @Param("categorySmall") String categorySmall,
        @Param("searchName") String searchName
    );

    // 카테고리(전체) density 계산용 count - 복합 인덱스 최적화
    @Query(value = """
        SELECT COUNT(*)
        FROM (
            SELECT id FROM store
            WHERE ST_DWithin(
                ST_SetSRID(ST_MakePoint(longitude, latitude), 4326)::geography,
                ST_SetSRID(ST_MakePoint(:longitude, :latitude), 4326)::geography,
                :radius
            )
            AND category_large = :categoryLarge
            AND name LIKE CONCAT('%', :searchName, '%')
            AND is_deleted = FALSE
            
            UNION
            
            SELECT id FROM store
            WHERE ST_DWithin(
                ST_SetSRID(ST_MakePoint(longitude, latitude), 4326)::geography,
                ST_SetSRID(ST_MakePoint(:longitude, :latitude), 4326)::geography,
                :radius
            )
            AND category_middle = :categoryMiddle
            AND name LIKE CONCAT('%', :searchName, '%')
            AND is_deleted = FALSE
            
            UNION
            
            SELECT id FROM store
            WHERE ST_DWithin(
                ST_SetSRID(ST_MakePoint(longitude, latitude), 4326)::geography,
                ST_SetSRID(ST_MakePoint(:longitude, :latitude), 4326)::geography,
                :radius
            )
            AND category_small = :categorySmall
            AND name LIKE CONCAT('%', :searchName, '%')
            AND is_deleted = FALSE
        ) AS combined_results
        """, nativeQuery = true)
    Long countStoresByCategoryAll(
        @Param("latitude") Double latitude,
        @Param("longitude") Double longitude,
        @Param("radius") Integer radius,
        @Param("categoryLarge") String categoryLarge,
        @Param("categoryMiddle") String categoryMiddle,
        @Param("categorySmall") String categorySmall,
        @Param("searchName") String searchName
    ); 
}
