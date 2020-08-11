package mrs.controller.reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import mrs.domain.model.MeetingRoom;
import mrs.domain.model.Reservation;
import mrs.domain.model.ReserveInfo;
import mrs.domain.service.reservation.ReservationService;
import mrs.domain.service.room.RoomService;

@Controller
public class ReservationController {


	@Autowired
	RoomService roomservice;

	@Autowired
	ReservationService reservationService;

	@GetMapping("/reservations/{date}/{roomId}")
	public String getReserve(Model model,@ModelAttribute Reservation reservation,@DateTimeFormat(pattern = "yyyy-MM-dd")@PathVariable("date") LocalDate date, @PathVariable("roomId")int roomId) {

		//該当する日付・該当するroomIdで予約リストを取得
		List<ReserveInfo> reserveList = reservationService.findReserveInfo(date, roomId);		
		model.addAttribute("reserveList", reserveList);

		//roomIdで該当の会議室を取得
		MeetingRoom meetingroom = roomservice.findRoomById(roomId);
		model.addAttribute("meetingroom", meetingroom);

		//日付をセット
		model.addAttribute("date", date);
		
		//比較する日付をセット
		LocalDate compareDate = LocalDate.now();
		model.addAttribute("compareDate", compareDate);

		return "reservation/reserveForm";
	}

	@PostMapping("/reservations/{date}/{roomId}")
	public String reserve(Model model,@Validated Reservation reservation,BindingResult result,@DateTimeFormat(pattern = "yyyy-MM-dd")@PathVariable("date") LocalDate date, @PathVariable("roomId")int roomId) {
		//入力チェックに引っかかった場合、ユーザー登録画面に戻る
		if(result.hasErrors()) {
			//GETリクエスト用のメソッドを呼び出して、ユーザー登録画面に戻る
			return getReserve(model,reservation,date,roomId);
		}

		reservationService.insert(reservation);

		return "redirect:/reservations/{date}/{roomId}";
	}

	@PostMapping(value="reservations/{date}/{roomId}", params = "cancel")
	public String cancel(@RequestParam("reservationId")int reservationId) {

		reservationService.deleteOne(reservationId);

		return "redirect:/reservations/{date}/{roomId}";
	}

	@RequestMapping(value = "/reservations/{date}/{roomId}/getStartTime", method = RequestMethod.GET)
	@ResponseBody
	public String getStartTime(@DateTimeFormat(pattern = "yyyy-MM-dd")@PathVariable("date") LocalDate date, @PathVariable("roomId")int roomId) {

		//該当する日付・該当するroomIdで予約リストを取得
		List<ReserveInfo> reserveList = reservationService.findReserveInfo(date, roomId);

		

		//全部の営業時間
		List<LocalTime> openingTimeList = genarateOpeningTimelist();
		

		//最終時間を無くす処理
		LocalTime nonReservableTime = LocalTime.of(23, 0);		
		openingTimeList.remove(nonReservableTime);

		List<LocalTime> reservedTimeList = new LinkedList<>();

		for(ReserveInfo reserve : reserveList) {

			LocalTime startTime = reserve.getStartTime();
			LocalTime endTime = reserve.getEndTime();

			while(startTime.isBefore(endTime)){	
				//予約の終了時刻を含めない
				reservedTimeList.add(startTime);
				startTime = startTime.plusMinutes(30);
			}

		}
		

		for(LocalTime removeTime:reservedTimeList) {

			if(openingTimeList.contains(removeTime)) {
				openingTimeList.remove(removeTime);
			}

		}
		

		return generateTimeOption(openingTimeList);
	}

	@RequestMapping(value = "/reservations/{date}/{roomId}/getEndTime", method = RequestMethod.POST)
	@ResponseBody
	public String getEndTime(String selectStartTime,@DateTimeFormat(pattern = "yyyy-MM-dd")@PathVariable("date") LocalDate date, @PathVariable("roomId")int roomId) {

		//開始時間より30分遅い時間を設定
		LocalTime endStartTime = genarateEndReserableTime(selectStartTime);
		
		//営業終了時間を設定
		LocalTime openingEndTime = LocalTime.of(23, 0);

		//予約終了時間から営業終了時間までの仮の時間帯を生成
		List<LocalTime> preReservableTimeList = new LinkedList<LocalTime>();

		
		while(endStartTime.isBefore(openingEndTime)){

			preReservableTimeList.add(endStartTime);
			endStartTime = endStartTime.plusMinutes(30);

		};
				
		preReservableTimeList.add(openingEndTime);
	

		


		//該当する日付・該当するroomIdで予約リストを取得
		List<ReserveInfo> reserveList = reservationService.findReserveInfo(date, roomId);

		List<LocalTime> reservedTimeList = genarateReservedTimeListForEndTime(reserveList);

		//全部の営業時間
		List<LocalTime> openingTimeList = genarateOpeningTimelist();


		for(LocalTime removeTime:reservedTimeList) {

			if(openingTimeList.contains(removeTime)) {
				openingTimeList.remove(removeTime);
			}

		}
		
		
		//最終的に予約できる時間のリストを作成
		List<LocalTime> reserableTimeList = new LinkedList<>();

		for(LocalTime preReservableTime : preReservableTimeList) {

			if(!openingTimeList.contains(preReservableTime)) {
				break;
			}	
			reserableTimeList.add(preReservableTime);						
		}


		return generateTimeOption(reserableTimeList);
	}
	
	//予約されている時間のリストを返すメソッド(終了時刻用)
	
	private List<LocalTime> genarateReservedTimeListForEndTime(List<ReserveInfo> reserveList){
		
		List<LocalTime> reservedTimeList = new LinkedList<LocalTime>();
		
		for(ReserveInfo reserve : reserveList) {

			LocalTime startTime = reserve.getStartTime();
			LocalTime endTime = reserve.getEndTime();



			while(startTime.isBefore(endTime)){	
				//予約の開始時刻を含めない
				startTime = startTime.plusMinutes(30);
				reservedTimeList.add(startTime);
			}

		}
		
		return reservedTimeList;
		
	}

	//予約の終了時間を返すメソッド
	private LocalTime genarateEndReserableTime(String selectedStartTime) {
		String[] startTimeArray = selectedStartTime.split(":");


		String shour = startTimeArray[0];
		String sminute = startTimeArray[1];

		int hour = Integer.parseInt(shour);
		int minute = Integer.parseInt(sminute);

		return LocalTime.of(hour,minute).plusMinutes(30);
	}
	
	//選択肢を返すメソッド
	private String generateTimeOption(List<LocalTime> timeList) {
		
		StringBuilder sb = new StringBuilder("<option value=\"\">----</option>");

		for(LocalTime time:timeList) {

			String option	= String.format("<option value=\"%s\">%s</option>", time,time);

			sb.append(option);
		}
		
		return sb.toString();
		
	}


	//営業時間のリストを作るメソッド

	private List<LocalTime> genarateOpeningTimelist(){

		List<LocalTime> timelist = new LinkedList<LocalTime>();
		
		
		//現在時刻の判定
		LocalTime now = LocalTime.now();
		
		int startHour = now.getHour();
		int startMinute = now.getMinute();
		
		//30分未満の場合は分を30分にする
		if(startMinute < 30) {
			startMinute = 30;
		}else if(startMinute >= 30) {
			startHour +=1;
			startMinute = 0;
		}
		
		
		

		LocalTime startTime = LocalTime.of(startHour, startMinute);
		LocalTime endTime = LocalTime.of(23, 0);

		do {
			timelist.add(startTime);
			startTime = startTime.plusMinutes(30);

		}while(startTime.isBefore(endTime));

		timelist.add(endTime);

		return timelist;

	}


}
