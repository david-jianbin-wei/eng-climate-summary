package com.david.engclimatesummary.dao;

import com.david.engclimatesummary.model.Climate;
import de.siegmar.fastcsv.reader.CsvReader;
import de.siegmar.fastcsv.reader.CsvRow;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static java.nio.charset.StandardCharsets.UTF_8;

@Component
public class ClimateSummaryDao {

    @Value("${source.filename}")
    private String fileName;
    private List<Climate> climateDetailsList;

    public List<Climate> getClimateDetailsList() {
        return climateDetailsList;
    }

    @PostConstruct
    private void setup() {
        try {
            climateDetailsList = new ArrayList<>();
            URL url = getClass().getClassLoader().getResource(fileName);
            Path path = Paths.get(url.toURI());
            CsvReader csvReader = CsvReader.builder().build(path, UTF_8);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            csvReader.forEach((CsvRow row) -> {
                if (row.getOriginalLineNumber() != 1) {
                    Climate  climateDetails = Climate.builder()
                        .stationName(row.getField(0))
                        .province(row.getField(1))
                        .date(LocalDate.parse(row.getField(2), formatter))
                        .meanTemp(row.getField(3))
                        .highestMonthlyMaxTemp(row.getField(4))
                        .lowestMonthlyMinTemp(row.getField(5)).build();
                    climateDetailsList.add(climateDetails);
                }
            });
        } catch (final IOException | URISyntaxException e) {
            throw new UncheckedIOException((IOException) e);
        }
    }

}
