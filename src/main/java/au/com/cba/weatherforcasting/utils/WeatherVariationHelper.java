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

import java.util.ArrayList;
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
public class WeatherVariationHelper {

	private static WeatherVariationHelper instance;
	private static Object mutex = new Object();

	private WeatherVariationHelper() {

	}

	/**
	 * <p>
	 * This function will help to find distance between two set of record using Euclidean Distance
	 * </p>
	 * 
	 * @param window
	 *            list of record from previous year.
	 * @param records
	 *            list of record from current year.
	 * @return distance between two list of records.
	 */
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

	/**
	 * <p>
	 * This function helps to find the minimum distance from a list of distance.
	 * 
	 * @param calculatedDistance
	 *            the list of distance from min distance to calculate.
	 * @return the minimum calculated distance.
	 */
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
	 * <p>
	 * find the mean of temperature pressure and humidity from a given list of records
	 * </p>
	 * 
	 * @param records
	 *            a list of records from mean is to be calculated
	 * @return a record contain mean of three parameters.
	 */
	public Record calculateMean(final List<Record> records) {
		WeatherRecord finalMeanRecord = new WeatherRecord();
		for (Record record : records) {
			float tempreature = finalMeanRecord.getTemperature() + ((WeatherRecord) record).getTemperature();
			finalMeanRecord.setTemperature(tempreature);
			float pressure = finalMeanRecord.getPressure() + ((WeatherRecord) record).getPressure();
			finalMeanRecord.setTemperature(pressure);
			float humidity = finalMeanRecord.getHumidity() + ((WeatherRecord) record).getHumidity();
			finalMeanRecord.setTemperature(humidity);
		}
		finalMeanRecord.setTemperature(finalMeanRecord.getTemperature() / records.size());
		finalMeanRecord.setHumidity(finalMeanRecord.getHumidity() / records.size());
		finalMeanRecord.setPressure(finalMeanRecord.getPressure() / records.size());
		return finalMeanRecord;
	}

	/**
	 * <p>
	 * A function to find weather variation from a list of data collected from the previous year and current year.
	 * </p>
	 * 
	 * @param records
	 *            list of weather data collected
	 * @return a list of record variations.
	 */
	public List<Record> calculateVariationMatrix(final List<Record> records) {
		WeatherRecord firstRecord = (WeatherRecord) records.get(0);
		List<Record> weatherRecords = new ArrayList<Record>();
		for (int index = 1; index < records.size(); index++) {
			WeatherRecord secondRecord = (WeatherRecord) records.get(index);
			WeatherRecord weatherRecord = new WeatherRecord();
			weatherRecord.setTemperature(secondRecord.getTemperature() - firstRecord.getTemperature());
			weatherRecord.setPressure(secondRecord.getPressure() - firstRecord.getPressure());
			weatherRecord.setHumidity(secondRecord.getHumidity() - firstRecord.getHumidity());
			weatherRecords.add(weatherRecord);
			firstRecord = secondRecord;
		}
		return weatherRecords;
	}

	/**
	 * A static function that is thread safe to ensure only one instance of object is used through out the application.
	 * 
	 * @return an instance of Util object.
	 */
	public static WeatherVariationHelper getInstance() {

		if (instance == null) {
			synchronized (mutex) {
				instance = new WeatherVariationHelper();
			}
		}
		return instance;
	}
}
