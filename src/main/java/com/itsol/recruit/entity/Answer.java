package com.itsol.recruit.entity;

import jdk.nashorn.internal.ir.annotations.Ignore;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity(name = "answers")
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Answer {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    @Type(type="org.hibernate.type.UUIDCharType")
    UUID id;

    @Column(name = "answer")
    String answer;

    @Column(name = "corect_answer")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    Boolean corectAnswer;

    @ManyToOne
    @JoinColumn(name = "question_id")
    Question question;

}
