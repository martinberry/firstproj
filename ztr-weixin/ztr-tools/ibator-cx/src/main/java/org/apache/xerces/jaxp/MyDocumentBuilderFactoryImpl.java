package org.apache.xerces.jaxp;

import com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl;

public class MyDocumentBuilderFactoryImpl extends DocumentBuilderFactoryImpl {

	private boolean isXIncludeAware;

	@Override
	public void setXIncludeAware(boolean state) {
		this.isXIncludeAware = state;
	}

	@Override
	public boolean isXIncludeAware() {
		return this.isXIncludeAware;
	}
}
