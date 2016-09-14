/**
 * 
 */
package train.transaction.jta.corejava;

import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.RollbackException;
import javax.transaction.Synchronization;
import javax.transaction.SystemException;
import javax.transaction.Transaction;
import javax.transaction.xa.XAResource;

/**
 * <pre>
 * 面向提供商的实现接口主要涉及到 TransactionManager 和 Transaction 两个对象：
 * Transaction 代表了一个物理意义上的事务，在开发人员调用 UserTransaction.begin() 方法时 TransactionManager 会创建一个 Transaction 事务对象（标志着事务的开始）并把此对象通过 ThreadLocale 关联到当前线程。
 * UserTransaction 接口中的 commit()、rollback()，getStatus() 等方法都将最终委托给 Transaction 类的对应方法执行。
 * </pre>
 *
 * @author Wayne.Wang<5waynewang@gmail.com>
 * @since 4:15:13 PM Jul 29, 2016
 */
public class TransactionImpl implements Transaction {
	/**
	 * 协调不同的事务资源共同完成事务的提交
	 */
	@Override
	public void commit() throws RollbackException, HeuristicMixedException, HeuristicRollbackException,
			SecurityException, IllegalStateException, SystemException {
		// TODO Auto-generated method stub

	}

	/**
	 * 将事务资源从当前事务中删除
	 */
	@Override
	public boolean delistResource(XAResource xaRes, int flag) throws IllegalStateException, SystemException {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * 将事务资源加入到当前的事务中（在上述示例中，在对数据库 A 操作时 其所代表的事务资源将被关联到当前事务中，同样，在对数据库 B 操作时其所代表的事务资源也将被关联到当前事务中）
	 */
	@Override
	public boolean enlistResource(XAResource xaRes) throws RollbackException, IllegalStateException, SystemException {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * 返回关联到当前线程的分布式事务的状态
	 */
	@Override
	public int getStatus() throws SystemException {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * 回调接口，Hibernate 等 ORM 工具都有自己的事务控制机制来保证事务， 但同时它们还需要一种回调机制以便在事务完成时得到通知从而触发一些处理工作，如清除缓存等。这就涉及到了
	 * Transaction 的回调接口 registerSynchronization。工具可以通过此接口将回调程序注入到事务中，当事务成功提交后，回调程序将被激活。
	 */
	@Override
	public void registerSynchronization(Synchronization sync)
			throws RollbackException, IllegalStateException, SystemException {
		// TODO Auto-generated method stub

	}

	/**
	 * 协调不同的事务资源共同完成事务的回滚
	 */
	@Override
	public void rollback() throws IllegalStateException, SystemException {
		// TODO Auto-generated method stub

	}

	/**
	 * 标识关联到当前线程的分布式事务将被回滚
	 */
	@Override
	public void setRollbackOnly() throws IllegalStateException, SystemException {
		// TODO Auto-generated method stub

	}

}
