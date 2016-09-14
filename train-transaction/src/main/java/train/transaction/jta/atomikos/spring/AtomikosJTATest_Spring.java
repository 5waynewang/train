/**
 * 
 */
package train.transaction.jta.atomikos.spring;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * <pre>
 *
 * </pre>
 *
 * @author Wayne.Wang<5waynewang@gmail.com>
 * @since 2:22:37 PM Jul 29, 2016
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("dev")
@ContextConfiguration(value = { "classpath:/applicationContext-atomikos.xml" })
//@org.junit.Ignore
public class AtomikosJTATest_Spring {
	@Autowired
	AtomikosJTAService atomikosJTAService;

	@Test
	public void testJTA_commit() throws Exception {
		atomikosJTAService.testJTA_commit();
	}

	@Test(expected = Exception.class)
	public void testJTA_rollback() throws Exception {
		atomikosJTAService.testJTA_rollback();
	}
}
