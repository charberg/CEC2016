import java.util.Date;


public class FoodStock extends FoodItem{

	Date expiryDate;
	Integer batchNumber;
	
	public FoodStock(String name, Date expiryDate, int stock, int batchNumber, int restockLimit, int popularity) {
		super(name, restockLimit, popularity, stock);
		this.expiryDate = expiryDate;
		this.batchNumber = batchNumber;
	}
	
	public int getBatchNumber() {
		return batchNumber;
	}
	
	public void setBatchNumber(int batchNumber) {
		this.batchNumber = batchNumber;
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
