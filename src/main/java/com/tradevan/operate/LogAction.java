package com.tradevan.operate;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class LogAction {

	protected Logger logger;

	public LogAction() {
		String path = "";
//		path = this.getClass().getResource("/").getPath();
		path = path + "src/main/resource/log4j.properties";
		PropertyConfigurator.configure(path);
	}

	public Logger getLogger() {
		return logger;
	}

	public void setLogget(Class clazz) {
		this.logger = Logger.getLogger(clazz);

	}
}
