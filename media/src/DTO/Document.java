package DTO;

public class Document {

	private int id;
	private String name;
	private boolean isAvailable;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isAvailable() {
		return isAvailable;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}
	@Override
	public String toString() {
		return "[Nom:"+name+", stock:"+isAvailable+"]";
	}
}
