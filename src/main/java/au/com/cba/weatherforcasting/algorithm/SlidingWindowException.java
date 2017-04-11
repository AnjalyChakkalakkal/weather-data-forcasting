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

/**
 * <p>
 * An exception class for Sliding window.
 * </p>
 * 
 * @author Anjaly Chakkalakkal
 *
 */
public class SlidingWindowException extends Exception {

	private static final long serialVersionUID = -5845886002241247432L;

	public SlidingWindowException() {

		super();
	}

	public SlidingWindowException(String msg) {

		super(msg);
	}

	public SlidingWindowException(String message, Throwable cause) {

		super(message, cause);
	}

	public SlidingWindowException(Throwable cause) {

		super(cause);
	}
}