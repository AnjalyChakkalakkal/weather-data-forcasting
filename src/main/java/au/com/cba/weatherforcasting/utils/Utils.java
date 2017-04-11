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
package au.com.cba.weatherforcasting.utils;

import java.util.List;

import au.com.cba.weatherforcasting.algorithm.Record;
import au.com.cba.weatherforcasting.algorithm.WeatherRecord;
import au.com.cba.weatherforcasting.algorithm.Window;

/**
 * <p>
 * Utility A singleton class that provide some basic functionality to main classes
 * </p>
 * 
 * @author Anjaly Chakkalakkal
 *
 */
public class Utils {

	private static Utils instance;
	private static Object mutex = new Object();

	private Utils() {

	}

	public Distance calculateEuclideanDistance(final Window<Record> window, final List<Record> records) {

		Distance distance = new Distance();
		distance.setRecords(records);
		distance.setWindow(window);

		double sum = 0;
		for (int index = 0; index < records.size(); index++) {
			WeatherRecord previousRecord = (WeatherRecord) window.getRecords().get(index);
			WeatherRecord currentRecord = (WeatherRecord) records.get(index);
			sum += Math.pow((previousRecord.getHumidity() - currentRecord.getHumidity()), 2d) +
					Math.pow((previousRecord.getTemperature() - currentRecord.getTemperature()), 2d) +
					Math.pow((previousRecord.getPressure() - currentRecord.getPressure()), 2d);
		}
		distance.setDistance(Math.round(Math.sqrt(sum) * 1000.0) / 1000.0);
		return distance;
	}

	public Distance getMinimumDistance(final List<Distance> calculatedDistance) {
		Distance minDistance = calculatedDistance.get(0);
		for (Distance distance : calculatedDistance) {
			if (distance.getDistance() < minDistance.getDistance()) {
				minDistance = distance;
			}
		}
		return null;
	}

	/**
	 * A static function that is thread safe to ensure only one instance of object is used through out the application.
	 * 
	 * @return an instance of Util object.
	 */
	public static Utils getInstance() {

		if (instance == null) {
			synchronized (mutex) {
				instance = new Utils();
			}
		}
		return instance;
	}
}
