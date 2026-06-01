package ma.ac.esi.culinaryarts.model;

public class Recipe {
    private int id;
    private String title;
    private String category;
    private String chefName;
    private String description;
    private double price;
    private String imageUrl;

    // Constructeurs, Getters et Setters
    public Recipe(int id, String title, String category, String chefName, String description, double price, String imageUrl) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.chefName = chefName;
        this.description = description;
        this.price = price;
        this.imageUrl= imageUrl;
    }
    public Recipe() {}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getChefName() {
		return chefName;
	}
	public void setChefName(String chefName) {
		this.chefName = chefName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	@Override
	public String toString() {
	    return "Recipe [id=" + id + ", title=" + title + ", category=" + category + ", chef=" + chefName + "]";
	}
    
}