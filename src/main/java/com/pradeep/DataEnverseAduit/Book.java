package com.pradeep.DataEnverseAduit;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Audited
public class Book {
    @Id
    @GeneratedValue
    private int id;
    private String name;
    private int pages;
}
