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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import au.com.cba.weatherforcasting.WeatherDataBuilderException;
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
public class WeatherSimulationHelper {

	private Logger logger = LogManager.getLogger(WeatherSimulationHelper.class);

	private static WeatherSimulationHelper instance;
	private static Object mutex = new Object();

	private WeatherSimulationHelper() {

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
	 * @throws WeatherSimulationHelperException
	 */
	public Distance calculateEuclideanDistance(final Window<Record> window, final List<Record> records)
			throws WeatherSimulationHelperException {

		checkSizeAreEqual(window.getRecords().size(), records.size());
		Distance distance = new Distance();
		distance.setRecords(records);
		distance.setWindow(window);

		double sum = 0;
		for (int index = 0; index < records.size(); index++) {
			WeatherRecord previousRecord = (WeatherRecord) window.getRecords().get(index);
			WeatherRecord currentRecord = (WeatherRecord) records.get(index);
			sum += Math.pow((previousRecord.getHumidity() - currentRecord.getHumidity()), 2) +
					Math.pow((previousRecord.getTemperature() - currentRecord.getTemperature()), 2) +
					Math.pow((previousRecord.getPressure() - currentRecord.getPressure()), 2);
		}
		distance.setDistance(Math.round(Math.sqrt(sum) * 10000.0) / 10000.0);
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
		return minDistance;
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
			finalMeanRecord.setPressure(pressure);
			float humidity = finalMeanRecord.getHumidity() + ((WeatherRecord) record).getHumidity();
			finalMeanRecord.setHumidity(humidity);
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
	 * <p>
	 * Validate whether previous data and current data is of same size.
	 * </p>
	 * 
	 * @param previousDataSize
	 *            size of history data
	 * @param currentDataSize
	 *            size of current data
	 * @throws WeatherDataBuilderException
	 */
	public void checkSizeAreEqual(final int previousDataSize, final int currentDataSize)
			throws WeatherSimulationHelperException {
		if (previousDataSize != currentDataSize)
			throw new WeatherSimulationHelperException(
					"Cannot calculate distance if size of previous data and current data is not same.");
	}

	/**
	 * <p>
	 * A function that format string based on the format passed to it.
	 * </p>
	 * 
	 * @param format
	 *            parameter that represent format of the date.
	 * @param date
	 *            parameter that represent date to be formatted.
	 * @return a date object after formatting.
	 * @throws ParseException
	 *             throws when date cannot be parsed to give format.
	 */
	public Date getFormatterDate(String format, String date) throws ParseException {

		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.parse(date);
	}

	/**
	 * <p>
	 * A function to convert date to string
	 * </p>
	 * 
	 * @param format
	 *            parameter that represent a format to which date to be converted.
	 * @param date
	 *            parameter that represent a date that to be formatted.
	 * @return a string representation of date.
	 * @throws ParseException
	 *             throws when date cannot be parsed to give format.
	 */
	public String convertDateToString(String format, Date date) throws ParseException {

		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(date);
	}

	/**
	 * A static function that is thread safe to ensure only one instance of object is used through out the application.
	 * 
	 * @return an instance of Util object.
	 */
	public static WeatherSimulationHelper getInstance() {

		if (instance == null) {
			synchronized (mutex) {
				instance = new WeatherSimulationHelper();
			}
		}
		return instance;
	}
}
