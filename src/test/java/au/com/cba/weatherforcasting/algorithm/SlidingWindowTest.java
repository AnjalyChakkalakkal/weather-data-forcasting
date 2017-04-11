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

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * <p>
 * Test case for Sliding Window class.
 * </p>
 * 
 * @author Anjaly Chakkalakkal
 *
 */

public class SlidingWindowTest {

	private SlidingWindow slidingWindow;
	private List<Record> records;
	private Logger logger = LogManager.getLogger(SlidingWindowTest.class);

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {

		this.slidingWindow = new SlidingWindow(3);
		this.records = new ArrayList<Record>();
		populateWeatherRecord();

	}

	@Test
	public void test_prepareSlidingWindows_success() {

		try {
			List<Window<Record>> slidingWindow = this.slidingWindow.prepareSlidingWindows(this.records);
			int size = slidingWindow.size();
			assertEquals(5, size);

		} catch (SlidingWindowException exception) {
			// Swallowing exception here.
		}
	}

	@Test
	public void test_prepareSlidingWindows_1_success() {

		try {
			this.slidingWindow.setWindowSize(2);
			List<Window<Record>> slidingWindow = this.slidingWindow.prepareSlidingWindows(this.records);
			int size = slidingWindow.size();
			assertEquals(6, size);

		} catch (SlidingWindowException exception) {
			// Swallowing exception here.
		}
	}

	private void populateWeatherRecord() {
		WeatherRecord record = new WeatherRecord();
		record.setStation("Sydney");
		record.setTemperature(1);
		this.records.add(record);
		record = new WeatherRecord();
		record.setStation("Sydney");
		record.setTemperature(2);
		this.records.add(record);
		record = new WeatherRecord();
		record.setStation("Sydney");
		record.setTemperature(3);
		this.records.add(record);
		record = new WeatherRecord();
		record.setStation("Sydney");
		record.setTemperature(4);
		this.records.add(record);
		record = new WeatherRecord();
		record.setStation("Sydney");
		record.setTemperature(5);
		this.records.add(record);
		record = new WeatherRecord();
		record.setStation("Sydney");
		record.setTemperature(6);
		this.records.add(record);
		record = new WeatherRecord();
		record.setStation("Sydney");
		record.setTemperature(7);
		this.records.add(record);
	}
}
