package mrs.domain.model;

import java.time.LocalDate;

import lombok.Data;

@Data
public class ReservableRoom {
	private LocalDate reservedDate;
	private int RoomId;
	
	public LocalDate getReservedDate() {
		return reservedDate;
	}
	public void setReservedDate(LocalDate reservedDate) {
		this.reservedDate = reservedDate;
	}
	public int getRoomId() {
		return RoomId;
	}
	public void setRoomId(int roomId) {
		RoomId = roomId;
	}
	
	
}
