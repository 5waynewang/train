/**
 * 
 */
package train.distributedlock.zk;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;
import org.apache.commons.collections.CollectionUtils;
import org.apache.zookeeper.CreateMode;

import train.distributedlock.Distributedlock;

/**
 * <pre>
 *
 * </pre>
 *
 * @author Wayne.Wang<5waynewang@gmail.com>
 * @since 3:34:21 PM Aug 19, 2016
 */
public class ZkDistributedlock extends ReentrantLock implements Distributedlock {
	private static final long serialVersionUID = 7283910430512452867L;
	private static final String ROOT = "/distributedlock";

	/** 排它锁节点 **/
	final ZkClient zkClient;
	final String exclusiveLockID;
	final String exclusiveLockPath;

	CountDownLatch cdl;

	public ZkDistributedlock(String exclusiveLockID) {
		this.exclusiveLockID = exclusiveLockID;
		this.exclusiveLockPath = ROOT + "/" + exclusiveLockID;

		zkClient = new ZkClient("10.8.100.119:2181,10.8.100.120:2181,10.8.100.121:2181");
		zkClient.createPersistent(ROOT, true);
		zkClient.subscribeChildChanges(ROOT, new IZkChildListener() {
			@Override
			public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {
				if (CollectionUtils.isEmpty(currentChilds) || !currentChilds.contains(exclusiveLockPath)) {
					doNotify();
				}
			}
		});
	}

	@Override
	public void lock() {
		super.lock();
		try {
			while (!doLock()) {
				try {
					doWait();
				}
				catch (InterruptedException ignore) {
				}
			}
		}
		finally {
			super.unlock();
		}
	}

	boolean doLock() {
		final String path = zkClient.create(exclusiveLockPath, null, CreateMode.EPHEMERAL);
		// 创建失败
		if (path == null) {
			return false;
		}
		cdl = null;
		return true;
	}

	void doWait() throws InterruptedException {
		cdl = new CountDownLatch(1);
		cdl.await();
	}

	boolean doWait(long timeout, TimeUnit unit) throws InterruptedException {
		cdl = new CountDownLatch(1);
		return cdl.await(timeout, unit);
	}

	void doNotify() {
		if (cdl != null) {
			cdl.countDown();
		}
	}

	@Override
	public void lockInterruptibly() throws InterruptedException {
		super.lockInterruptibly();
		try {
			while (!doLock()) {
				doWait();
			}
		}
		finally {
			super.unlock();
		}
	}

	@Override
	public boolean tryLock() {
		if (super.tryLock()) {
			try {
				return doLock();
			}
			finally {
				super.unlock();
			}
		}
		return false;
	}

	@Override
	public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
		final long start = System.nanoTime();
		if (super.tryLock(time, unit)) {
			try {
				while (!doLock()) {
					final long cost = System.nanoTime() - start;
					final long timeout = unit.toNanos(time) - cost;
					if (timeout <= 0) {
						return false;
					}
					if (!doWait(timeout, TimeUnit.NANOSECONDS)) {
						return false;
					}
				}
				return true;
			}
			finally {
				super.unlock();
			}
		}
		return false;
	}

	@Override
	public void unlock() {
		zkClient.delete(exclusiveLockPath);
	}

	@Override
	public Condition newCondition() {
		throw new UnsupportedOperationException();
	}

}
