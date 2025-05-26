package hello.reboapi.domain.store.repository;

import hello.reboapi.domain.store.entity.Store;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class StoreRepositoryTest {

    @Mock
    private StoreRepository storeRepository;

    private Store createTestStore(String name, BigDecimal latitude, BigDecimal longitude) {
        return Store.builder()
                .name(name)
                .csvId("TEST001")
                .categorySmall("스터디카페")
                .latitude(latitude)
                .longitude(longitude)
                .build();
    }

    @Test
    @DisplayName("위치와 카테고리로 반경 내 매장을 검색한다")
    void findStoresByLocationAndCategoryTest() {
        // given
        Store store1 = createTestStore(
            "위넌 스터디카페",
            new BigDecimal("37.506322"),
            new BigDecimal("127.053834")
        );
        Store store2 = createTestStore(
            "다른 스터디카페",
            new BigDecimal("37.506000"),
            new BigDecimal("127.053000")
        );

        given(storeRepository.findStoresByLocationAndCategory(
            anyDouble(), anyDouble(), anyInt(), anyString()))
            .willReturn(Arrays.asList(store1, store2));

        // when
        List<Store> results = storeRepository.findStoresByLocationAndCategory(
            37.506322,
            127.053834,
            500,
            "스터디카페"
        );

        // then
        assertThat(results).isNotEmpty();
        assertThat(results).hasSize(2);
        assertThat(results).extracting("name")
            .containsExactlyInAnyOrder("위넌 스터디카페", "다른 스터디카페");
    }
} 