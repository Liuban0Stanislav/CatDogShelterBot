package pro.sky.telegrambot.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.telegrambot.model.Report;
import pro.sky.telegrambot.services.ReportService;

@RestController
@RequestMapping("/report")
@Slf4j
public class ReportController {
    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping
    public ResponseEntity<Report> createReport(@RequestBody Report report) {
        log.info("Вызван метод ReportController.createReport , получен Report ={}", report);
        return ResponseEntity.ok(reportService.createReport(report));
    }

    @PutMapping
    public ResponseEntity<Report> editReport(@RequestBody Report report) {
        Report foundReport = reportService.editReport(report);
        if (foundReport == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(report);
    }

    @DeleteMapping("{id}")
    public void deleteReport(@PathVariable Long id) {
        reportService.deleteReport(id);
    }

    @GetMapping("{id}")
    public ResponseEntity<Report> getReport(@PathVariable Long id) {
        Report report = reportService.findReport(id);
        if (report == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(report);
    }
}
