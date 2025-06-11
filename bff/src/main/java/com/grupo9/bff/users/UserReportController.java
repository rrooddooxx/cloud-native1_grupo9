package com.grupo9.bff.users;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/private/users")
@RequiredArgsConstructor
@Slf4j
public class UserReportController {

    private final UserReportService userReportService;

    @GetMapping("/{id}/report")
    public ResponseEntity<UserReportDTO> getUserReport(@PathVariable String id) {
        log.info("Getting report for user: {}", id);
        
        UserReportDTO report = userReportService.getUserReport(id);
        return ResponseEntity.ok(report);
    }
}