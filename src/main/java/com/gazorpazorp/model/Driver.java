package com.gazorpazorp.model;

public class Driver {
	private Long id;
	private Long userId;
	private Car car;
	
	private String profileImageId;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Car getCar() {
		return car;
	}
	public void setCar(Car car) {
		this.car = car;
	}
	public String getProfileImageId() {
		return profileImageId;
	}
	public void setProfileImageId(String profileImageId) {
		this.profileImageId = profileImageId;
	}
	
	public class Car {		
		private String make;
		private String model;
		private String year;
		private String colour;
		private String plate;
		
		public Car() {}

		public String getMake() {
			return make;
		}
		public void setMake(String make) {
			this.make = make;
		}

		public String getModel() {
			return model;
		}
		public void setModel(String model) {
			this.model = model;
		}

		public String getYear() {
			return year;
		}
		public void setYear(String year) {
			this.year = year;
		}

		public String getColour() {
			return colour;
		}
		public void setColour(String colour) {
			this.colour = colour;
		}

		public String getPlate() {
			return plate;
		}
		public void setPlate(String plate) {
			this.plate = plate;
		}		
	}
	
}
