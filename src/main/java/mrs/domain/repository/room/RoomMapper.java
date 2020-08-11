package mrs.domain.repository.room;

import java.time.LocalDate;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import mrs.domain.model.MeetingRoom;

@Mapper
public interface RoomMapper {
	
	//該当の日付の会議室一覧を取得する
	public List<MeetingRoom> findReservableRoom(LocalDate date);
	
	//該当するIDの会議室を取得する
	public MeetingRoom findRoomById(int roomId);
}
