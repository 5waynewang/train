/**
 * 
 */
package train.transaction.jta.corejava;

import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.InvalidTransactionException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.Transaction;
import javax.transaction.TransactionManager;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.xa.DruidXADataSource;
import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.icatch.jta.UserTransactionManager;

/**
 * <pre>
 * 面向提供商的实现接口主要涉及到 TransactionManager 和 Transaction 两个对象：
 * TransactionManager 本身并不承担实际的事务处理功能，它更多的是充当用户接口和实现接口之间的桥梁。
 * 下面列出了 TransactionManager 中定义的方法，可以看到此接口中的大部分事务方法与 UserTransaction 和 Transaction 相同。 
 * 在开发人员调用 UserTransaction.begin() 方法时 TransactionManager 会创建一个 Transaction 事务对象（标志着事务的开始）并把此对象通过 ThreadLocale 关联到当前线程上；同样 UserTransaction.commit() 会调用 TransactionManager.commit()， 方法将从当前线程下取出事务对象 Transaction 并把此对象所代表的事务提交， 即调用 Transaction.commit()：
 * </pre>
 *
 * @author Wayne.Wang<5waynewang@gmail.com>
 * @since 11:01:30 AM Jul 29, 2016
 */
public class TransactionManagerImpl implements TransactionManager {
	// 此处 transactionHolder 用于将 Transaction 所代表的事务对象关联到线程上
	private static ThreadLocal<TransactionImpl> transactionHolder = new ThreadLocal<TransactionImpl>();

	//TransacationMananger 必须维护一个全局对象，因此使用单实例模式实现
	private static TransactionManagerImpl singleton = new TransactionManagerImpl();

	public static TransactionManagerImpl singleton() {
		return singleton;
	}

	@Override
	public void begin() throws NotSupportedException, SystemException {

	}

	@Override
	public void commit() throws RollbackException, HeuristicMixedException, HeuristicRollbackException,
			SecurityException, IllegalStateException, SystemException {
		// TODO Auto-generated method stub

	}

	@Override
	public int getStatus() throws SystemException {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * 返回关联到当前线程的事务
	 */
	@Override
	public Transaction getTransaction() throws SystemException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 继续当前线程关联的事务
	 */
	@Override
	public void resume(Transaction tobj) throws InvalidTransactionException, IllegalStateException, SystemException {
		// TODO Auto-generated method stub

	}

	@Override
	public void rollback() throws IllegalStateException, SecurityException, SystemException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setRollbackOnly() throws IllegalStateException, SystemException {
		// TODO Auto-generated method stub

	}

	/**
	 * 设置事务超时时间
	 */
	@Override
	public void setTransactionTimeout(int seconds) throws SystemException {
		// TODO Auto-generated method stub

	}

	/**
	 * 挂起当前线程关联的事务
	 */
	@Override
	public Transaction suspend() throws SystemException {
		// TODO Auto-generated method stub
		return null;
	}
}
