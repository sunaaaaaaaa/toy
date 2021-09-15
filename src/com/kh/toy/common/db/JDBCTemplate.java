package com.kh.toy.common.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import oracle.ucp.jdbc.PoolDataSource;
import oracle.ucp.jdbc.PoolDataSourceFactory;

public class JDBCTemplate {
	
	//Singleton 패턴
	//해당 클래스의 인스턴스가 하나만 생성되어야할때 사용하는 디자인패턴
	private static JDBCTemplate instance;
	private static PoolDataSource pds;
	
	//생성자를 private으로 처리해, 외부에서 JDBCTemplate을 생성하는것을 차단
	private JDBCTemplate() { 
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			final String DB_URL="jdbc:oracle:thin:@yoonzamdb_high?TNS_ADMIN=C:/CODE/Wallet_YOONZAMDB";
			  // Use the TNS Alias name along with the TNS_ADMIN - For ATP and ADW
			  // final static String DB_URL="jdbc:oracle:thin:@dbname_alias?TNS_ADMIN=/Users/test/wallet_dbname";
			  final String DB_USER = "ADMIN";
			  final String DB_PASSWORD = "Yoonzam1234!!";
			  final String CONN_FACTORY_CLASS_NAME="oracle.jdbc.pool.OracleDataSource";
			    
				PoolDataSource pds = PoolDataSourceFactory.getPoolDataSource();
				pds.setConnectionFactoryClassName(CONN_FACTORY_CLASS_NAME);
				pds.setURL(DB_URL);
				pds.setUser(DB_USER);
				pds.setPassword(DB_PASSWORD);
				pds.setConnectionPoolName("JDBC_UCP_POOL");

				// Default is 0. Set the initial number of connections to be created
				// when UCP is started.
				pds.setInitialPoolSize(5);

				// Default is 0. Set the minimum number of connections
				// that is maintained by UCP at runtime.
				pds.setMinPoolSize(5);

				// Default is Integer.MAX_VALUE (2147483647). Set the maximum number of
				// connections allowed on the connection pool.
				pds.setMaxPoolSize(20);

				// Default is 30secs. Set the frequency in seconds to enforce the timeout
				// properties. Applies to inactiveConnectionTimeout(int secs),
				// AbandonedConnectionTimeout(secs)& TimeToLiveConnectionTimeout(int secs).
				// Range of valid values is 0 to Integer.MAX_VALUE. .
				pds.setTimeoutCheckInterval(5);

				// Default is 0. Set the maximum time, in seconds, that a
				// connection remains available in the connection pool.
				pds.setInactiveConnectionTimeout(10);
			
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//외부에서 JDBCTemplate의 인스턴스를 생성하지않고도 
	//JDBCTemplate의 인스턴스 반환받는 용도 메소드
	public static JDBCTemplate getInstance() {
		//instance변수가 한번도 생성된적이 없으면,
		if(instance ==null) {
			instance = new JDBCTemplate();
		}
		return instance;
	}
	
	
	
	
	
	public Connection getConnection() {
		
		Connection conn = null;
		    
		try {

			conn =  pds.getConnection();
			
			conn.setAutoCommit(false);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return conn;
	}
	
	
	//3. commit/ rollback 
	public void commit(Connection conn) {
		try {
			conn.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void rollback(Connection conn) {
		try {
			conn.rollback();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	public void close(Connection conn) {
		try {
			if(conn!=null && !conn.isClosed()) {
			conn.close();
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void close(Statement stmt) {
		
		try {
			if(stmt!=null && !stmt.isClosed()) {
			stmt.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public void close(ResultSet rset) {
		
			try {
				if(rset!=null && !rset.isClosed()) {
				rset.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	public void close(Connection conn,Statement stmt) {
		close(conn);
		close(stmt);

	}

	public void close(Statement stmt,ResultSet rset) {
		close(stmt);
		close(rset);
	}

	public void close(Connection conn,Statement stmt,ResultSet rset) {
		close(conn);
		close(stmt);
		close(rset);
	}

	
	
	}
	
	
