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
import java.util.Calendar;
import java.util.Scanner;
import java.util.StringTokenizer;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import au.com.cba.weatherforcasting.algorithm.WeatherRecord;
import au.com.cba.weatherforcasting.utils.WeatherConstants;

/**
 * <p>
 * Test case for WeatherForcastingEngineTest.
 * </p>
 * 
 * @author Anjaly Chakkalakkal
 *
 */
public class WeatherForcastingEngineTest {

	private WeatherHistory weatherHistory;
	private WeatherForcastingEngine weatherForcastingEngine;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {

		populateWeatherHistory();

		this.weatherForcastingEngine = new WeatherForcastingEngine(this.weatherHistory);
	}

	@Test
	public void test_startForcasting_success() throws Exception {
		Calendar calendar = Calendar.getInstance();
		calendar.set(2017, 3, 12);
		this.weatherForcastingEngine.startForcasting(calendar.getTime(), 2);
	}

	@Test(expected = NullPointerException.class)
	public void test_startForcasting_failure() throws Exception {
		Calendar calendar = Calendar.getInstance();
		calendar.set(2017, 4, 12);
		WeatherForcastingEngine weatherForcastingEngine = new WeatherForcastingEngine(null);
		weatherForcastingEngine.startForcasting(calendar.getTime(), 2);
	}

	private void populateWeatherHistory() {
		try (Scanner scanner = new Scanner(this.getClass().getResourceAsStream(File.separator + "SYDNEY.CSV")))
		{
			this.weatherHistory = new WeatherHistory();
			this.weatherHistory.setStationName("SYDNEY");

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
				this.weatherHistory.getHistoryRecord().put(date, weatherRecord);
			}
		}
	}
}
