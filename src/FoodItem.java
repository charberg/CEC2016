
public class FoodItem {

	String name;
	Integer restockLimit;
	Integer popularity;
	Integer stock;
	
	public FoodItem(String name, int restockLimit, int popularity, int stock) {
		this.name = name;
		this.restockLimit = restockLimit;
		this.popularity = popularity;
		this.stock = stock;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Integer getRestockLimit() {
		return restockLimit;
	}

	public void setRestockLimit(Integer limit) {
		this.restockLimit = limit;
	}
	
	public Integer getPopularity() {
		return popularity;
	}

	public void setPopularity(Integer limit) {
		this.popularity = limit;
	}
	
}
