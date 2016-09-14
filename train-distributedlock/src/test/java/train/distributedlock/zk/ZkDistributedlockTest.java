/**
 * 
 */
package train.distributedlock.zk;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * <pre>
 *
 * </pre>
 *
 * @author Wayne.Wang<5waynewang@gmail.com>
 * @since 4:41:54 PM Aug 19, 2016
 */
public class ZkDistributedlockTest {

	int size = 32;
	ZkDistributedlock[] locks;

	int count = 1000000;
	int stock = count * size;

	@Before
	public void before() {
		final String exclusiveLockID = "stockUpdater";

		locks = new ZkDistributedlock[size];
		for (int i = 0; i < locks.length; i++) {
			locks[i] = new ZkDistributedlock(exclusiveLockID);
		}
	}
	

	@Test
	public void testUpdateStock() throws InterruptedException {
		final CountDownLatch cdl = new CountDownLatch(size);
		final ScheduledExecutorService exec = Executors.newScheduledThreadPool(size);
		for (int i = 0; i < size; i++) {
			final int index = i;

			exec.submit(new Runnable() {
				@Override
				public void run() {
					try {
						for (int i = 0; i < count; i++) {
							locks[index].lock();
							try {
								--stock;
							}
							finally {
								locks[index].unlock();
							}
						}
					}
					finally {
						cdl.countDown();
					}
				}
			});
		}
		cdl.await();
		Assert.assertEquals(stock, 0);
	}
}
