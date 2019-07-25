package com.tradevan.run;

import com.tradevan.action.EtaxETL;
import com.tradevan.action.FoodETL;
import com.tradevan.operate.LogAction;

public class RunTaxETL {

	/**
	 * @param args
	 */

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		LogAction logAction = new LogAction();
		logAction.setLogget(RunTaxETL.class);
		// that five web stand alone
		String str = args[0];
		switch (str) {
		case "1":
			EtaxETL etl = new EtaxETL();
			etl.run();
			break;
		case "2":
			FoodETL fetl = new FoodETL();
			fetl.run();
			break;
		default:
			break;
		}
		
	}

}
