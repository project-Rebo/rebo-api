package hello.reboapi.domain.region.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import hello.reboapi.domain.region.dto.RegionSaveRequest;
import hello.reboapi.domain.region.entity.Region;
import hello.reboapi.domain.region.repository.RegionRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RegionService {

    private final RegionRepository regionRepository;

    // 회원 지역 리스트 조회 (store_input을 통해 간접 조회)
    public List<Region> getMemberRegionList(Long storeInputId) {
        List<Region> regionList = regionRepository.findByStoreInputId(storeInputId);
        
        return regionList;
    }

    // 지역 저장 (storeInputId 기반)
    public Region saveRegion(Long storeInputId, RegionSaveRequest request) {
        
        Region region = Region.builder()
                .storeInputId(storeInputId)
                .roadAddress(request.getRoadAddress())
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
                
        return regionRepository.save(region);
    }
}
