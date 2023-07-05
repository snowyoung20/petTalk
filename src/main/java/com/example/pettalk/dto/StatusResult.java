package com.example.pettalk.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;


@Getter
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StatusResult {
    private String msg;
    private int statusCode;

    @Builder
    public StatusResult(String msg, Integer statusCode){
        this.msg = msg;
        this.statusCode = statusCode;
    }
}
