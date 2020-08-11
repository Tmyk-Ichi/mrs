package mrs.controller.room;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import mrs.domain.model.MeetingRoom;
import mrs.domain.service.room.RoomService;

@Controller
public class RoomController {

	@Autowired
	RoomService roomservice;


	@GetMapping("/rooms")
	public String getRooms(Model model) {
		//本日の日付を取得
		LocalDate today = LocalDate.now();

		//本日の日付で、会議室一覧を取得
		List<MeetingRoom> roomlist = roomservice.findReservableRoom(today);

		//モデルに登録
		model.addAttribute("roomlist", roomlist);

		model.addAttribute("date", today);

		return "/room/listroom";
	}

	@GetMapping("/rooms/{date}")
	public String getRooms(Model model,@DateTimeFormat(pattern = "yyyy-MM-dd")@PathVariable("date") LocalDate date) {
		//本日の日付で、会議室一覧を取得
		List<MeetingRoom> roomlist = roomservice.findReservableRoom(date);
		
		//モデルに登録
		model.addAttribute("roomlist", roomlist);

		model.addAttribute("date", date);

		return "/room/listroom";
	}

}
