package com.tradevan.action;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.sun.xml.bind.v2.model.core.Element;
import com.tradevan.operate.AccessDatabase;
import com.tradevan.operate.LogAction;

public class FoodETL extends LogAction {
	LogAction logAction = new LogAction();
	private AccessDatabase _access = new AccessDatabase();
	SimpleDateFormat sdFormatTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public void run() {
		logAction.setLogget(FoodETL.class);
		logAction.getLogger().info("Start ETL");
		Set<List<String>> item = getItem();
		compareText(item);
		// TODO Auto-generated method stub

	}

	private void compareText(Set<List<String>> item) {
		// TODO Auto-generated method stub
		List<Integer> indexs = new ArrayList<Integer>();

		String getItemSQL = String
				.format("SELECT distinct [編號],[樣品名稱] FROM [ChemiBigData].[dbo].[公用_食品成分資料]");
		indexs.add(1);
		indexs.add(2);
		Set<List<String>> result = read4Table(getItemSQL, indexs);
		for (List<String> eachKeyword : item) {
			Set<List<String>> compareData = null;
			int check;
			String casno = eachKeyword.get(0);
			String kw = eachKeyword.get(1);
			logAction.getLogger().info(kw);
			for (List<String> results : result) {
				List<String> eachCompareData = null;
				String id = "", description = "";
				id = results.get(0);
				description = results.get(1);
				check = description.indexOf(kw);

				// logAction.getLogger().info(kw + " VS Each Data :" + results + " = " + check);
				if (check == 0) {
					compareData = new HashSet<List<String>>();
					eachCompareData = new ArrayList<String>();
					eachCompareData.add(casno);
					eachCompareData.add(id);
					eachCompareData.add(kw);
					compareData.add(eachCompareData);
					insertInto(compareData);
					logAction.getLogger().info(kw + " VS Each Data :" + results + " = " + check);
				}

			}
		}

	}

	private void insertInto(Set<List<String>> compareData) {
		String id, kw, casNo;
		Set<List<String>> result = null;
		List<String> l = null;
		String insertSql = String.format("INSERT INTO [廠商關聯與流向_食品proc1]( [CASNo],[食品_ROW], [關鍵字]) VALUES (?,?,?); ");

		for (List<String> eachData : compareData) {
			result = new HashSet<List<String>>();
			l = new ArrayList<String>();
			casNo = eachData.get(0);
			id = eachData.get(1);
			kw = eachData.get(2);
			l.add(casNo);
			l.add(id);
			l.add(kw);
			result.add(l);
		}
		write2Table(insertSql, result);

	}

	private Set<List<String>> getItem() {
		// TODO Auto-generated method stub
		Set<List<String>> result = null;
		List<Integer> indexs = new ArrayList<Integer>();
		String getItemSQL = String
				.format("SELECT [CASNo], [aliases_pro] FROM [ChemiBigData].[dbo].[公用_化學物質學名別名對照表(中文對照Casno用)]");
		indexs.add(1);
		indexs.add(2);
		result = read4Table(getItemSQL, indexs);
		return result;
	}

	public Set<List<String>> read4Table(String searchSQL, List<Integer> indexs) {
		// TODO Auto-generated method stub
		Set<List<String>> response = null, setData = new HashSet<List<String>>();
		List<String> setColumn = null;
		logAction.getLogger().info("Connect to DB");
		response = this._access.selectFromTable(searchSQL, indexs);
		for (List<String> allRow : response) {
			int i = 1;
			setColumn = new ArrayList<String>();
			for (String eachData : allRow) {
				// logAction.getLogger().info("Each Data " + eachData);
				if (i == indexs.size()) {
					i = 1;
					setColumn.add(eachData);
					setData.add(setColumn);
					setColumn = new ArrayList<String>();
				} else {
					setColumn.add(eachData);
					i++;
				}
			}
		}
		return setData;
	}

	public void write2Table(String insertSQL, Set<List<String>> setData) {
		// TODO Auto-generated method stub
		_access.write2Table(insertSQL, setData);
	}
}
