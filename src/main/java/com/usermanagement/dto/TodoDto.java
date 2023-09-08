package com.usermanagement.dto;


import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TodoDto {

    private Long id;
    @NotEmpty(message = "Todo title not be null or empty")
    private String title;
    @NotEmpty(message = "Todo description not be null or empty")
    private String description;
    private boolean completed;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date createdDate;
   // @NotEmpty(message = "Todo target date not be null or empty")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date targetDate;
    private Long userId;
}
