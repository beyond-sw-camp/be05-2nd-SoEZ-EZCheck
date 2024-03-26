package com.soez.ezcheck.facility.repository;

import com.soez.ezcheck.entity.Facility;
import com.soez.ezcheck.entity.FacilityReservation;
import com.soez.ezcheck.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Repository
public interface FacilityReservationRepository extends JpaRepository<FacilityReservation, Integer> {

    List<FacilityReservation> findByFrDateAndFrTime(Date frDate, Time frTime);
    List<FacilityReservation> findByUsers(Users users);

    List<FacilityReservation> findByFacilityAndFrDate(Facility facility, Date date);
}
