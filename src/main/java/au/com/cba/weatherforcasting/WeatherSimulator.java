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

import java.io.File;
import java.util.Date;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Scanner;
import java.util.StringTokenizer;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import au.com.cba.weatherforcasting.algorithm.WeatherRecord;
import au.com.cba.weatherforcasting.utils.WeatherConstants;
import au.com.cba.weatherforcasting.utils.WeatherSimulationHelper;

/**
 * <p>
 * Main entry point of the application, Extra arguments are received from this class. This class act a thread for
 * handling multiple weather station. History file is loaded based on the configuration file and data is predicted.
 * </p>
 * 
 * @author Anjaly Chakkalakkal
 *
 */
public class WeatherSimulator implements Runnable {

	private String weatherMetaData;
	private Logger logger = LogManager.getLogger(WeatherSimulator.class);

	public WeatherSimulator() {
	}

	public WeatherSimulator(String weatherMetaData) {

		this.weatherMetaData = weatherMetaData;
	}

	/**
	 * <p>
	 * This function marks the entry point of the application. This function loads configuration data and start weather
	 * forecasting using the history data and sliding window algorithm.
	 * </p>
	 * 
	 * @param args
	 *            the argument to the application.
	 * @throws Exception
	 *             the final exception point that is given back to JVM.
	 */
	public static void main(final String[] args) throws Exception {
		BasicConfigurator.configure();

		WeatherSimulator simulator = new WeatherSimulator();
		Properties configurationFile = simulator.loadConfigurationFile();

		for (Entry<Object, Object> entry : configurationFile.entrySet()) {
			WeatherSimulator weatherSimulator = new WeatherSimulator((String) entry.getValue());
			final Thread simulatorThread = new Thread(weatherSimulator, (String) entry.getKey());
			simulatorThread.start();
		}
	}

	@Override
	public void run() {

		try {

			initializeAndloadHistory();
		} catch (Exception exception) {
			logger.error(exception, exception);
		}
	}

	/**
	 * <p>
	 * Parse the configuration and read history files for weather data prediction. Configuration contain the location of
	 * the history files and date from which prediction is to be made and number of days consider for prediction. It
	 * then create record object from the history file and construct weather history object that is futher used for
	 * prediction.
	 * </p>
	 * 
	 * @throws Exception
	 *             is thrown when ever a validation fails.
	 */
	private void initializeAndloadHistory() throws Exception {

		StringTokenizer metaTokenizer = new StringTokenizer(this.weatherMetaData, WeatherConstants.METADATA_SEPARATOR);
		String fileName = (String) metaTokenizer.nextElement();
		String startDate = (String) metaTokenizer.nextElement();
		int numberOfYearsToPredict = Integer.parseInt((String) metaTokenizer.nextElement());
		try (Scanner scanner = new Scanner(this.getClass().getResourceAsStream(File.separator + fileName)))
		{
			WeatherHistory weatherHistory = new WeatherHistory();
			weatherHistory.setStationName(Thread.currentThread().getName());

			while (scanner.hasNext()) {
				WeatherRecord weatherRecord = new WeatherRecord();
				StringTokenizer stringTokenizer = new StringTokenizer(scanner.nextLine(),
						WeatherConstants.DATA_SEPARATOR);
				String date = (String) stringTokenizer.nextElement();
				weatherRecord.setStation(Thread.currentThread().getName());
				weatherRecord.setLocalTime(date);
				weatherRecord.setTemperature(Float.valueOf((String) stringTokenizer.nextElement()));
				weatherRecord.setHumidity(Float.valueOf((String) stringTokenizer.nextElement()));
				weatherRecord.setPressure(Float.valueOf((String) stringTokenizer.nextElement()));
				weatherHistory.getHistoryRecord().put(date, weatherRecord);
			}

			logger.info("Loaded weather history for " + Thread.currentThread().getName());
			Date date = WeatherSimulationHelper.getInstance().getFormatterDate(WeatherConstants.DATE_FORMAT, startDate);
			WeatherForcastingEngine forcastingEngine = new WeatherForcastingEngine(weatherHistory);
			logger.info("Starting Weather Forcasting for " + Thread.currentThread().getName());
			forcastingEngine.startForcasting(date, numberOfYearsToPredict);
		}
	}

	/**
	 * <p>
	 * This function load configuration file from the resource folder.
	 * </p>
	 * 
	 * @return a property that was created from the configuration file.
	 * @throws Exception
	 *             is thrown when the file is not available in the location.
	 */
	private Properties loadConfigurationFile() throws Exception {

		Properties configuration = new Properties();
		configuration.load(this.getClass().getResourceAsStream(WeatherConstants.CONFIG_PATH));
		return configuration;
	}
}
