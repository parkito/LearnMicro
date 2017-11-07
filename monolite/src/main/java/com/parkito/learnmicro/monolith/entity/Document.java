package com.parkito.learnmicro.monolith.entity;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;


/**
 * @author Artem Karnov @date 11/6/2017.
 * artem.karnov@t-systems.com
 */
@Entity
@Builder
@Data
public class Document implements Serializable {
    @Tolerate
    public Document() {
    }

    @Id
    @GeneratedValue
    private long documentId;

    @Column(nullable = false)
    private String serial;

    @Column(nullable = false)
    private String number;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;
}
