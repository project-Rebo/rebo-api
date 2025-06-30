package hello.reboapi.domain.store_input.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hello.reboapi.domain.store_input.dto.StoreInputSaveRequest;
import hello.reboapi.domain.store_input.entity.StoreInput;
import hello.reboapi.domain.store_input.service.StoreInputService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/store_input")
@Slf4j
public class StoreInputController {
    
    private final StoreInputService storeInputService;

    @Operation(summary = "회원 지역 리스트", description = "회원 ID로 지역 리스트를 조회합니다")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "지역 리스트 조회 성공"),
        @ApiResponse(responseCode = "400", description = "잘못된 요청: 필수 파라미터 누락"),
        @ApiResponse(responseCode = "404", description = "회원을 찾을 수 없음")
    })
    @GetMapping("/list/{memberId}")
    public ResponseEntity<List<StoreInput>> getMemberStoreInputList(@PathVariable Long memberId) {
        List<StoreInput> storeInputList = storeInputService.getMemberStoreInputList(memberId);
        
        return ResponseEntity.ok(storeInputList);
    }

    @Operation(summary = "회원 상권 입력 저장", description = "회원 ID와 상권 입력 정보를 저장합니다")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "상권 입력 저장 성공"),
        @ApiResponse(responseCode = "400", description = "잘못된 요청: 필수 파라미터 누락"),
        @ApiResponse(responseCode = "404", description = "회원을 찾을 수 없음")
    })
    @PostMapping("/save")
    public ResponseEntity<StoreInput> saveStoreInput(@RequestParam Long memberId, @RequestBody StoreInputSaveRequest request) {
        log.info("Received StoreInput save request - memberId: {}, location: {}, category: {}, radius: {}", 
                 memberId, request.getLocation(), request.getCategory(), request.getRadius());
        
        StoreInput storeInput = storeInputService.saveStoreInput(memberId, request);
        
        return ResponseEntity.ok(storeInput);
    }
}
