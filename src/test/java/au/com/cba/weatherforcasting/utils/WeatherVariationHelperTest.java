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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import au.com.cba.weatherforcasting.algorithm.Record;
import au.com.cba.weatherforcasting.algorithm.WeatherRecord;
import au.com.cba.weatherforcasting.algorithm.Window;

/**
 * <p>
 * Test case for Sliding Window class.
 * </p>
 * 
 * @author Anjaly Chakkalakkal
 *
 */

public class WeatherVariationHelperTest {

	private WeatherSimulationHelper weatherSimulationHelper;
	private Window<Record> windowRecord;
	private List<Record> records;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		this.weatherSimulationHelper = WeatherSimulationHelper.getInstance();
		populateWindowRecord();
		populateRecord();

	}

	@Test
	public void test_calculateEuclideanDistance_Success() throws WeatherSimulationHelperException {
		Distance distance = this.weatherSimulationHelper.calculateEuclideanDistance(this.windowRecord, this.records);
		Assert.assertNotNull(distance);
		double actualDistance = distance.getDistance();
		Assert.assertEquals(52.2972, actualDistance, 0.0);
	}

	@Test(expected = WeatherSimulationHelperException.class)
	public void test_calculateEuclideanDistance_Failure() throws WeatherSimulationHelperException {
		this.windowRecord.getRecords().remove(0);
		this.weatherSimulationHelper.calculateEuclideanDistance(this.windowRecord, this.records);
	}

	@Test
	public void test_getMinimumDistance_Success() {

		List<Distance> distances = populateDistance();
		Distance minimumDistance = this.weatherSimulationHelper.getMinimumDistance(distances);
		Assert.assertNotNull(minimumDistance);
		double minDistance = minimumDistance.getDistance();
		Assert.assertEquals(12.7, minDistance, 0.0);
	}

	@Test
	public void test_calculateMean_Success() {
		Record meanRecord = this.weatherSimulationHelper.calculateMean(this.records);
		Assert.assertNotNull(meanRecord);
		float meanTemperature = ((WeatherRecord) meanRecord).getTemperature();
		float meanHumidity = ((WeatherRecord) meanRecord).getHumidity();
		float meanPressure = ((WeatherRecord) meanRecord).getPressure();

		Assert.assertEquals(18.286, meanTemperature, 1);
		Assert.assertEquals(98.0, meanHumidity, 1);
		Assert.assertEquals(980.028, meanPressure, 1);
	}

	@Test
	public void test_calculateVariationMatrix_success() {
		List<Record> variationMatrix = this.weatherSimulationHelper.calculateVariationMatrix(this.records);
		Assert.assertNotNull(variationMatrix);
		Assert.assertEquals(6, variationMatrix.size());
	}

	@Test
	public void test_convertDateToString_success() throws ParseException {
		Calendar calendar = Calendar.getInstance();
		calendar.set(2017, 4, 12);
		String convertDateToString = this.weatherSimulationHelper.convertDateToString(WeatherConstants.DATE_FORMAT,
				calendar.getTime());
		Assert.assertNotNull(convertDateToString);
		Assert.assertEquals("12/05/2017", convertDateToString);
	}

	@Test
	public void test_getFormatterDate_success() {

	}

	private List<Distance> populateDistance() {
		List<Distance> distances = new ArrayList<Distance>();
		Distance distance = new Distance();
		distance.setDistance(30);
		distances.add(distance);

		distance = new Distance();
		distance.setDistance(35.0);
		distances.add(distance);

		distance = new Distance();
		distance.setDistance(20);
		distances.add(distance);

		distance = new Distance();
		distance.setDistance(12.7);
		distances.add(distance);
		return distances;
	}

	private void populateRecord() {

		this.records = new ArrayList<Record>();
		WeatherRecord weatherRecord = new WeatherRecord();
		weatherRecord.setStation("Sydney");
		weatherRecord.setTemperature(19);
		weatherRecord.setHumidity(97);
		weatherRecord.setPressure(1010.3f);
		this.records.add(weatherRecord);

		weatherRecord = new WeatherRecord();
		weatherRecord.setStation("Sydney");
		weatherRecord.setTemperature(17);
		weatherRecord.setHumidity(100);
		weatherRecord.setPressure(988);
		this.records.add(weatherRecord);

		weatherRecord = new WeatherRecord();
		weatherRecord.setStation("Sydney");
		weatherRecord.setTemperature(20);
		weatherRecord.setHumidity(93);
		weatherRecord.setPressure(1020.6f);
		this.records.add(weatherRecord);

		weatherRecord = new WeatherRecord();
		weatherRecord.setStation("Sydney");
		weatherRecord.setTemperature(15);
		weatherRecord.setHumidity(103);
		weatherRecord.setPressure(800.4f);
		this.records.add(weatherRecord);

		weatherRecord = new WeatherRecord();
		weatherRecord.setStation("Sydney");
		weatherRecord.setTemperature(17);
		weatherRecord.setHumidity(103);
		weatherRecord.setPressure(859);
		weatherRecord = new WeatherRecord();
		this.records.add(weatherRecord);

		weatherRecord.setStation("Sydney");
		weatherRecord.setTemperature(19);
		weatherRecord.setHumidity(98);
		weatherRecord.setPressure(1015.3f);
		this.records.add(weatherRecord);

		weatherRecord = new WeatherRecord();
		weatherRecord.setStation("Sydney");
		weatherRecord.setTemperature(19);
		weatherRecord.setHumidity(97);
		weatherRecord.setPressure(1010.3f);
		this.records.add(weatherRecord);
	}

	private void populateWindowRecord() {

		this.windowRecord = new Window<Record>();

		WeatherRecord weatherRecord = new WeatherRecord();
		weatherRecord.setStation("Sydney");
		weatherRecord.setTemperature(20);
		weatherRecord.setHumidity(98);
		weatherRecord.setPressure(1011.3f);
		this.windowRecord.getRecords().add(weatherRecord);

		weatherRecord = new WeatherRecord();
		weatherRecord.setStation("Sydney");
		weatherRecord.setTemperature(18);
		weatherRecord.setHumidity(101);
		weatherRecord.setPressure(989);
		this.windowRecord.getRecords().add(weatherRecord);

		weatherRecord = new WeatherRecord();
		weatherRecord.setStation("Sydney");
		weatherRecord.setTemperature(19);
		weatherRecord.setHumidity(94);
		weatherRecord.setPressure(1010.6f);
		this.windowRecord.getRecords().add(weatherRecord);

		weatherRecord = new WeatherRecord();
		weatherRecord.setStation("Sydney");
		weatherRecord.setTemperature(16);
		weatherRecord.setHumidity(104);
		weatherRecord.setPressure(850.4f);
		this.windowRecord.getRecords().add(weatherRecord);

		weatherRecord = new WeatherRecord();
		weatherRecord.setStation("Sydney");
		weatherRecord.setTemperature(18);
		weatherRecord.setHumidity(105);
		weatherRecord.setPressure(890);
		weatherRecord = new WeatherRecord();
		this.windowRecord.getRecords().add(weatherRecord);

		weatherRecord.setStation("Sydney");
		weatherRecord.setTemperature(18);
		weatherRecord.setHumidity(100);
		weatherRecord.setPressure(1011.3f);
		this.windowRecord.getRecords().add(weatherRecord);

		weatherRecord = new WeatherRecord();
		weatherRecord.setStation("Sydney");
		weatherRecord.setTemperature(20);
		weatherRecord.setHumidity(96);
		weatherRecord.setPressure(1019.3f);
		this.windowRecord.getRecords().add(weatherRecord);
	}
}
