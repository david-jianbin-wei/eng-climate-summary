package com.david.engclimatesummary.service;

import com.david.engclimatesummary.model.Climate;
import com.david.engclimatesummary.model.ClimateSummary;
import com.david.engclimatesummary.model.Page;
import com.david.engclimatesummary.model.PagingRequest;

public interface ClimateSummaryService {
    public Page<ClimateSummary> getClimates(PagingRequest pagingRequest);

    public Climate getClimateDetail(String stationName);
}
