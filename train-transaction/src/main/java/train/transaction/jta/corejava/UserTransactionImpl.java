/**
 * 
 */
package train.transaction.jta.corejava;

import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

/**
 * <pre>
 * 面向开发人员的接口为 UserTransaction，开发人员通常只使用此接口实现 JTA 事务管理
 * </pre>
 *
 * @author Wayne.Wang<5waynewang@gmail.com>
 * @since 12:28:57 PM Jul 29, 2016
 */
public class UserTransactionImpl implements UserTransaction {
	/**
	 * 开始一个分布式事务，（在后台 TransactionManager 会创建一个 Transaction 事务对象并把此对象通过 ThreadLocale 关联到当前线程上 )
	 */
	@Override
	public void begin() throws NotSupportedException, SystemException {
		TransactionManagerImpl.singleton().begin();
	}

	/**
	 * 提交事务（在后台 TransactionManager 会从当前线程下取出事务对象并把此对象所代表的事务提交）
	 */
	@Override
	public void commit() throws RollbackException, HeuristicMixedException, HeuristicRollbackException,
			SecurityException, IllegalStateException, SystemException {
		// TODO Auto-generated method stub

	}

	/**
	 * 回滚事务（在后台 TransactionManager 会从当前线程下取出事务对象并把此对象所代表的事务回滚）
	 */
	@Override
	public void rollback() throws IllegalStateException, SecurityException, SystemException {
		// TODO Auto-generated method stub

	}

	/**
	 * 标识关联到当前线程的分布式事务将被回滚
	 */
	@Override
	public void setRollbackOnly() throws IllegalStateException, SystemException {
		// TODO Auto-generated method stub

	}

	/**
	 * 返回关联到当前线程的分布式事务的状态 (Status 对象里边定义了所有的事务状态，感兴趣的读者可以参考 API 文档 )
	 */
	@Override
	public int getStatus() throws SystemException {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * 设置事务超时时间
	 */
	@Override
	public void setTransactionTimeout(int seconds) throws SystemException {
		// TODO Auto-generated method stub

	}

}
