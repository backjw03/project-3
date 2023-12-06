package com.project.lolchess.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class ReplyDto {
    private int r_num;
    private int r_bnum;
    private String r_contents;
    private String r_id;
    private Timestamp r_date;
}
