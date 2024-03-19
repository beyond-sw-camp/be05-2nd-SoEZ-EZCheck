package com.soez.ezcheck.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;

@Data
@Entity
@Table(name = "reservation")
public class Reservation {

    @Id
    @Column(name = "rv_id")
    private Integer rvId;

    @Column(name = "rv_date_from")
    private Date rvDateFrom;

    @Column(name = "rv_date_to")
    private Date rvDateTo;

    @ManyToOne
    @JoinColumn(name = "u_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "rg_id")
    private RoomGrade roomGrade;

}
