package com.example.pettalk.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Getter
@NoArgsConstructor
@ToString
public class StatusResult {
    private String msg;
    private int statusCode;

    @Builder
    public StatusResult(String msg, int statusCode){
        this.msg = msg;
        this.statusCode = statusCode;
    }
}
