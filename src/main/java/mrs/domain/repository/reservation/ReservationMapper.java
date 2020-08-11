package mrs.domain.repository.reservation;

import java.time.LocalDate;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import mrs.domain.model.Reservation;
import mrs.domain.model.ReserveInfo;

@Mapper
public interface ReservationMapper {
	
	//予約登録用メソッド
	public boolean insert(Reservation reservation);
	
	public List<ReserveInfo> findReserveInfo(@Param("reservedDate")LocalDate reservedDate,@Param("roomId")int roomId);
	
	public boolean deleteOne(int reservationId);
}
