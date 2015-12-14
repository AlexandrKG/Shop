package domain;

public class Bird extends Goods {

	private int lifetime;
	private int feedNumber;
	private boolean flight = true;

	public Bird() {

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
