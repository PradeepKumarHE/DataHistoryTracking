package com.pradeep.DataEnverseAduit;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomRevision {
    private Book book;
    private Integer revisionNumber;
    private String revisionType;
    private String revisionTime;

}
