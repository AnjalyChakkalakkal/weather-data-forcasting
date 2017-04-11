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
import java.util.Arrays;
import java.util.List;

import au.com.cba.weatherforcasting.algorithm.Record;
import au.com.cba.weatherforcasting.algorithm.SlidingWindow;
import au.com.cba.weatherforcasting.algorithm.SlidingWindowException;
import au.com.cba.weatherforcasting.algorithm.Window;
import au.com.cba.weatherforcasting.utils.Distance;
import au.com.cba.weatherforcasting.utils.WeatherVariationHelper;

/**
 * <p>
 * A Builder class to predict weather using Sliding Window algorithm.
 * </p>
 * 
 * @author Anjaly Chakkalakkal
 *
 */
public class WeatherDataBuilder {

	private List<Window<Record>> windows;
	private SlidingWindow slidingWindow = new SlidingWindow(7);
	private List<Record> previousRecord;
	private List<Record> currentRecord;

	/**
	 * <p>
	 * Constructor for WeatherDataBuilder which accepts list of data from previous year and data from current year
	 * </p>
	 * 
	 * @param previousRecord
	 *            data from previous year
	 * @param currentRecord
	 *            data from the current year
	 */
	public WeatherDataBuilder(final List<Record> previousRecord, final List<Record> currentRecord) {

		this.previousRecord = previousRecord;
		this.currentRecord = currentRecord;
	}

	/**
	 * <p>
	 * This function finds the final weather variation using sliding window algorithm and Euclidean distance/
	 * </p>
	 * 
	 * @return a record that contain final weather variation.
	 * @throws WeatherDataBuilderException
	 *             throws an excpetion if any of the validation fails.
	 */
	public Record findWeatherVariation() throws WeatherDataBuilderException {

		try {
			this.windows = this.slidingWindow.prepareSlidingWindows(this.previousRecord);
			List<Distance> calculatedDistance = calculateDistance(this.currentRecord);
			Distance minimumDistance = WeatherVariationHelper.getInstance().getMinimumDistance(calculatedDistance);
			List<Record> previousDataVariation = WeatherVariationHelper.getInstance().calculateVariationMatrix(
					minimumDistance.getWindow().getRecords());
			List<Record> currentDataVariation = WeatherVariationHelper.getInstance().calculateVariationMatrix(
					minimumDistance.getRecords());
			Record weatherVariation = getFinalWeatherVariation(previousDataVariation, currentDataVariation);
			return weatherVariation;

		} catch (SlidingWindowException exception) {
			throw new WeatherDataBuilderException(exception);
		}
	}

	/**
	 * <p>
	 * A function that calculate final weather variation form a list of past weather variation and current variation.
	 * </p>
	 * 
	 * @param previousDataVariation
	 *            variation from the past weather data.
	 * @param currentDataVariation
	 *            variation from the current weather data.
	 * @return a weather data record with final variation.
	 */
	private Record getFinalWeatherVariation(final List<Record> previousDataVariation,
			final List<Record> currentDataVariation) {

		Record previousMeanRecord = WeatherVariationHelper.getInstance().calculateMean(previousDataVariation);
		Record currentMeanRecord = WeatherVariationHelper.getInstance().calculateMean(currentDataVariation);
		List<Record> records = Arrays.asList(previousMeanRecord, currentMeanRecord);
		return WeatherVariationHelper.getInstance().calculateMean(records);
	}

	/**
	 * <p>
	 * Calculate the distance between sliding window and current data.
	 * </p>
	 * 
	 * @param records
	 *            a list of data
	 * @return a list of distance
	 * @throws WeatherDataBuilderException
	 */
	private List<Distance> calculateDistance(final List<Record> records) throws SlidingWindowException {

		validateRequiredData();

		List<Distance> distances = new ArrayList<Distance>();
		for (Window<Record> window : this.windows) {
			checkSizeAreEqual(window.getRecords().size(), records.size());
			distances.add(WeatherVariationHelper.getInstance().calculateEuclideanDistance(window, records));
		}
		return distances;
	}

	/**
	 * <p>
	 * This function validate the windows is created and it is not null
	 * </p>
	 * 
	 * @throws WeatherDataBuilderException
	 *             throws when the windows is not created.
	 */
	private void validateRequiredData() throws SlidingWindowException {

		if (this.windows == null || this.windows.isEmpty())
			throw new SlidingWindowException(
					"Windows not created to calculate distance between current data and previous data");
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
	private void checkSizeAreEqual(final int previousDataSize, final int currentDataSize) throws SlidingWindowException {
		if (previousDataSize != currentDataSize)
			throw new SlidingWindowException(
					"Cannot calculate distance if size of previous data and current data is not same.");
	}
}
