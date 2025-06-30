package hello.reboapi.domain.store_input.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import hello.reboapi.domain.store_input.dto.StoreInputSaveRequest;
import hello.reboapi.domain.store_input.entity.StoreInput;
import hello.reboapi.domain.store_input.repository.StoreInputRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class StoreInputService {

    private final StoreInputRepository storeInputRepository;

    // 회원 상권 입력 리스트 조회
    public List<StoreInput> getMemberStoreInputList(Long memberId) {

        List<StoreInput> storeInputList = storeInputRepository.findByMemberId(memberId);
        
        return storeInputList;
    }

    // 회원 상권 입력 저장
    public StoreInput saveStoreInput(Long memberId, StoreInputSaveRequest request) {
        StoreInput storeInput = StoreInput.builder()
                .memberId(memberId)
                .location(request.getLocation())
                .category(request.getCategory())
                .radius(request.getRadius())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
                
        return storeInputRepository.save(storeInput);
    }
}
