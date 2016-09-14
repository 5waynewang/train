/**
 * 
 */
package train.transaction.jta.atomikos.corejava;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import com.alibaba.druid.pool.xa.DruidXADataSource;
import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.icatch.jta.UserTransactionManager;
import com.atomikos.jdbc.AtomikosDataSourceBean;

/**
 * <pre>
 *
 * </pre>
 *
 * @author Wayne.Wang<5waynewang@gmail.com>
 * @since 11:53:36 AM Jul 29, 2016
 */
public class AtomikosJTATest {

	AtomikosDataSourceBean xaDataSource1;
	AtomikosDataSourceBean xaDataSource2;

	UserTransactionManager transactionManager;

	UserTransactionImp userTransaction;

	JdbcTemplate jdbcTemplate1;
	JdbcTemplate jdbcTemplate2;

	@Before
	public void before() throws Exception {
		xaDataSource1 = createXQDataSource("jta1");
		xaDataSource1.init();

		xaDataSource2 = createXQDataSource("jta2");
		xaDataSource2.init();

		transactionManager = new UserTransactionManager();
		transactionManager.setForceShutdown(true);
		transactionManager.init();

		userTransaction = new UserTransactionImp();
		userTransaction.setTransactionTimeout(300);

		jdbcTemplate1 = new JdbcTemplate(xaDataSource1);
		jdbcTemplate2 = new JdbcTemplate(xaDataSource2);
	}

	@After
	public void after() {
		if (xaDataSource1 != null) {
			xaDataSource1.close();
		}
		if (xaDataSource2 != null) {
			xaDataSource2.close();
		}
		if (transactionManager != null) {
			transactionManager.close();
		}
	}

	public AtomikosDataSourceBean createXQDataSource(String db) {
		DruidXADataSource xaDataSource = new DruidXADataSource();
		xaDataSource.setUrl("jdbc:mysql://localhost:3306/" + db);
		xaDataSource.setUsername("root");
		xaDataSource.setPassword("ilovebaby");

		AtomikosDataSourceBean atomikosDataSource = new AtomikosDataSourceBean();
		atomikosDataSource.setUniqueResourceName(db);
		atomikosDataSource.setXaDataSource(xaDataSource);

		return atomikosDataSource;
	}

	@Test
	public void testJTA_commit() throws Exception {
		userTransaction.begin();
		try {
			jdbcTemplate1.execute("UPDATE test_jta SET value=value+50 WHERE id=1");
			jdbcTemplate2.execute("UPDATE test_jta SET value=value-50 WHERE id=2");
			userTransaction.commit();
		}
		catch (Exception e) {
			userTransaction.rollback();
			e.printStackTrace();
		}
	}
	
	@Test
	public void testJTA_rollback() throws Exception {
		userTransaction.begin();
		try {
			jdbcTemplate1.execute("UPDATE test_jta SET value=value+50 WHERE id=1");
			if (true) {
				throw new Exception();
			}
			jdbcTemplate2.execute("UPDATE test_jta SET value=value-50 WHERE id=2");
			userTransaction.commit();
		}
		catch (Exception e) {
			userTransaction.rollback();
			e.printStackTrace();
		}
	}
}
