import java.util.Date;
import java.util.HashMap;


public class Employee {

	String name;
	HashMap<String, Date> startTimes;
	HashMap<String, Date> endTimes;
	boolean overnight = false;
	
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
