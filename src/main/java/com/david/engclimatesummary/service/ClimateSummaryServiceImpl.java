package com.david.engclimatesummary.service;

import com.david.engclimatesummary.dao.ClimateSummaryDao;
import com.david.engclimatesummary.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ClimateSummaryServiceImpl implements ClimateSummaryService {

    private static final Comparator<Climate> EMPTY_COMPARATOR = (e1, e2) -> 0;

    private final ClimateSummaryDao dao;

    @Autowired
    public ClimateSummaryServiceImpl(ClimateSummaryDao dao) {
        this.dao = dao;
    }

    public Page<ClimateSummary> getClimates(PagingRequest pagingRequest) {
        List<ClimateSummary> filtered = dao.getClimateDetailsList().stream()
                .sorted(sortClimates(pagingRequest))
                .filter(searchFilterClimates(pagingRequest))
                .filter(dateRangeFilterClimates(pagingRequest))
                .skip(pagingRequest.getStart())
                .limit(pagingRequest.getLength())
                .map(p -> ClimateSummary.builder().stationName(p.getStationName()).date(p.getDate()).meanTemp(p.getMeanTemp()).build())
                .collect(Collectors.toList());

        long count = dao.getClimateDetailsList().stream()
                .filter(searchFilterClimates(pagingRequest))
                .filter(dateRangeFilterClimates(pagingRequest))
                .count();

        Page<ClimateSummary> page = new Page<>(filtered);
        page.setRecordsFiltered((int) count);
        page.setRecordsTotal((int) count);
        page.setDraw(pagingRequest.getDraw());

        return page;
    }

    public Climate getClimateDetail(String stationName) {
        return dao.getClimateDetailsList()
                .stream()
                .filter((climateDetails -> stationName.equalsIgnoreCase(climateDetails.getStationName())))
                .findFirst()
                .orElse(null);
    }

    private Predicate<Climate> searchFilterClimates(PagingRequest pagingRequest) {
        if (pagingRequest.getSearch() == null || !StringUtils.hasLength(pagingRequest.getSearch()
                .getValue())) {
            return climate -> true;
        }

        String value = pagingRequest.getSearch()
                .getValue().toLowerCase();

        return climate -> climate.getStationName()
                .toLowerCase()
                .contains(value)
                || climate.getDate().toString()
                .contains(value)
                || climate.getMeanTemp()
                .contains(value);
    }

    private Predicate<Climate> dateRangeFilterClimates(PagingRequest pagingRequest) {
        LocalDate minDate = LocalDate.MIN;
        LocalDate maxDate = LocalDate.MAX;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        if (pagingRequest.getMinDate() != null && StringUtils.hasLength(pagingRequest.getMinDate())) {
            try {
                minDate = LocalDate.parse(pagingRequest.getMinDate(), formatter);
            } catch (DateTimeParseException e) {
                // keep MIN value
            }
        }

        if (pagingRequest.getMaxDate() != null && StringUtils.hasLength(pagingRequest.getMaxDate())) {
            try {
                maxDate = LocalDate.parse(pagingRequest.getMaxDate(), formatter);
            } catch (DateTimeParseException e) {
                // keep MAX value
            }
        }

        final LocalDate finalMinDate = minDate;
        final LocalDate finalMaxDate = maxDate;
        return climate -> climate.getDate().isAfter(finalMinDate) && climate.getDate().isBefore(finalMaxDate);
    }

    private Comparator<Climate> sortClimates(PagingRequest pagingRequest) {
        if (pagingRequest.getOrder() == null) {
            return EMPTY_COMPARATOR;
        }

        try {
            Order order = pagingRequest.getOrder()
                    .get(0);

            int columnIndex = order.getColumn();
            Column column = pagingRequest.getColumns()
                    .get(columnIndex);

            Comparator<Climate> comparator = ClimateComparators.getComparator(column.getData(), order.getDir());
            if (comparator == null) {
                return EMPTY_COMPARATOR;
            }

            return comparator;

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return EMPTY_COMPARATOR;
    }
}
