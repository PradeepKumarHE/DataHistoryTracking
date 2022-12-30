package com.pradeep.DataEnverseAduit;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Audited
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private int pages;
    private Date createdDate=new Date();
}
