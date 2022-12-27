package com.pradeep.DataEnverseAduit;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomRevision {
    private Book book;
    private Integer revisionNumber;
    private String revisionType;
    private String revisionTime;

}
