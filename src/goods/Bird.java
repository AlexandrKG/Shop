package goods;

public class Bird extends Goods {

	private int lifetime;
	private int feedNumber;
	private boolean flight = true;

	public Bird() {
		this(0,0.0,0);
	}
	
	public Bird(int number,double price, int sold) {
		super(number,price,sold);
		category = "Birds";
	}

	public Bird(String name) {
		category = "Birds";
		this.name = name;
	}

	public boolean isFlight() {
		return flight;
	}

	public int getLifetime() {
		return lifetime;
	}

	public int getFeedNumber() {
		return feedNumber;
	}

	public void setLifetime(int lifetime) {
		this.lifetime = lifetime;
	}

	public void setFeedNumber(int feedNumber) {
		this.feedNumber = feedNumber;
	}

	public void setFlight(boolean flight) {
		this.flight = flight;
	}
}
