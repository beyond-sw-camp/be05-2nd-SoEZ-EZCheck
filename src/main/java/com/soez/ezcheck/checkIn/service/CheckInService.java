package com.soez.ezcheck.checkIn.service;

import com.soez.ezcheck.checkIn.domain.CheckInRequestDTO;
import com.soez.ezcheck.checkIn.repository.CheckInRepository;
import com.soez.ezcheck.entity.CheckIn;
import com.soez.ezcheck.entity.Reservation;
import com.soez.ezcheck.entity.Room;
import com.soez.ezcheck.entity.RoomStatusEnum;
import com.soez.ezcheck.reservation.repository.ReservationRepository;
import com.soez.ezcheck.room.repository.RoomRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CheckInService {

    private final RoomRepository roomRepository;

    private final CheckInRepository checkInRepository;

    private final ReservationRepository reservationRepository;

    @PersistenceContext
    private EntityManager entityManager;


    // 사용자 ID에 따른 모든 예약 객실 정보 조회 x
    // 예약 ID에 따른 모든 예약 객실 정보 조회
    public List<Reservation> findAllReservationsById(Integer rvId) {
        // 사용자 ID(String)에 따른 모든 예약 정보 조회 (수정 필요)

        //return reservationRepository.findByUsers_UId(uId);
        Iterable<Integer> iterable = Collections.singleton(rvId);
        return reservationRepository.findAllById(iterable);

    }


    public List<Room> findAvailableRooms(Integer rId) {
        Optional<Room> roomData = roomRepository.findById(rId);
        if (roomData.isPresent()) {
            Room room = roomData.get();
            if (room.getRoomStatusEnum() == RoomStatusEnum.AVAILABLE) {
                return Collections.singletonList(room);
            }
        }
        return Collections.emptyList();


    }

    public String checkInReservation (List <Reservation> availableReservations, CheckInRequestDTO request) {
        // 체크인할 예약 정보 찾기
        Optional<Reservation> reservationOptional = availableReservations.stream()
                .filter(reservation -> reservation.getRvId().equals(request.getRvId()))
                .findFirst();

        if (reservationOptional.isEmpty()) {
            throw new IllegalArgumentException("체크인할 예약 정보를 찾을 수 없습니다.");
        } else {

            Reservation reservationToCheckIn = reservationOptional.get();
            Optional<Room> roomOptional = roomRepository.findById(reservationToCheckIn.getRvId());
            if (roomOptional.isPresent()) {
                Room room = roomOptional.get();
                if (room.getRoomStatusEnum() == RoomStatusEnum.AVAILABLE) {
                    List<Room> availableRooms = Collections.singletonList(room);

                    // Select the first available room
                    Room roomToCheckIn = availableRooms.get(0);

                    // 객실 상태 및 비밀번호 업데이트
                    roomToCheckIn.setRPwd(request.getRoomPwd());
                    roomToCheckIn.setRoomStatusEnum(RoomStatusEnum.OCCUPIED);
                    roomRepository.save(roomToCheckIn);

                    // 체크인 정보 저장
                    CheckIn checkIn = new CheckIn();
                    LocalDateTime currentTime = LocalDateTime.now();
                    checkIn.setCinDate(Date.valueOf(currentTime.toLocalDate()));
                    checkIn.setCinTime(Time.valueOf(currentTime.toLocalTime()));
                    checkInRepository.save(checkIn);

                    return "체크인이 완료되었습니다.";
                }
            } else {
                throw new IllegalArgumentException("체크인 가능한 객실을 찾을 수 없습니다.");
            }
        }
        return null;
    }
}

