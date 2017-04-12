/*****************************************************************************
 * Copyright (C) 2017-2018 Anjaly Chakkalakkal 
 * 
 * All rights reserved.  Licensed to the Apache Software Foundation (ASF) 
 * under one or more contributor license agreements. See the NOTICE file 
 * distributed with this work for additional information regarding copyright 
 * ownership.
 * 
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *  http://www.apache.org/licenses/LICENSE-2.0
 *  
 ********************************************************************************/
package au.com.cba.weatherforcasting.algorithm;

import au.com.cba.weatherforcasting.utils.Condition;
import au.com.cba.weatherforcasting.utils.WeatherConstants;

/**
 * <p>
 * This class represent weather data for a particular day.
 * </p>
 * 
 * @author Anjaly Chakkalakkal
 *
 */
public class WeatherRecord implements Record {

	private String station;
	private String localTime;
	private Condition condition;
	private float temperature;
	private float pressure;
	private float humidity;

	public String getStation() {
		return station;
	}

	public void setStation(String station) {
		this.station = station;
	}

	public String getLocalTime() {
		return localTime;
	}

	public void setLocalTime(String localTime) {
		this.localTime = localTime;
	}

	public Condition getCondition() {
		return condition;
	}

	public void setCondition(Condition condition) {
		this.condition = condition;
	}

	public float getTemperature() {
		return temperature;
	}

	public void setTemperature(float temperature) {
		this.temperature = temperature;
		if (temperature < 0 || (temperature > 0 && temperature < 10)) {
			this.condition = Condition.COLD;
		} else if (temperature > 10 && temperature < 15) {
			this.condition = Condition.COLD;
		} else if (temperature > 15 && temperature < 20) {
			this.condition = Condition.CLOUDY;
		} else if (temperature > 20 && temperature < 25) {
			this.condition = Condition.RAINY;
		} else if (temperature > 25 && temperature < 30) {
			this.condition = Condition.SUNNY;
		} else if (temperature > 30 && temperature < 35) {
			this.condition = Condition.HOT;
		} else if (temperature > 35) {
			this.condition = Condition.WARM;
		}
	}

	public float getPressure() {
		return pressure;
	}

	public void setPressure(float pressure) {
		this.pressure = pressure;
	}

	public float getHumidity() {
		return humidity;
	}

	public void setHumidity(float humidity) {
		this.humidity = humidity;
	}

	@Override
	public String getFormattedData() {
		return this.station + WeatherConstants.METADATA_SEPARATOR + this.localTime
				+ WeatherConstants.METADATA_SEPARATOR + this.temperature
				+ WeatherConstants.METADATA_SEPARATOR + this.humidity + WeatherConstants.METADATA_SEPARATOR
				+ this.pressure + WeatherConstants.METADATA_SEPARATOR + this.condition;
	}
}
