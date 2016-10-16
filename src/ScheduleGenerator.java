import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class ScheduleGenerator {

	
	
	
	static Date D831 = new Date(2001,0,1,8,29),
	D929 = new Date(2001,0,1,9,29),
	D931 = new Date(2001,0,1,9,31),
	D1029 = new Date(2001,0,1,10,29),
	D1031 = new Date(2001,0,1,10,31),
	D1129 = new Date(2001,0,1,11,29),
	D1131 = new Date(2001,0,1,11,31),
	D1229 = new Date(2001,0,1,12,29),
	D1231 = new Date(2001,0,1,12,31),
	D129 = new Date(2001,0,1,13,29),
	D131 = new Date(2001,0,1,13,31),
	D229 = new Date(2001,0,1,14,29),
	D231 = new Date(2001,0,1,14,31),
	D329 = new Date(2001,0,1,15,29),
	D331 = new Date(2001,0,1,15,31),
	D429 = new Date(2001,0,1,16,29),
	D431 = new Date(2001,0,1,16,31),
	D529 = new Date(2001,0,1,17,29);

	private static void setDates() {
		Calendar cal = Calendar.getInstance();
		cal.set(2001, 0, 1, 8, 31);
		D831 = cal.getTime();
		cal.set(2001, 0, 1, 9, 29);
		D929 = cal.getTime();
		cal.set(2001, 0, 1, 9, 31);
		D931 = cal.getTime();
		cal.set(2001, 0, 1, 10, 29);
		D1029 = cal.getTime();
		cal.set(2001, 0, 1, 10, 31);
		D1031 = cal.getTime();
		cal.set(2001, 0, 1, 11, 29);
		D1129 = cal.getTime();
		cal.set(2001, 0, 1, 11, 31);
		D1131 = cal.getTime();
		cal.set(2001, 0, 1, 12, 29);
		D1229 = cal.getTime();
		cal.set(2001, 0, 1, 12, 31);
		D1231 = cal.getTime();
		cal.set(2001, 0, 1, 13, 29);
		D129 = cal.getTime();
		cal.set(2001, 0, 1, 13, 31);
		D131 = cal.getTime();
		cal.set(2001, 0, 1, 14, 29);
		D229 = cal.getTime();
		cal.set(2001, 0, 1, 14, 31);
		D231 = cal.getTime();
		cal.set(2001, 0, 1, 15, 29);
		D329 = cal.getTime();
		cal.set(2001, 0, 1, 15, 31);
		D331 = cal.getTime();
		cal.set(2001, 0, 1, 16, 29);
		D429 = cal.getTime();
		cal.set(2001, 0, 1, 16, 31);
		D431 = cal.getTime();
		cal.set(2001, 0, 1, 17, 29);
		D529 = cal.getTime();
	}
	
	public static String[][] generateSchedule(ArrayList<Employee> employees) {
		
		setDates();
		
		String[][] schedule = new String[6][10];
		
		ArrayList<Employee> usedEmployees = new ArrayList<Employee>();
		
		for (int day = 0; day < 6; day++) {
			for (int hour = 0; hour < 10; hour++) {
				if (hour != 9) {
					schedule[day][hour] = findEmployee(usedEmployees, employees, day, hour);
				}
				else {	//Is overnight hour
					if (day == 5) {	//is Saturday
						employees.addAll(usedEmployees);
						schedule[day][hour] = "";
						for (int i = 0; i < employees.size(); i++) {
							if (employees.get(i).overnight) {
								schedule[day][hour] = employees.get(i).name;
							}
						}
					}
					else {	//if not Saturday, no overnight
						schedule[day][hour] = "";
					}
				}
			}
		}
		
		return schedule;
	}
	
	private static String findEmployee(ArrayList<Employee> usedEmployees,ArrayList<Employee> employees, int day, int hour) {
		
		String employeeChoosen = "";
		
		Date start, end, estart, eend;
		
		switch (hour)  {
		case 0:
			start = D831;
			end = D929;
			break;
		case 1:
			start = D931;
			end = D1029;
			break;
		case 2:
			start = D1031;
			end = D1129;
			break;
		case 3:
			start = D1131;
			end = D1229;
			break;
		case 4:
			start = D1231;
			end = D129;
			break;
		case 5:
			start = D131;
			end = D229;
			break;
		case 6:
			start = D231;
			end = D329;
			break;
		case 7:
			start = D331;
			end = D429;
			break;
		case 8:
			start = D431;
			end = D529;
			break;
		default:
			start = null;
			end = null;
		}
		
		for(int i = 0; i < employees.size();i++) {
			estart = employees.get(i).startTimes.get(getDay(day));
			eend = employees.get(i).endTimes.get(getDay(day));
			
			if (estart != null && eend != null) {
				if (estart.after(start) && end.before(eend)) {
					employeeChoosen = employees.get(i).name;
					usedEmployees.add(employees.remove(i));
					return employeeChoosen;
				}
			}
			
		}
		
		employees.addAll(usedEmployees);
		usedEmployees.clear();
		
		for(int i = 0; i < employees.size();i++) {
			estart = employees.get(i).startTimes.get(getDay(day));
			eend = employees.get(i).endTimes.get(getDay(day));
			
			if (estart != null && eend != null) {
				if (estart.after(start) && eend.before(end)) {
					employeeChoosen = employees.get(i).name;
					usedEmployees.add(employees.remove(i));
					return employeeChoosen;
				}
			}
		}
		
		return "";
	}
	
	private static String getDay(int i) {
		switch (i) {
		case 0:
			return "monday";
		case 1:
			return "tuesday";
		case 2:
			return "wednesday";
		case 3:
			return "thursday";
		case 4:
			return "friday";
		case 5:
			return "saturday";
		default:
			return null;
		}
			
	}
	
	public static void main(String[] args) {
		
		
	
	}
	
}
