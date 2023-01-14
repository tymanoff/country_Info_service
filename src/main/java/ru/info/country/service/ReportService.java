package ru.info.country.service;

import lombok.RequiredArgsConstructor;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;
import ru.info.country.entity.SuccessResponseCount;
import ru.info.country.repository.SuccessResponseCountRepository;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final SuccessResponseCountRepository successResponseCountRepository;

    public void writeFile(File file) throws IOException {

        List<SuccessResponseCount> successResponseCounts = successResponseCountRepository.getServiceGroupedList();

        try (Workbook workbook = new HSSFWorkbook()) {
            try (OutputStream fileOut = new FileOutputStream(file)) {
                Sheet sheet = workbook.createSheet("Sheet 1");
                sheet.autoSizeColumn(0);

                fillShell(sheet, successResponseCounts);

                workbook.write(fileOut);
            }
        }
    }

    private void fillShell(Sheet sheet, List<SuccessResponseCount> successResponseCounts) {
        int indexRow = 0;
        Row row = sheet.createRow(indexRow);
        row.createCell(0).setCellValue("Название сервиса");
        row.createCell(1).setCellValue("Кешированные запросы");
        row.createCell(2).setCellValue("Платные запросы");

        for (SuccessResponseCount successResponseCount : successResponseCounts) {
            int indexCell = 0;
            indexRow++;
            row = sheet.createRow(indexRow);

            row.createCell(indexCell).setCellValue(String.format("%s:%d",
                    successResponseCount.getService(), successResponseCount.getVersion()));
            indexCell++;

            row.createCell(indexCell).setCellValue(successResponseCount.getCachedSuccessResponseCount());
            indexCell++;

            row.createCell(indexCell).setCellValue(successResponseCount.getPaidSuccessResponseCount());
        }
    }
}
