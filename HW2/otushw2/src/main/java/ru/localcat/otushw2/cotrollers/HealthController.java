package ru.localcat.otushw2.cotrollers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.localcat.otushw2.dtos.HealthStatusDto;

@RestController
@RequiredArgsConstructor
public class HealthController {


    @GetMapping("/health")
    public HealthStatusDto getResultHealth() {
        return new HealthStatusDto("OK");
    }


}
