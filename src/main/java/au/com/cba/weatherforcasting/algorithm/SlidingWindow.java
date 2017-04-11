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

import java.util.ArrayList;
import java.util.List;

import au.com.cba.weatherforcasting.WeatherDataBuilderException;

/**
 * <p>
 * Implementation of sliding window algorithm.
 * 
 * Given a larger integer buffer/array (say size, x), window size (say, n) and a number (say, k). Windows starts from
 * the 1stelement and keeps shifting right by one element.The objective is to find the minimum k numbers present in each
 * window. This is commonly solved using sliding window algorithm.
 * </p>
 * 
 * @author Anjaly Chakkalakkal
 *
 */

public class SlidingWindow {

	private int windowSize;

	public SlidingWindow(final int windowSize) {

		this.windowSize = windowSize;
	}

	/**
	 * <p>
	 * Prepare windows for performing sliding window algorithm.
	 * 
	 * @param records
	 * @throws WeatherDataBuilderException
	 */
	public List<Window<Record>> prepareSlidingWindows(final List<Record> records) throws SlidingWindowException {

		validateRecords(records);

		return constructWindow(records);
	}

	/**
	 * <p>
	 * This function splits the list of record into fixed sized windows
	 * 
	 * @param records
	 *            Records that are to be split.
	 * @return a List contain Windows of record.
	 */
	private List<Window<Record>> constructWindow(final List<Record> records) {

		List<Window<Record>> windows = new ArrayList<Window<Record>>();

		Window<Record> window = new Window<Record>();

		int windowCounter = this.windowSize;

		int noOfWindows = (records.size() - windowCounter) + 1;

		for (int index = 0; index < noOfWindows; index++) {
			for (int internalIndex = index; internalIndex < windowCounter; internalIndex++) {
				window.addRecord(records.get(internalIndex));
			}
			windows.add(window);
			window = new Window<Record>();
			windowCounter++;
		}
		return windows;
	}

	/**
	 * <p>
	 * This function validates the record size is greater than or equal to window size
	 * </p>
	 * 
	 * @param records
	 *            List of record for creating window
	 * @throws WeatherDataBuilderException
	 *             throw when criteria is not met.
	 */
	private void validateRecords(final List<Record> records) throws SlidingWindowException {

		if (records.size() < this.windowSize)
			throw new SlidingWindowException("Records used for creating windows should be more than window size");
	}

	public int getWindowSize() {
		return windowSize;
	}

	public void setWindowSize(int windowSize) {
		this.windowSize = windowSize;
	}
}
