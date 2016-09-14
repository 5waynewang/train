/**
 * 
 */
package train.distributedlock.redis;

import org.junit.Before;
import org.junit.Test;
import org.redisson.Config;
import org.redisson.Redisson;
import org.redisson.core.RLock;

/**
 * <pre>
 *
 * </pre>
 *
 * @author Wayne.Wang<5waynewang@gmail.com>
 * @since 11:21:58 AM Aug 28, 2016
 */
public class RedisDistributedlockTest {

	Redisson redisson;

	@Before
	public void before() {
		Config config = new Config();
		config.useClusterServers()//
				.addNodeAddress("10.8.100.180:7000")//
				.addNodeAddress("10.8.100.180:7001")//
				.addNodeAddress("10.8.100.180:7002")//
				.setScanInterval(2000);

		redisson = (Redisson) Redisson.create(config);
	}

	@Test
	public void testLock() {
		RLock lock = redisson.getLock("test_redisson_lock");
		lock.lock();
		try {
			//TODO 
		}
		finally {
			lock.unlock();
		}
	}
}
