package com.david.engclimatesummary.controller;

import com.david.engclimatesummary.model.Climate;
import com.david.engclimatesummary.model.ClimateSummary;
import com.david.engclimatesummary.model.Page;
import com.david.engclimatesummary.model.PagingRequest;
import com.david.engclimatesummary.service.ClimateSummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@RestController
public class ClimateSummaryController {

    @Autowired
    private ClimateSummaryService climateService;

    @PostMapping(value = "/history", produces = {"application/json"})
    public ResponseEntity<Page<ClimateSummary>> getClimateSummary(@RequestBody PagingRequest pagingRequest) {
        return ResponseEntity.ok(climateService.getClimates(pagingRequest));
    }

    @GetMapping(value = "/history/{station}", produces = {"application/json"})
    public ResponseEntity<Climate> getClimateDetail(
            @PathVariable(value = "station") String station) {
        Climate res = climateService.getClimateDetail(station);
        if (res == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(res);
    }

}

