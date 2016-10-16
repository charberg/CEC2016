import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;


public class DocParser {

	public static ArrayList<FoodItem> parseFoodListDocx(String filePath)
    {
		
		ArrayList<String> lines;
		
		try {
            FileInputStream fis = new FileInputStream(filePath);        
            org.apache.poi.xwpf.extractor.XWPFWordExtractor oleTextExtractor = new XWPFWordExtractor(new XWPFDocument(fis));
            lines = new ArrayList<String>(Arrays.asList(oleTextExtractor.getText().split("\n")));
            lines.remove(0);	//Remove column headers
            oleTextExtractor.close();
        } catch (Exception e) {
        	System.out.println("Error reading in file");
            e.printStackTrace();
            return null;
        }
		
		ArrayList<FoodItem> items = new ArrayList<FoodItem>();
		
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
			items.add(new FoodItem(lineItems[0], date, Integer.parseInt(lineItems[2].replaceAll("\\s+", "")), (int)(Math.random()*9000)+1000));
			}
			catch (Exception e) {
				System.out.println("!!!" + line + "!!!");
				System.out.println("!!!" + lineItems[0] + "!!!");
				System.out.println("!!!" + lineItems[1] + "!!!");
				System.out.println("!!!" + lineItems[2] + "!!!");
			}
		}
		
		return items;
		
    }
	
	
	//public static void main(String[] args) {
		
	//	ArrayList<FoodItem> items = DocParser.parseFoodListDocx("Programming Food List.docx");
	//	System.out.println("Done");
		
	//}
	
	
	
}
