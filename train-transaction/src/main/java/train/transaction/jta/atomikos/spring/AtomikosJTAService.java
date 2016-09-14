/**
 * 
 */
package train.transaction.jta.atomikos.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <pre>
 *
 * </pre>
 *
 * @author Wayne.Wang<5waynewang@gmail.com>
 * @since 2:27:02 PM Jul 29, 2016
 */
@Service
@Transactional(rollbackFor = { Exception.class })
public class AtomikosJTAService {
	@Autowired
	@Qualifier("jdbcTemplate1")
	JdbcTemplate jdbcTemplate1;
	@Autowired
	@Qualifier("jdbcTemplate2")
	JdbcTemplate jdbcTemplate2;

	public void testJTA_commit() throws Exception {
		jdbcTemplate1.execute("UPDATE test_jta SET value=value+50 WHERE id=1");
		jdbcTemplate2.execute("UPDATE test_jta SET value=value-50 WHERE id=2");
	}

	public void testJTA_rollback() throws Exception {
		jdbcTemplate1.execute("UPDATE test_jta SET value=value+50 WHERE id=1");
		if (true) {
			throw new Exception();
		}
		jdbcTemplate2.execute("UPDATE test_jta SET value=value-50 WHERE id=2");
	}
}
