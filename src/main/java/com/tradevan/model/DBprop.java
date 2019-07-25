package com.tradevan.model;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.log4j.PropertyConfigurator;

public class DBprop {

	private String host = "", port = "", database = "", username = "", password = "", driver = "", driverType = "";

	public DBprop() {
		FileInputStream fis = null;
		String path = "";
		try {
//			path = this.getClass().getResource("/").getPath();
			path = path + "src/main/resource/dbaccess.properties";
			fis = new FileInputStream(path);
			InputStreamReader isr = new InputStreamReader(fis);
			BufferedReader br = new BufferedReader(isr);

			String read_line;

			try {
				while ((read_line = br.readLine()) != null) {
					if (read_line.indexOf("#") == 0) {
						continue;
					}
					if (read_line.toLowerCase().indexOf("host:") >= 0) {
						this.host = read_line.substring(read_line.indexOf(":") + 1).toLowerCase().trim();
					}
					if (read_line.toLowerCase().indexOf("port:") >= 0) {
						this.port = read_line.substring(read_line.indexOf(":") + 1).toLowerCase().trim();
					}
					if (read_line.toLowerCase().indexOf("database:") >= 0) {
						this.database = read_line.substring(read_line.indexOf(":") + 1).trim();
					}
					if (read_line.toLowerCase().indexOf("username:") >= 0) {
						this.username = read_line.substring(read_line.indexOf(":") + 1).toLowerCase().trim();
					}
					if (read_line.indexOf("password:") >= 0) {
						this.password = read_line.substring(read_line.indexOf(":") + 1).trim();
					}
					if (read_line.toLowerCase().indexOf("driver:") >= 0) {
						this.driver = read_line.substring(read_line.indexOf(":") + 1).trim();
					}
					if (read_line.toLowerCase().indexOf("drivertype:") >= 0) {
						this.driverType = read_line.substring(read_line.indexOf(":") + 1).toLowerCase().trim();
					}

				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				fis.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public String getHost() {
		return host;
	}

	public String getPort() {
		return port;
	}

	public String getDatabase() {
		return database;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getDriver() {
		return driver;
	}

	public String getDriverType() {
		return driverType;
	}

}
