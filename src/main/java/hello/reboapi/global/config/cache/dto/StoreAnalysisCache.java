package hello.reboapi.global.config.cache.dto;

import java.io.Serializable;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StoreAnalysisCache implements Serializable {

   private final String region;
   private final String categoryLarge;
   private final String categoryMiddle;
   private final String categorySmall;
   private final int radius;
   private final double density;
   private final long totalStores;
   
   // 실제 사용된 카테고리를 반환하는 메서드
   public String getActiveCategory() {
       if (categorySmall != null && !categorySmall.trim().isEmpty()) {
           return categorySmall;
       }
       if (categoryMiddle != null && !categoryMiddle.trim().isEmpty()) {
           return categoryMiddle;
       }
       if (categoryLarge != null && !categoryLarge.trim().isEmpty()) {
           return categoryLarge;
       }
       return "전체";
   }
   
   // 하위 호환성을 위한 메서드 (기존 코드가 깨지지 않도록)
   @Deprecated
   public String getCategory() {
       return getActiveCategory();
   }
}
