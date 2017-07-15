package com.zerofang.pagerank.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.zerofang.pagerank.entity.Url;
import com.zerofang.pagerank.entity.Word;
import com.zerofang.pagerank.interfaces.IUrlDAO;
import com.zerofang.pagerank.interfaces.IWordDAO;
import com.zerofang.pagerank.util.JDBCUtil;
import com.zerofang.pagerank.util.Utilities;

public class UrlDAOImpl implements IUrlDAO {

	private static String TABLE_NAME = "url";

	private static PreparedStatement pstadd = null;
	private static PreparedStatement pstupdate = null;
	private static PreparedStatement pstdel = null;

	private void setPS(PreparedStatement ps, Url t) throws SQLException {
		ps.setInt(1, t.getID());
		ps.setString(2, t.getUrlName());
		ps.setDouble(3, t.getPagerankValue());
		ps.setString(4, t.getTitle());
		ps.setString(5, t.getText());
		ps.setString(6, t.getAuthor());
		ps.setTimestamp(7, new java.sql.Timestamp(t.getDate().getTime()));
	}

	@Override
	public boolean add(Url t) {
		Connection conn = null;
		boolean done = false;

		try {
			if (null == pstadd) {
				conn = JDBCUtil.getConnection();
				pstadd = conn
						.prepareStatement("insert into " + TABLE_NAME
								+ "(url_id,url_name,pagerank_value) "
								+ "values(?,?,?)");
			}

			// setPS(pstadd, t);
			pstadd.setInt(1, t.getID());
			pstadd.setString(2, t.getUrlName());
			pstadd.setDouble(3, t.getPagerankValue());
			done = pstadd.executeUpdate() != 0;
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			JDBCUtil.closes(null, pstadd, conn);
			pstadd = null;
		}

		return done;
	}

	@Override
	public boolean update(Url t) {
		Connection conn = null;
		boolean done = false;

		try {
			if (null == pstupdate) {
				conn = JDBCUtil.getConnection();
				pstupdate = conn.prepareStatement("update " + TABLE_NAME
						+ " set url_id=?, url_name=?, pagerank_value=?"
						+ "where url_id=?");
			}

			setPS(pstupdate, t);
			pstupdate.setInt(4, t.getID());

			done = pstupdate.executeUpdate() != 0;
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			JDBCUtil.closes(null, pstupdate, conn);
			pstupdate = null;
		}

		return done;
	}

	public boolean updateContent(Url t) {
		Connection conn = null;
		boolean done = false;

		try {
			if (null == pstupdate) {
				conn = JDBCUtil.getConnection();
				pstupdate = conn.prepareStatement("update " + TABLE_NAME
						+ " set title=?, text=? ,author=? ,date=? "
						+ "where url_id=?");
			}

			// setPS(pstupdate, t);
			pstupdate.setString(1, t.getTitle());
			pstupdate.setString(2, t.getText());
			pstupdate.setString(3, t.getAuthor());
			pstupdate.setTimestamp(4, new java.sql.Timestamp(t.getDate()
					.getTime()));
			pstupdate.setInt(5, t.getID());

			done = pstupdate.executeUpdate() != 0;
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			JDBCUtil.closes(null, pstupdate, conn);
			pstupdate = null;
		}

		return done;
	}

	public boolean updateCategory(Url t) {
		Connection conn = null;
		boolean done = false;

		try {
			if (null == pstupdate) {
				conn = JDBCUtil.getConnection();
				pstupdate = conn.prepareStatement("update " + TABLE_NAME
						+ " set cat_id=? " + "where url_id=?");
			}

			// setPS(pstupdate, t);
			pstupdate.setInt(1, t.getCategory());
			pstupdate.setInt(2, t.getID());

			done = pstupdate.executeUpdate() != 0;
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			JDBCUtil.closes(null, pstupdate, conn);
			pstupdate = null;
		}

		return done;
	}

	public boolean updatePagerankValue(Url t) {
		Connection conn = null;
		boolean done = false;

		try {
			if (null == pstupdate) {
				conn = JDBCUtil.getConnection();
				pstupdate = conn.prepareStatement("update " + TABLE_NAME
						+ " set pagerank_value=?" + "where url_id=?");
			}

			// setPS(pstupdate, t);
			pstupdate.setDouble(1, t.getPagerankValue());
			pstupdate.setInt(2, t.getID());

			done = pstupdate.executeUpdate() != 0;
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			JDBCUtil.closes(null, pstupdate, conn);
			pstupdate = null;
		}

		return done;
	}

	@Override
	public boolean delete(Url t) {
		Connection conn = null;
		boolean done = false;

		try {
			if (null == pstdel) {
				conn = JDBCUtil.getConnection();
				pstdel = conn.prepareStatement("delete from " + TABLE_NAME
						+ " where url_id=? " + "limit 1");
			}

			pstdel.setInt(1, t.getID());

			done = pstdel.executeUpdate() != 0;
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			JDBCUtil.closes(null, pstdel, conn);
			pstdel = null;
		}

		return done;
	}

	@Override
	public List<Url> select(String key) {
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		// HashSet<Scene> result = new HashSet<Scene>();
		List<Url> result = new ArrayList<>();
		StringBuilder sb = new StringBuilder();
		sb.append("select * from " + TABLE_NAME + " ");
		// append where statement
		sb.append(Utilities.generateWhereStatement(key));

		try {
			conn = JDBCUtil.getConnection();
			pst = conn.prepareStatement(sb.toString());

			rs = pst.executeQuery();

			while (rs.next()) {
				Url url = new Url();

				url.setID(rs.getInt("url_id"));
				url.setUrlName(rs.getString("url_name"));
				url.setPagerankValue(rs.getDouble("pagerank_value"));

				result.add(url);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			JDBCUtil.closes(rs, pst, conn);
		}

		return result;
	}

}
