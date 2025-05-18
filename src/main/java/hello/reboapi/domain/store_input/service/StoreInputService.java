package hello.reboapi.domain.store_input.service;

import hello.reboapi.domain.store_input.repository.StoreInputRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoreInputService {

    private final StoreInputRepository storeInputRepository;

    @Transactional
    public void searchInput() {

    }
}
