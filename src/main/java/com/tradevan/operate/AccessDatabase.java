package com.tradevan.operate;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringEscapeUtils;

import com.tradevan.model.DBprop;

public class AccessDatabase extends DBprop {

	private Connection _con = null;

	public AccessDatabase() {
		try {
			Class.forName(super.getDriver());
			// postgres
			// this._con = DriverManager.getConnection(String.format("jdbc:%s://%s:%s/%s",
			// super.getDriverType(),
			// super.getHost(), super.getPort(), super.getDatabase()), super.getUsername(),
			// super.getPassword());
			// ms sql
			// this._con = DriverManager.getConnection(
			// String.format("jdbc:%s://%s:%s;DatabaseName=%s;", super.getDriverType(),
			// super.getHost(),
			// super.getPort(), super.getDatabase().toUpperCase()),
			// super.getUsername(), super.getPassword());

			// this._con = DriverManager.getConnection(String.format("jdbc:%s://%s:%s/%s",
			// super.getDriverType(),
			// super.getHost(), super.getPort(), super.getDatabase()), super.getUsername(),
			// super.getPassword());

			this._con = DriverManager.getConnection(
					String.format("jdbc:%s://%s:%s;DatabaseName=%s;", super.getDriverType(), super.getHost(),
							super.getPort(), super.getDatabase().toUpperCase()),
					super.getUsername(), super.getPassword());

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// insert data into specific table
	public void write2Table(String insertSQL, Set<List<String>> setData) {
		PreparedStatement ps = null;
		int countBatch = 0;
		System.out.println(StringEscapeUtils.unescapeJava(insertSQL));
		try {
			ps = this._con.prepareStatement(StringEscapeUtils.unescapeJava(insertSQL));
			int i = 1;
			for (List<String> datas : setData) {
				countBatch++;
				i = 1;
				// System.out.println(datas);
				// for (int j = 0; i < datas.size(); i++) {
				// if (j == 0) {
				// ps.setInt(j+1, Integer.parseInt(datas.get(j)));
				// } else {
				// ps.setString(j+2, datas.get(j));
				// }
				// }
				for (String s : datas) {
					// if (i == 1) {
					// try {
					// ps.setInt(i++, Integer.valueOf(s));
					// } catch (Exception e) {
					// i = 1;
					// ps.setString(i++, s);
					// }
					// } else {
					// ps.setString(i++, s);
					// }
					ps.setString(i++, s);
				}
				ps.addBatch();
				if (countBatch % 100 == 0) {
					ps.executeBatch();
					ps.clearBatch();
				}
			}
			ps.executeBatch();
			ps.clearBatch();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				ps.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block

				e.printStackTrace();
			}
		}

	}

	public Set<List<String>> selectFromTable(String selectSQL, List<Integer> indexs) {
		// TODO Auto-generated method stub
		PreparedStatement ps = null;
		Set<List<String>> datas = new HashSet<List<String>>();
		List<String> l = new ArrayList<String>();
		// System.out.println(StringEscapeUtils.unescapeJava(selectSQL));
		try {
			ps = this._con.prepareStatement(StringEscapeUtils.unescapeJava(selectSQL));
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				for (int i : indexs) {
					// System.out.println(rs.getString(i));
					if (rs.getString(i) == null) {
						l.add("");

					} else {
						l.add(rs.getString(i).toString());
					}

				}
			}
			datas.add(l);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				ps.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return datas;
	}

	public void closeConnection() {
		try {
			this._con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Set<List<String>> selectFromTableWithParameter(String selectSQL, List<String> setData,
			List<Integer> indexs) {
		// TODO Auto-generated method stub
		PreparedStatement ps = null;
		Set<List<String>> datas = new HashSet<List<String>>();
		List<String> l = null;
		// List<String> l = new ArrayList<String>();
		try {
			// Set parameters into selectSQL
			ps = this._con.prepareStatement(StringEscapeUtils.unescapeJava(selectSQL));
			System.out.println(StringEscapeUtils.unescapeJava(selectSQL));
			int i = 1;
			for (String s : setData) {
				ps.setString(i++, s);
			}
			// Execute the SQL and get response.
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				l = new ArrayList<String>();
				for (int index : indexs) {
					// System.out.println(rs.getString(i));
					if (rs.getString(index) == null) {
						l.add("");

					} else {
						l.add(rs.getString(index).toString());
					}

				}
				datas.add(l);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				ps.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return datas;
	}

}
