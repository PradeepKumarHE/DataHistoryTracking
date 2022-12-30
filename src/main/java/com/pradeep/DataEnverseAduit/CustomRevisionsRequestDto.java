package com.pradeep.DataEnverseAduit;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomRevisionsRequestDto {
    private int id;
    private Date startDate;
    private Date endDate;
}
