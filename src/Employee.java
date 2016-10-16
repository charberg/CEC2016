import java.util.Date;
import java.util.HashMap;


public class Employee {

	String name;
	HashMap<String, Date> startTimes;
	HashMap<String, Date> endTimes;
	boolean overnight = false;
	Integer employeeId = null;
	
	public Employee() {
		startTimes = new HashMap<String, Date>();
		startTimes.put("monday", null);
		startTimes.put("tuesday", null);
		startTimes.put("wednesday", null);
		startTimes.put("thursday", null);
		startTimes.put("friday", null);
		startTimes.put("saturday", null);
		endTimes = new HashMap<String, Date>();
		endTimes.put("monday", null);
		endTimes.put("tuesday", null);
		endTimes.put("wednesday", null);
		endTimes.put("thursday", null);
		endTimes.put("friday", null);
		endTimes.put("saturday", null);
	}
	
	public Employee(String name) {
		this();
		this.name = name;
		
	}

	public Employee(Integer employeeId, String name, Date startMonday, Date endMonday, Date startTuesday, Date endTuesday, Date startWednesday,
					Date endWednesday, Date startThursday, Date endThursday, Date startFriday, Date endFriday, Date startSaturday,
					Date endSaturday, Boolean overnight){
		this.employeeId = employeeId;
		this.name = name;
		this.overnight = overnight;
		startTimes = new HashMap<String, Date>();
		startTimes.put("monday", startMonday);
		startTimes.put("tuesday", startTuesday);
		startTimes.put("wednesday", startWednesday);
		startTimes.put("thursday", startThursday);
		startTimes.put("friday", startFriday);
		startTimes.put("saturday", startSaturday);
		endTimes = new HashMap<String, Date>();
		endTimes.put("monday", endMonday);
		endTimes.put("tuesday", endTuesday);
		endTimes.put("wednesday", endWednesday);
		endTimes.put("thursday", endThursday);
		endTimes.put("friday", endFriday);
		endTimes.put("saturday", endSaturday);
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setStartTime(String day, Date time) {
		this.startTimes.put(day, time);
	}
	
	public void setEndTime(String day, Date time) {
		this.endTimes.put(day, time);
	}
	
	public boolean getOvernight() {
		return overnight;
	}
	
	public void setOvernight(boolean overnight) {
		this.overnight = overnight;
	}
	
}
