package com.david.engclimatesummary.controller;

import com.david.engclimatesummary.mocks.ClimateSummaryMocks;
import com.david.engclimatesummary.model.PagingRequest;
import com.david.engclimatesummary.model.Search;
import com.david.engclimatesummary.service.ClimateSummaryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ClimateSummaryControllerTest {

    @InjectMocks
    private ClimateSummaryController controller;

    @Mock
    private ClimateSummaryService climateService;


    @Test
    public void Given_ClimateSummaryController_when_getClimateSummary_then_success() {
        when(climateService.getClimates(any(PagingRequest.class))).thenReturn(ClimateSummaryMocks.getClimatesMock());

        ResponseEntity responseEntity = controller.getClimateSummary(PagingRequest.builder()
                .start(0)
                .length(10)
                .draw(1)
                .search(new Search())
                .minDate("")
                .maxDate("")
                .order(null)
                .columns(Collections.emptyList())
                .build());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity);
    }

    @Test
    public void Given_ClimateSummaryController_when_getClimateDetail_with_correct_station_then_success() {
        when(climateService.getClimateDetail("CHEMAINUS")).thenReturn(ClimateSummaryMocks.getClimatesDetailsMock());

        ResponseEntity responseEntity = controller.getClimateDetail("CHEMAINUS");
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity);
    }

    @Test
    public void Given_ClimateSummaryController_when_getClimateDetail_with_wrong_station_then_fail() {
        when(climateService.getClimateDetail("DUMMY")).thenReturn(null);

        ResponseEntity responseEntity = controller.getClimateDetail("DUMMY");
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNotNull(responseEntity);
    }

}
