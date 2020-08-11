package mrs.domain.service.room;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mrs.domain.model.MeetingRoom;
import mrs.domain.repository.room.RoomMapper;

@Service
public class RoomService {
	@Autowired
	RoomMapper roomMapper;
	
	public List<MeetingRoom> findReservableRoom(LocalDate date){
		return roomMapper.findReservableRoom(date);
	};
	
	public MeetingRoom findRoomById(int roomId) {
		return roomMapper.findRoomById(roomId);
	} 
}
