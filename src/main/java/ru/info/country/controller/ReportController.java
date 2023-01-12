package ru.info.country.controller;

import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.info.country.service.ReportService;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping(path = "report")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping(path = "request-success-counts")
    public ResponseEntity<byte[]> createReportXlsSync() throws IOException {
        String fileName = "statistic_report";
        File reportTmpFile = File.createTempFile(fileName, ".xls");
        String attachmentFileName = String.format("%s_%s.xls", fileName,
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy_HH-mm-ss")));

        reportService.writeFile(reportTmpFile);

        try (BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(reportTmpFile))) {
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + attachmentFileName)
                    .body(IOUtils.toByteArray(inputStream));
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            FileUtils.forceDelete(reportTmpFile);
        }
    }
}
