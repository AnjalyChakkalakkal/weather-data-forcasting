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

import java.util.List;

import au.com.cba.weatherforcasting.algorithm.Record;
import au.com.cba.weatherforcasting.algorithm.Window;

/**
 * <p>
 * A class that represent a window which include a set of Weather Record.
 * </p>
 * 
 * @author Anjaly Chakkalakkal
 *
 */
public class Distance {

	private Window<Record> window;
	private List<Record> records;
	private double distance;
	private int index;

	public Window<Record> getWindow() {
		return window;
	}

	public void setWindow(Window<Record> window) {
		this.window = window;
	}

	public List<Record> getRecords() {
		return records;
	}

	public void setRecords(List<Record> records) {
		this.records = records;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
}
