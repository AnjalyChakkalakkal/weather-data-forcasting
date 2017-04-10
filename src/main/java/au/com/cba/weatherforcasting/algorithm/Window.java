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

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * A class that represent a window which include a set of Weather Record.
 * </p>
 * 
 * @author Anjaly Chakkalakkal
 *
 */
public class Window {

	List<WeatherRecord> weatherRecords;

	public Window() {

		this.weatherRecords = new ArrayList<WeatherRecord>(7);
	}

	public Window(int size) {

		this.weatherRecords = new ArrayList<WeatherRecord>();
	}

	public List<WeatherRecord> getWeatherRecords() {
		return weatherRecords;
	}

	public void setWeatherRecords(List<WeatherRecord> weatherRecords) {
		this.weatherRecords = weatherRecords;
	}
}
