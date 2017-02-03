package org.limingnihao.model;

import java.io.Serializable;

public class SortBean implements Serializable {

	private String property;
	private String direction;

	public SortBean(){}

	public SortBean(String property, String direction) {
		this.property = property;
		this.direction = direction;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

}
