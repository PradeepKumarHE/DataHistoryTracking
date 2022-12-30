package com.pradeep.DataEnverseAduit;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomRevisionsRequestDto {
    private int id;
    private LocalDate startDate;
    private LocalDate endDate;
}
