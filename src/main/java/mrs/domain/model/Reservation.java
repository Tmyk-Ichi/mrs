package mrs.domain.model;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class Reservation {
	@NotNull(message = "時刻が正しくありません")
	private LocalTime startTime;
	@NotNull(message = "時刻が正しくありません")
	private LocalTime endTime;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate reservedDate;
	private int roomId;
	private String userId;
	public LocalTime getStartTime() {
		return startTime;
	}
	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}
	public LocalTime getEndTime() {
		return endTime;
	}
	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}
	public LocalDate getReservedDate() {
		return reservedDate;
	}
	public void setReservedDate(LocalDate reservedDate) {
		this.reservedDate = reservedDate;
	}
	public int getRoomId() {
		return roomId;
	}
	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
}
