package com.david.engclimatesummary.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class Climate {

    @JsonProperty("Station_Name")
    private String stationName;

    @JsonProperty("Date")
    private LocalDate date;

    @JsonProperty("Mean_Temp")
    private String meanTemp;

    private String province;
    private String highestMonthlyMaxTemp;
    private String lowestMonthlyMinTemp;
}
