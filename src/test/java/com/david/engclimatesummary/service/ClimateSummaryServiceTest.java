package com.david.engclimatesummary.service;

import com.david.engclimatesummary.dao.ClimateSummaryDao;
import com.david.engclimatesummary.mocks.ClimateSummaryMocks;
import com.david.engclimatesummary.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.Month;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class ClimateSummaryServiceTest {

    private ClimateSummaryServiceImpl service;

    @Mock
    private ClimateSummaryDao daoMocks;

    @BeforeEach
    public void setUp() throws Exception {
        service = new ClimateSummaryServiceImpl(daoMocks);
    }

    @Test
    public void Given_ClimateSummaryService_when_getClimates_then_success() {
        when(daoMocks.getClimateDetailsList()).thenReturn(ClimateSummaryMocks.getClimatesDetailsListMock());

        Page<ClimateSummary> res = service.getClimates(PagingRequest.builder()
                .start(0)
                .length(10)
                .draw(1)
                .search(new Search())
                .minDate("")
                .maxDate("")
                .order(null)
                .columns(Collections.emptyList())
                .build());
        assertNotNull(res);
        assertEquals(res.getData().get(0).getStationName(), "CHEMAINUS");
        assertEquals(res.getData().get(0).getDate(), LocalDate.of(2018, Month.JANUARY, 4));
        assertEquals(res.getData().get(0).getMeanTemp(), "15.1");

    }

    @Test
    public void Given_ClimateSummaryService_when_getClimateDetail_with_correct_station_then_success() {
        when(daoMocks.getClimateDetailsList()).thenReturn(ClimateSummaryMocks.getClimatesDetailsListMock());

        Climate res = service.getClimateDetail("HAINES JUNCTION");
        assertNotNull(res);
        assertEquals(res.getStationName(), "HAINES JUNCTION");
        assertEquals(res.getProvince(), "YT");
        assertEquals(res.getDate(), LocalDate.of(2017, Month.NOVEMBER, 11));
        assertEquals(res.getMeanTemp(), "6.3");
        assertEquals(res.getHighestMonthlyMaxTemp(), "20.8");
        assertEquals(res.getLowestMonthlyMinTemp(), "-11");
    }

    @Test
    public void Given_ClimateSummaryService_when_getClimateDetail_with_wrong_station_then_return_null() {
        when(daoMocks.getClimateDetailsList()).thenReturn(ClimateSummaryMocks.getClimatesDetailsListMock());

        Climate res = service.getClimateDetail("DUMMY");
        assertNull(res);
    }
}
