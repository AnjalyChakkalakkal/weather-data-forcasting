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
package au.com.cba.weatherforcasting;

import java.util.LinkedHashMap;
import java.util.Map;

import au.com.cba.weatherforcasting.algorithm.Record;

/**
 * <p>
 * A class to hold the history of weather data.
 * </p>
 * 
 * @author Anjaly Chakkalakkal
 *
 */
public class WeatherHistory {

	private String stationName;
	private Map<String, Record> historyRecord;

	public WeatherHistory() {

		this.historyRecord = new LinkedHashMap<String, Record>();
	}

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public Map<String, Record> getHistoryRecord() {
		return historyRecord;
	}

	public void setHistoryRecord(Map<String, Record> historyRecord) {
		this.historyRecord = historyRecord;
	}
}
