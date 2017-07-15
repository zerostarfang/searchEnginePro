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

import com.zerofang.pagerank.interfaces.IWordDAO;
import com.zerofang.pagerank.entity.Word;
import com.zerofang.pagerank.util.JDBCUtil;
import com.zerofang.pagerank.util.Utilities;

public class WordDAOImpl implements IWordDAO {

	private static String TABLE_NAME = "word";

	private static PreparedStatement pstadd = null;
	private static PreparedStatement pstupdate = null;
	private static PreparedStatement pstdel = null;

	private void setPS(PreparedStatement ps, Word t) throws SQLException {
		ps.setInt(1, t.getUrlID());
		// ps.setInt(2, t.getTimesInUrl());
		// ps.setInt(3, t.getPosition());
		ps.setString(2, t.getValue());
	}

	@Override
	public boolean add(Word t) {
		Connection conn = null;
		boolean done = false;

		try {
			if (null == pstadd) {
				conn = JDBCUtil.getConnection();
				pstadd = conn.prepareStatement("insert into " + TABLE_NAME
						+ "(url_id,word_value) " + "values(?, ?)",
						Statement.RETURN_GENERATED_KEYS);
			}

			setPS(pstadd, t);

			done = pstadd.executeUpdate() != 0;

			if (done) {
				ResultSet rsk = pstadd.getGeneratedKeys();
				if (rsk.next()) {
					t.setID(rsk.getInt(1));
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			JDBCUtil.closes(null, pstadd, conn);
			pstadd = null;
		}

		return done;
	}

	@Override
	public boolean update(Word t) {
		Connection conn = null;
		boolean done = false;

		try {
			if (null == pstupdate) {
				conn = JDBCUtil.getConnection();
				pstupdate = conn.prepareStatement("update " + TABLE_NAME
						+ " set url_id=?,word_value=?" + "where word_id=?");
			}

			setPS(pstupdate, t);
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

	@Override
	public boolean delete(Word t) {
		Connection conn = null;
		boolean done = false;

		try {
			if (null == pstdel) {
				conn = JDBCUtil.getConnection();
				pstdel = conn.prepareStatement("delete from " + TABLE_NAME
						+ " where word_id=? " + "limit 1");
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
	public List<Word> select(String key) {
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		// HashSet<Scene> result = new HashSet<Scene>();
		List<Word> result = new ArrayList<>();
		StringBuilder sb = new StringBuilder();
		sb.append("select * from " + TABLE_NAME + " ");
		// append where statement
		sb.append(Utilities.generateWhereStatement(key));

		try {
			conn = JDBCUtil.getConnection();
			pst = conn.prepareStatement(sb.toString());

			rs = pst.executeQuery();

			while (rs.next()) {
				Word word = new Word();

				word.setID(rs.getInt("word_id"));
				word.setUrlID(rs.getInt("url_id"));
				// word.setTimesInUrl(rs.getInt("times_in_url"));
				// word.setPosition(rs.getInt("position_in_url"));
				word.setValue(rs.getString("word_value"));
				result.add(word);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			JDBCUtil.closes(rs, pst, conn);
		}

		return result;
	}

}
