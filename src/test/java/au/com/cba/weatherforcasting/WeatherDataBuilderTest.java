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

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import au.com.cba.weatherforcasting.algorithm.Record;
import au.com.cba.weatherforcasting.algorithm.WeatherRecord;

/**
 * <p>
 * Test case for WeatherDataBuilder.
 * </p>
 * 
 * @author Anjaly Chakkalakkal
 *
 */
public class WeatherDataBuilderTest {

	private List<Record> previousRecord;
	private List<Record> currentRecord;
	private WeatherDataBuilder weatherDataBuilder;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		populatePreviousRecord();
		populateCurrentRecord();
		this.weatherDataBuilder = new WeatherDataBuilder(this.previousRecord, this.currentRecord);
	}

	@Test
	public void test_findWeatherVariation_success() throws WeatherDataBuilderException {
		WeatherRecord variation = (WeatherRecord) this.weatherDataBuilder.findWeatherVariation();
		Assert.assertNotNull(variation);
		Assert.assertEquals(0.6666667, variation.getTemperature(), 1);
		Assert.assertEquals(0.6666667, variation.getPressure(), 1);
		Assert.assertEquals(-0.16666667, variation.getHumidity(), 1);
	}

	private void populatePreviousRecord() {

		this.previousRecord = new ArrayList<Record>();
		WeatherRecord weatherRecord = new WeatherRecord();
		weatherRecord.setStation("Sydney");
		weatherRecord.setTemperature(19);
		weatherRecord.setHumidity(97);
		weatherRecord.setPressure(1010.3f);
		this.previousRecord.add(weatherRecord);

		weatherRecord = new WeatherRecord();
		weatherRecord.setStation("Sydney");
		weatherRecord.setTemperature(17);
		weatherRecord.setHumidity(100);
		weatherRecord.setPressure(988);
		this.previousRecord.add(weatherRecord);

		weatherRecord = new WeatherRecord();
		weatherRecord.setStation("Sydney");
		weatherRecord.setTemperature(20);
		weatherRecord.setHumidity(93);
		weatherRecord.setPressure(1020.6f);
		this.previousRecord.add(weatherRecord);

		weatherRecord = new WeatherRecord();
		weatherRecord.setStation("Sydney");
		weatherRecord.setTemperature(15);
		weatherRecord.setHumidity(103);
		weatherRecord.setPressure(800.4f);
		this.previousRecord.add(weatherRecord);

		weatherRecord = new WeatherRecord();
		weatherRecord.setStation("Sydney");
		weatherRecord.setTemperature(17);
		weatherRecord.setHumidity(103);
		weatherRecord.setPressure(859);
		weatherRecord = new WeatherRecord();
		this.previousRecord.add(weatherRecord);

		weatherRecord.setStation("Sydney");
		weatherRecord.setTemperature(19);
		weatherRecord.setHumidity(98);
		weatherRecord.setPressure(1015.3f);
		this.previousRecord.add(weatherRecord);

		weatherRecord = new WeatherRecord();
		weatherRecord.setStation("Sydney");
		weatherRecord.setTemperature(19);
		weatherRecord.setHumidity(97);
		weatherRecord.setPressure(1010.3f);
		this.previousRecord.add(weatherRecord);

		this.previousRecord = new ArrayList<Record>();
		weatherRecord.setStation("Sydney");
		weatherRecord.setTemperature(19);
		weatherRecord.setHumidity(97);
		weatherRecord.setPressure(1010.3f);
		this.previousRecord.add(weatherRecord);

		weatherRecord = new WeatherRecord();
		weatherRecord.setStation("Sydney");
		weatherRecord.setTemperature(17);
		weatherRecord.setHumidity(100);
		weatherRecord.setPressure(988);
		this.previousRecord.add(weatherRecord);

		weatherRecord = new WeatherRecord();
		weatherRecord.setStation("Sydney");
		weatherRecord.setTemperature(20);
		weatherRecord.setHumidity(93);
		weatherRecord.setPressure(1020.6f);
		this.previousRecord.add(weatherRecord);

		weatherRecord = new WeatherRecord();
		weatherRecord.setStation("Sydney");
		weatherRecord.setTemperature(15);
		weatherRecord.setHumidity(103);
		weatherRecord.setPressure(800.4f);
		this.previousRecord.add(weatherRecord);

		weatherRecord = new WeatherRecord();
		weatherRecord.setStation("Sydney");
		weatherRecord.setTemperature(17);
		weatherRecord.setHumidity(103);
		weatherRecord.setPressure(859);
		weatherRecord = new WeatherRecord();
		this.previousRecord.add(weatherRecord);

		weatherRecord.setStation("Sydney");
		weatherRecord.setTemperature(19);
		weatherRecord.setHumidity(98);
		weatherRecord.setPressure(1015.3f);
		this.previousRecord.add(weatherRecord);

		weatherRecord = new WeatherRecord();
		weatherRecord.setStation("Sydney");
		weatherRecord.setTemperature(19);
		weatherRecord.setHumidity(97);
		weatherRecord.setPressure(1010.3f);
		this.previousRecord.add(weatherRecord);
	}

	private void populateCurrentRecord() {

		this.currentRecord = new ArrayList<Record>();

		WeatherRecord weatherRecord = new WeatherRecord();
		weatherRecord.setStation("Sydney");
		weatherRecord.setTemperature(20);
		weatherRecord.setHumidity(98);
		weatherRecord.setPressure(1011.3f);
		this.currentRecord.add(weatherRecord);

		weatherRecord = new WeatherRecord();
		weatherRecord.setStation("Sydney");
		weatherRecord.setTemperature(18);
		weatherRecord.setHumidity(101);
		weatherRecord.setPressure(989);
		this.currentRecord.add(weatherRecord);

		weatherRecord = new WeatherRecord();
		weatherRecord.setStation("Sydney");
		weatherRecord.setTemperature(19);
		weatherRecord.setHumidity(94);
		weatherRecord.setPressure(1010.6f);
		this.currentRecord.add(weatherRecord);

		weatherRecord = new WeatherRecord();
		weatherRecord.setStation("Sydney");
		weatherRecord.setTemperature(16);
		weatherRecord.setHumidity(104);
		weatherRecord.setPressure(850.4f);
		this.currentRecord.add(weatherRecord);

		weatherRecord = new WeatherRecord();
		weatherRecord.setStation("Sydney");
		weatherRecord.setTemperature(18);
		weatherRecord.setHumidity(105);
		weatherRecord.setPressure(890);
		weatherRecord = new WeatherRecord();
		this.currentRecord.add(weatherRecord);

		weatherRecord.setStation("Sydney");
		weatherRecord.setTemperature(18);
		weatherRecord.setHumidity(100);
		weatherRecord.setPressure(1011.3f);
		this.currentRecord.add(weatherRecord);

		weatherRecord = new WeatherRecord();
		weatherRecord.setStation("Sydney");
		weatherRecord.setTemperature(20);
		weatherRecord.setHumidity(96);
		weatherRecord.setPressure(1019.3f);
		this.currentRecord.add(weatherRecord);
	}
}
