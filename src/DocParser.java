import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;


public class DocParser {

	public static ArrayList<FoodStock> parseFoodListDocx(String filePath)
    {
		
		ArrayList<String> lines;
		
		try {
            FileInputStream fis = new FileInputStream(filePath);        
            org.apache.poi.xwpf.extractor.XWPFWordExtractor oleTextExtractor = new XWPFWordExtractor(new XWPFDocument(fis));
            lines = new ArrayList<String>(Arrays.asList(oleTextExtractor.getText().split("\n")));
            lines.remove(0);	//Remove column headers
            oleTextExtractor.close();
        } catch (Exception e) {
        	System.out.println("Error reading in food file");
            e.printStackTrace();
            return null;
        }
		
		ArrayList<FoodStock> items = new ArrayList<FoodStock>();
		
		for(String line : lines) {
			
			String[] lineItems = line.split("\\t+");
			DateFormat format = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
			Date date;
			try {
				date = format.parse(lineItems[1]);
			} catch (ParseException e) {
				System.out.println("Unable to parse date string: " + lineItems[1] + " Not in the right format. MMMM, d, yyyy");
				return null;
			}

			try {
			items.add(new FoodStock(lineItems[0], date, Integer.parseInt(lineItems[2].replaceAll("\\s+", "")), (int) (Math.random() * 9000) + 1000, 0, 0));
			}
			catch (Exception e) {
				System.out.println("Problem parsing line: " + line);
				return null;
			}
		}
		
		return items;
		
    }
	
	
	public static ArrayList<Employee> parseEmployeeListDocx(String filePath)
    {
		
		HashMap<String, Integer> dayToVal = new HashMap<String, Integer>();
		HashMap<Integer, String> valToDay = new HashMap<Integer, String>();
		
		dayToVal.put("monday", 1);
		dayToVal.put("tuesday", 2);
		dayToVal.put("wednesday", 3);
		dayToVal.put("thursday", 4);
		dayToVal.put("friday", 5);
		dayToVal.put("saturday", 6);
		
		valToDay.put(1, "monday");
		valToDay.put(2, "tuesday");
		valToDay.put(3, "wednesday");
		valToDay.put(4, "thursday");
		valToDay.put(5, "friday");
		valToDay.put(6, "saturday");
		
		
		ArrayList<String> lines;
		
		try {
            FileInputStream fis = new FileInputStream(filePath);        
            org.apache.poi.xwpf.extractor.XWPFWordExtractor oleTextExtractor = new XWPFWordExtractor(new XWPFDocument(fis));
            lines = new ArrayList<String>(Arrays.asList(oleTextExtractor.getText().split("\n")));
            lines.remove(0);	//Remove column headers
            oleTextExtractor.close();
        } catch (Exception e) {
        	System.out.println("Error reading in employee file");
            e.printStackTrace();
            return null;
        }
		
		ArrayList<Employee> items = new ArrayList<Employee>();
		
		for(String line : lines) {
			
			if(line.length() == 0 || line.contains("The operating hours")) {
				//String from end of doc, just exit
				break;
			}
			
			line = line.replaceAll(",", "");
			line = line.replaceAll("\t", " ");
			line = line.toLowerCase();
			String[] lineItems = line.split("\\s+");
			
			//DateFormat format = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);

			String name = lineItems[0] + " " + lineItems[1];	//Get the first and last name

			Employee employee = new Employee();
			employee.setName(name);
			
			int i = 2;
			while(i < lineItems.length) {	//Iterate over each object in the line, due to nasty multi-format date strings
				
				//str.matches(".*\\d+.*"); Checks if string contains number
				
				//Check if range of dates, and not a range of times
				if(lineItems[i].contains("-") && !lineItems[i].matches(".*\\d+.*")) {	
					//If so, get the first and last day, and then iterate over the range, and add as a full day of availability
					String[] days = lineItems[i].split("-");
					String start = days[0];
					String end = days[1];
					int startVal = dayToVal.get(start);
					int endVal = dayToVal.get(end);
					
					while(startVal <= endVal) {
						Date startDate = new Date();
						//Set start time
						Calendar cal = Calendar.getInstance();
						cal.set(2001, 0, startVal, 8, 30);
						startDate = cal.getTime();
						
						//Set end time
						Date endDate = new Date();
						//Set start time
						cal = Calendar.getInstance();
						cal.set(2001, 0, endVal, 17, 30);
						endDate = cal.getTime();

						
						employee.setStartTime(valToDay.get(startVal), startDate);
						employee.setEndTime(valToDay.get(startVal), endDate);
						
						startVal++;
					}
					
				}
				
				//Check if no number in string, so not a specific time, but a day
				else if(!lineItems[i].matches(".*\\d+.*")) {
					
					if(lineItems[i].charAt(lineItems[i].length()-1) == 's') {
						lineItems[i] = lineItems[i].substring(0, lineItems[i].length()-1);
					}
					
					//Separate check for saturday, since it may specify overnights next
					if(lineItems[i].contains("saturday")) {
						
						try{
							//The next element must be "yes" or "overnight" to enable overnights for Saturdays
							if(lineItems[i+1].equals("yes") || lineItems[i+1].equals("overnight")) {
								employee.setOvernight(true);
							}
						}
						catch(ArrayIndexOutOfBoundsException e) {
							//Then nothing after Saturday, just continue adding the whole day
						}
						
						Date startDate = new Date();
						//Set start time
						Calendar cal = Calendar.getInstance();
						cal.set(2001, 0, 6, 8, 30);
						startDate = cal.getTime();
						
						//Set end time
						Date endDate = new Date();

						cal = Calendar.getInstance();
						cal.set(2001, 0, 6, 17, 30);
						endDate = cal.getTime();
						
						employee.setStartTime("saturday", startDate);
						employee.setEndTime("saturday", endDate);
						
						//If saturday case found, we can stop here.
						break;
					}
					
					String timeRange;
					
					try{
						timeRange = lineItems[i+1];
					}
					catch(ArrayIndexOutOfBoundsException e) {
						//No more items after this one, means no specific time range, so can just do full day, and break out of loop
						if(lineItems[i].charAt(lineItems[i].length()-1) == 's') {
							lineItems[i] = lineItems[i].substring(0, lineItems[i].length()-1);
						}
						Date startDate = new Date();
						//Set start time
						Calendar cal = Calendar.getInstance();
						cal.set(2001, 0, dayToVal.get(lineItems[i]), 8, 30);
						startDate = cal.getTime();
						
						//Set end time
						Date endDate = new Date();
						
						cal.set(2001, 0, dayToVal.get(lineItems[i]), 17, 30);
						endDate = cal.getTime();
						
						employee.setStartTime(lineItems[i], startDate);
						employee.setEndTime(lineItems[i], endDate);
						break;
					}
					
					if(!timeRange.matches(".*\\d+.*")) {
						
						if(dayToVal.get(lineItems[i]) == null) {
							continue;
						}
						
						//Next item is NOT a time range, so just make the full day
						if(lineItems[i].charAt(lineItems[i].length()-1) == 's') {
							lineItems[i] = lineItems[i].substring(0, lineItems[i].length()-1);
						}
						Date startDate = new Date();
						//Set start time
						Calendar cal = Calendar.getInstance();
						cal.set(2001, 0, dayToVal.get(lineItems[i]), 8, 30);
						startDate = cal.getTime();
						
						//Set end time
						Date endDate = new Date();
						//Set start time
						
						cal = Calendar.getInstance();
						cal.set(2001, 0, dayToVal.get(lineItems[i]), 17, 30);
						endDate = cal.getTime();
						
						employee.setStartTime(lineItems[i], startDate);
						employee.setEndTime(lineItems[i], endDate);
						break;
					}
					
					//Next item IS a time range! Now things get tricky. Multiple formats, and figure AM/PM offsets
					else {
						
						//Split date into begin/end times
						
						String[] split = timeRange.split("-");
						String startStr = split[0];
						String endStr = split[1];
						
						DateFormat format1 = new SimpleDateFormat("HH:mma", Locale.US);
						DateFormat format2 = new SimpleDateFormat("HHa", Locale.US);
						
						ArrayList<DateFormat> formats = new ArrayList<DateFormat>();
						formats.add(format1);
						formats.add(format2);
						
						Date startDate = null;
						Date endDate = null;
						
						Calendar startCal = Calendar.getInstance();
						Calendar endCal = Calendar.getInstance();
						
						boolean formatPassStart = false;
						boolean formatPassEnd = false;
						
						for(DateFormat format : formats) {
							try {
								startCal.setTime(format.parse(startStr));
								formatPassStart = true;
								break;
							} catch(ParseException e) {
								//Format didn't stick, try the next
							}
						}
						
						
						for(DateFormat format : formats) {
							try {
								endCal.setTime(format.parse(endStr));
								formatPassEnd = true;
								break;
							} catch(ParseException e) {
								//Format didn't stick, try the next
							}
						}
						
						if(formatPassStart = false || formatPassEnd == false) {
							//None of the formats worked, failure in parsing
							System.out.println("Error parsing date: " + timeRange);
							return null;
						}
						
						startCal.set(Calendar.YEAR, 2001);
						startCal.set(Calendar.MONTH, 0);
						startCal.set(Calendar.DAY_OF_MONTH, dayToVal.get(lineItems[i]));
						startDate = startCal.getTime();
						
						endCal.set(Calendar.YEAR, 2001);
						endCal.set(Calendar.MONTH, 0);
						endCal.set(Calendar.DAY_OF_MONTH, dayToVal.get(lineItems[i]));
						endDate = endCal.getTime();
						
						employee.setStartTime(lineItems[i], startDate);
						employee.setEndTime(lineItems[i], endDate);
						
						//Skip the next element, we parsed the times
						i++;
						
						
					}

				}
				i++;
			}
			
			items.add(employee);
			
		}
		
		return items;
		
    } 
	
	
	
	public static void main(String[] args) {
		
		ArrayList<Employee> items = DocParser.parseEmployeeListDocx("Programming Employee List.docx");
		System.out.println("Done");
		
	}
	
	
	
}
