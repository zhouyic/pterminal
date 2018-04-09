package communicate.timer;

import java.util.ArrayList;
import java.util.List;

import communicate.pkmgmt.IThaiService;
import communicate.pkmgmt.OrderMapMemory;
import communicate.pkmgmt.ThaiServiceImpl;
import communicate.pkmgmt.dto.StatusDTO;

public class TimerServlet{

	private static final long serialVersionUID = 1024951815010423373L;
	private TimerSchedule timer;
	private IThaiService thaiService = null;
	
	public void init() {
		thaiService = new ThaiServiceImpl();
		thaiService.getAllOnTerminal();
		timer = new TimerSchedule();
		timer.start();
	}


	public void destroy(){
		timer.cancel();
	}

	public static void main(String[] a) {
		TimerServlet t = new TimerServlet();
		t.init();

		OrderMapMemory o1 = OrderMapMemory.getInstance();
		List<StatusDTO> list = new ArrayList<StatusDTO>();
		StatusDTO d = new StatusDTO();
		d.setId("1");
		d.setStatus(2);
		StatusDTO d1 = new StatusDTO();
		d1.setId("2");
		d1.setStatus(2);
		StatusDTO d2 = new StatusDTO();
		d2.setId("3");
		d2.setStatus(2);
		StatusDTO d3 = new StatusDTO();
		d3.setId("4");
		d3.setStatus(2);
		list.add(d);
		list.add(d1);
		list.add(d2);
		list.add(d3);
		o1.setDownOkOrders(list);
	}

}
