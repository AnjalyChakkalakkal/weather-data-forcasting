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

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import au.com.cba.weatherforcasting.algorithm.Record;
import au.com.cba.weatherforcasting.algorithm.WeatherRecord;
import au.com.cba.weatherforcasting.utils.WeatherConstants;
import au.com.cba.weatherforcasting.utils.WeatherSimulationHelper;

/**
 * <p>
 * A class that determines when the date to which data is to forecast.
 * </p>
 * 
 * @author Anjaly Chakkalakkal
 *
 */
public class WeatherForcastingEngine {

	private WeatherHistory weatherHistory;
	private Logger logger = LogManager.getLogger(WeatherForcastingEngine.class);

	public WeatherForcastingEngine(final WeatherHistory weatherHistory) {

		this.weatherHistory = weatherHistory;
	}

	/**
	 * <p>
	 * This function is the starting point of weather forecasting. The function will continuously called the sliding
	 * window until the condition met.„
	 * </p>
	 * 
	 * @param forcastingDate
	 *            date to which forecasting should start.
	 * @param numberOfYearsToPredict
	 *            number of days weather to forecast
	 * @throws Exception
	 *             when ever some validation failed.
	 */
	public void startForcasting(Date forcastingDate, final int numberOfYearsToPredict) throws Exception {
		int days = getNumberOfDaysAfter(forcastingDate, numberOfYearsToPredict);
		int counter = 0;
		do {
			List<Record> currentRecord = getPastDaysWeatherRecord(forcastingDate, -7);
			Date lastYearDate = getLastYearDate(forcastingDate);
			List<Record> previousRecord = getPastDaysWeatherRecord(lastYearDate, -14);
			WeatherDataBuilder weatherDataBuilder = new WeatherDataBuilder(previousRecord, currentRecord);
			WeatherRecord weatherVariation = (WeatherRecord) weatherDataBuilder.findWeatherVariation();
			createPredictedWeatherRecord(forcastingDate, weatherVariation);
			forcastingDate = getDate(forcastingDate, 1);
			counter++;
		} while (counter != days);
	}

	/**
	 * <p>
	 * This function will create a new prediction based on the variation calculated using sliding window
	 * </p>
	 * 
	 * @param forcastingDate
	 *            the date to which forecasting should start
	 * @param weatherVariation
	 *            the variation calculated
	 * @throws ParseException
	 */
	private void createPredictedWeatherRecord(final Date forcastingDate, final WeatherRecord weatherVariation)
			throws ParseException {

		Date yesterday = getDate(forcastingDate, -1);
		WeatherRecord record = (WeatherRecord) getRecord(yesterday);
		WeatherRecord predictedRecord = new WeatherRecord();
		predictedRecord.setStation(this.weatherHistory.getStationName());
		predictedRecord.setTemperature(record.getTemperature() + weatherVariation.getTemperature());
		predictedRecord.setHumidity(record.getHumidity() + weatherVariation.getHumidity());
		predictedRecord.setPressure(record.getPressure() + weatherVariation.getPressure());

		String predictionDate = WeatherSimulationHelper.getInstance().convertDateToString(
				WeatherConstants.DATE_FORMAT, forcastingDate);
		predictedRecord.setLocalTime(predictionDate);
		logger.info(predictedRecord.getFormattedData());
		this.weatherHistory.getHistoryRecord().put(predictionDate, predictedRecord);
	}

	/**
	 * <p>
	 * This function will give number of days from a given date
	 * </p>
	 * 
	 * @param forcastingDate
	 *            this is the given date.
	 * @param numberOfYearsToPredict
	 *            number of years to consider.
	 * @return number of days.
	 */
	private int getNumberOfDaysAfter(Date forcastingDate, int numberOfYearsToPredict) {
		int numberOfDays = 0;
		for (int index = 1; index <= numberOfYearsToPredict; index++) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(forcastingDate);
			calendar.add(Calendar.YEAR, index);
			numberOfDays += calendar.getActualMaximum(Calendar.DAY_OF_YEAR);
		}
		return numberOfDays;
	}

	/**
	 * <p>
	 * This function will be used to get past weather record from history
	 * </p>
	 * 
	 * @param date
	 *            from date to which record should be fetched.
	 * @param numberOfDays
	 *            number of days need to consider for fetching.
	 * @return a list of record.
	 * @throws ParseException
	 *             when date is parsing this exception is thrown
	 */
	private List<Record> getPastDaysWeatherRecord(Date date, int numberOfDays) throws ParseException {

		List<Record> records = new ArrayList<Record>();
		for (int day = -1; day >= numberOfDays; day--) {
			Date yesterday = getDate(date, day);
			Record record = getRecord(yesterday);
			records.add(record);
		}
		return records;
	}

	/**
	 * <p>
	 * Get record for a day from history using the given date.
	 * </p>
	 * 
	 * @param yesterday
	 *            the day for querying record.
	 * @return a record that is queried.
	 * @throws ParseException
	 *             when date is parsing this exception is thrown.
	 */
	private Record getRecord(Date yesterday) throws ParseException {
		String lastDay = WeatherSimulationHelper.getInstance().convertDateToString(WeatherConstants.DATE_FORMAT,
				yesterday);
		return this.weatherHistory.getHistoryRecord().get(lastDay);
	}

	/**
	 * <p>
	 * Get date after a specified date passed as parameter
	 * </p>
	 * 
	 * @param date
	 *            starting date
	 * @param day
	 *            number of days to be considered
	 * @return a date based on the condition.
	 */
	private Date getDate(Date date, int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, day);
		return calendar.getTime();
	}

	/**
	 * <p>
	 * This function will fetch the last year date with the date passed in as parameter
	 * </p>
	 * 
	 * @param forcastingDate
	 *            the data that need to be considered to find the targeted date.
	 * @return a date
	 */
	private Date getLastYearDate(Date forcastingDate) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(forcastingDate);
		calendar.add(Calendar.YEAR, -1);
		calendar.add(Calendar.DATE, 7);
		return calendar.getTime();
	}
}
