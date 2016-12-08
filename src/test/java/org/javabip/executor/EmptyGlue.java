/*
 * Copyright 2012-2016 École polytechnique fédérale de Lausanne (EPFL), Switzerland
 * Copyright 2012-2016 Crossing-Tech SA, Switzerland
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Author: Simon Bliudze, Anastasia Mavridou, Radoslaw Szymanek and Alina Zolotukhina
 */
package org.javabip.executor;

import org.javabip.api.Accept;
import org.javabip.api.BIPGlue;
import org.javabip.api.DataWire;
import org.javabip.api.Require;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents an empty glue without any ports.
 * 
 */
public class EmptyGlue implements BIPGlue {
	@Override
	public List<Accept> getAcceptConstraints() {
		return new ArrayList<Accept>();
	}

	@Override
	public List<Require> getRequiresConstraints() {
		return new ArrayList<Require>();
	}

	@Override
	public List<DataWire> getDataWires() {
		return new ArrayList<DataWire>();
	}

	@Override
	public void toXML(OutputStream outputStream) {
	}

}
