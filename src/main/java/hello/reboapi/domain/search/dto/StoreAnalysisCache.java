package hello.reboapi.domain.search.dto;

import java.io.Serializable;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreAnalysisCache implements Serializable {

   private String region;
   private String categoryLarge;
   private String categoryMiddle;
   private String categorySmall;
   private String placeName;
   private String roadAddress;
   private int radius;
   private double density;
   private long totalStores;
   private Double latitude;
   private Double longitude;

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

   @Deprecated
   public String getCategory() {
       return getActiveCategory();
   }
}
