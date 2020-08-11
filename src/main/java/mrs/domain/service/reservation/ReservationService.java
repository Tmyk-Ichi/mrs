package mrs.domain.service.reservation;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mrs.domain.model.Reservation;
import mrs.domain.model.ReserveInfo;
import mrs.domain.repository.reservation.ReservationMapper;

@Service
public class ReservationService {
	
	@Autowired
	ReservationMapper reservationMapper;
	
	public List<ReserveInfo> findReserveInfo(LocalDate reservedDate,int roomId){
		return reservationMapper.findReserveInfo(reservedDate, roomId);
	}
	
	public boolean insert(Reservation reservation) {
		return reservationMapper.insert(reservation);		
	}
	
	public boolean deleteOne(int reservationId) {
		return reservationMapper.deleteOne(reservationId);
	}
	
}
