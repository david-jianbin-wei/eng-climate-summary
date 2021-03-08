package com.david.engclimatesummary.mocks;

import com.david.engclimatesummary.model.Climate;
import com.david.engclimatesummary.model.ClimateSummary;
import com.david.engclimatesummary.model.Page;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class ClimateSummaryMocks {

    public static Page<ClimateSummary> getClimatesMock() {
        List<ClimateSummary> list = new ArrayList<>();
        list.add(ClimateSummary.builder().stationName("CHEMAINUS").date(LocalDate.of(2018, Month.JANUARY, 4)).meanTemp("15.1").build());
        list.add(ClimateSummary.builder().stationName("HAINES JUNCTION").date(LocalDate.of(2017, Month.NOVEMBER, 11)).meanTemp("6.2").build());
        list.add(ClimateSummary.builder().stationName("MUSKOKA").date(LocalDate.of(2017, Month.DECEMBER, 19)).meanTemp("15.9").build());
        Page<ClimateSummary> page = new Page<>(list);
        return page;
    }

    public static Climate getClimatesDetailsMock() {
        return Climate.builder()
                .stationName("CHEMAINUS")
                .province("BC")
                .date(LocalDate.of(2018, Month.JANUARY, 4))
                .meanTemp("15.1")
                .highestMonthlyMaxTemp("26.5")
                .lowestMonthlyMinTemp("7")
                .build();
    }

    public static List<Climate> getClimatesDetailsListMock() {
        List<Climate> list = new ArrayList<>();
        list.add(Climate.builder().stationName("CHEMAINUS")
                .province("BC")
                .date(LocalDate.of(2018, Month.JANUARY, 4))
                .meanTemp("15.1")
                .highestMonthlyMaxTemp("26.5")
                .lowestMonthlyMinTemp("7")
                .build());
        list.add(Climate.builder().stationName("HAINES JUNCTION")
                .province("YT")
                .date(LocalDate.of(2017, Month.NOVEMBER, 11))
                .meanTemp("6.3")
                .highestMonthlyMaxTemp("20.8")
                .lowestMonthlyMinTemp("-11")
                .build());
        list.add(Climate.builder().stationName("MUSKOKA")
                .province("ON")
                .date(LocalDate.of(2017, Month.DECEMBER, 19))
                .meanTemp("15.9")
                .highestMonthlyMaxTemp("31")
                .lowestMonthlyMinTemp("0.7")
                .build());
        return list;
    }
}
