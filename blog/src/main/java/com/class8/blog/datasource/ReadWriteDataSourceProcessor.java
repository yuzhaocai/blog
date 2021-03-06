package com.class8.blog.datasource;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.interceptor.NameMatchTransactionAttributeSource;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionAttribute;
import org.springframework.util.PatternMatchUtils;
import org.springframework.util.ReflectionUtils;
import com.class8.blog.exception.DynamicDataSourceException;

/**
 * 读写数据源处理器
 * @author Administrator
 *
 */
public class ReadWriteDataSourceProcessor implements BeanPostProcessor {
	
	private static final Logger logger = LoggerFactory.getLogger(ReadWriteDataSourceProcessor.class);
	
	//当写后读取数据时是否强制选择读库，默认为false
	private boolean forceChoiceReadWhenWrite = false;
	
	private List<String> readMethodPatters = new ArrayList<String>();
	
	public void setForceChoiceReadWhenWrite(boolean forceChoiceReadWhenWrite) {
		this.forceChoiceReadWhenWrite = forceChoiceReadWhenWrite;
	}

	@SuppressWarnings("unchecked")
	public Object postProcessAfterInitialization(Object bean, String name)
			throws BeansException {
		if(!(bean instanceof NameMatchTransactionAttributeSource)) {
            return bean;
        }
		try {
			NameMatchTransactionAttributeSource transactionAttributeSource = (NameMatchTransactionAttributeSource)bean;
			Field nameMapField = ReflectionUtils.findField(NameMatchTransactionAttributeSource.class, "nameMap");
			nameMapField.setAccessible(true);
			Map<String, TransactionAttribute> nameMap = (Map<String, TransactionAttribute>) nameMapField.get(transactionAttributeSource);
			for(Entry<String, TransactionAttribute> entry : nameMap.entrySet()) {
			        RuleBasedTransactionAttribute ruleBasedTransactionAttribute = (RuleBasedTransactionAttribute)entry.getValue();
			//仅对read-only的处理
			if(!ruleBasedTransactionAttribute.isReadOnly()) {
			    continue;
			}
			String methodPattern = entry.getKey();
			if(forceChoiceReadWhenWrite) {
				//不管之前操作是写，默认强制从读库读 （设置为NOT_SUPPORTED即可,NOT_SUPPORTED会挂起之前的事务）
				ruleBasedTransactionAttribute.setPropagationBehavior(Propagation.NOT_SUPPORTED.value());
			} else {
			    //否则 设置为SUPPORTS（这样可以参与到写事务）
				ruleBasedTransactionAttribute.setPropagationBehavior(Propagation.SUPPORTS.value());
			}
			readMethodPatters.add(methodPattern);
			}
		} catch (Exception e) {
			throw new DynamicDataSourceException("process read/write transaction error",e);
		}
		return null;
	}

	public Object postProcessBeforeInitialization(Object bean, String name)
			throws BeansException {
		return bean;
	}
	
	/**
	 * 读写数据源AOP增强方法
	 * TODO:直接读方法选择读，写方法选择写，不用理会上次是都还是写？
	 * @param proceedingJoinPoint
	 * @return
	 * @throws Throwable
	 */
	public Object determineDataSourceType(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
		String methodName = proceedingJoinPoint.getSignature().getName();
		for (String methodPattern : readMethodPatters) {
			if(isMatch(methodName,methodPattern)){
				if(forceChoiceReadWhenWrite){
					//如果写后强制选择读库，则选择读库
					ReadWriteDataSourceDecision.setType(ReadWriteType.READ);
				} else {
					ReadWriteType type = ReadWriteDataSourceDecision.getType();
					if(ReadWriteType.WRITE.equals(type)){
						//如果上一次是写，且没有强制写后选择读库，则选择写库
						ReadWriteDataSourceDecision.setType(ReadWriteType.WRITE);
					} else {
						ReadWriteDataSourceDecision.setType(ReadWriteType.READ);
					}
				}
				break;
			}
		}
		//默认选择写库
		ReadWriteDataSourceDecision.setType(ReadWriteType.WRITE);
		try {
			return proceedingJoinPoint.proceed();
		} finally {
			ReadWriteDataSourceDecision.clear();
		}
	}
	
	protected boolean isMatch(String methodName, String methodPattern) {
        return PatternMatchUtils.simpleMatch(methodPattern, methodName);
    }

}
