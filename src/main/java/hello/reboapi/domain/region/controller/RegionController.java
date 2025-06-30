package hello.reboapi.domain.region.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hello.reboapi.domain.region.dto.RegionSaveRequest;
import hello.reboapi.domain.region.entity.Region;
import hello.reboapi.domain.region.service.RegionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;


@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/region")
public class RegionController {
    
    private final RegionService regionService;

    @Operation(summary = "회원 지역 리스트", description = "회원 ID로 지역 리스트를 조회합니다")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "지역 리스트 조회 성공"),
        @ApiResponse(responseCode = "400", description = "잘못된 요청: 필수 파라미터 누락"),
        @ApiResponse(responseCode = "404", description = "회원을 찾을 수 없음")
    })
    @GetMapping("/list/{memberId}")
    public ResponseEntity<List<Region>> getMemberRegionList(@PathVariable Long memberId) {
        List<Region> regionList = regionService.getMemberRegionList(memberId);
        
        return ResponseEntity.ok(regionList);
    }

    @Operation(summary = "지역 저장", description = "storeInputId와 지역 정보를 저장합니다")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "지역 저장 성공"),
        @ApiResponse(responseCode = "400", description = "잘못된 요청: 필수 파라미터 누락"),
        @ApiResponse(responseCode = "404", description = "StoreInput을 찾을 수 없음")
    })
    @PostMapping("/save")
    public ResponseEntity<Region> saveRegion(@RequestParam Long storeInputId, @RequestBody RegionSaveRequest request) {
        Region region = regionService.saveRegion(storeInputId, request);
        
        return ResponseEntity.ok(region);
    }
}
