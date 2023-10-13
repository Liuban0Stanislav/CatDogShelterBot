package pro.sky.telegrambot.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.model.Report;
import pro.sky.telegrambot.repository.ReportRepository;

@Service
@Slf4j
public class ReportService {
    private final ReportRepository reportRepository;

    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    public Report createReport(Report report) {
        log.info("Creating report: {}", report);
        return reportRepository.save(report);
    }

    public Report findReport(Long id) {
        if (reportRepository.findById(id).isPresent()) {
            var findedReport = reportRepository.findById(id).get();
            log.info("Report: {} found", findedReport);
            return findedReport;
        } else {
            throw new RuntimeException();
        }
    }

    public Report editReport(Report report) {
        log.info("The method editReport has invoked");
        return reportRepository.save(report);
    }

    public void deleteReport(Long id) {
        log.info("The method deleteReport has invoked");
        reportRepository.deleteById(id);
    }

}
