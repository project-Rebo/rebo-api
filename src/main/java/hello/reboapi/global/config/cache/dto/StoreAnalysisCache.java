package hello.reboapi.global.config.cache.dto;

import java.io.Serializable;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StoreAnalysisCache implements Serializable {

   private final String region;
   private final String category;
   private final int radius;
   private final double desity;
   private final int totalStores;
}
