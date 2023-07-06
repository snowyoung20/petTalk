package com.example.pettalk.dto;

import lombok.Getter;

@Getter
public class GreatResponseDto {
    private String msg;

    public GreatResponseDto(String msg){
        this.msg = msg;
    }

}
