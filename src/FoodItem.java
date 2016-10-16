import java.util.Date;


public class FoodItem {

	String name;
	Date expiryDate;
	int stock;
	int batchNumber;
	
	public FoodItem(String name, Date expiryDate, int stock, int batchNumber) {
		this.name = name;
		this.expiryDate = expiryDate;
		this.stock = stock;
		this.batchNumber = batchNumber;
	}
	
	public int getBatchNumber() {
		return batchNumber;
	}
	
	public void setBatchNumber(int batchNumber) {
		this.batchNumber = batchNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}
	
}