package com.xz.springboot;

import java.util.Arrays;
import java.util.Map;
import java.util.Properties;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.Serializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Kafka Producer<String, String>、Consumer
 * <p>KafkaUtil description</p>
 * <p></p>
 * @author xchao
 * @E-mail xiongchao@tusvn.com
 * @date 2018-05-19
 * @since 2018-05-19
 */
public class KafkaUtil {

	private static ThreadLocal<Producer<String, String>> threadLocal = new ThreadLocal<>();
	
	protected final static Logger LOGGER = LoggerFactory.getLogger(KafkaUtil.class);
	
	private static Properties props;
	private static final String BOOTSTRAP_SERVERS = PropertiesUtil.getValueByKey("kafka.properties", "kafka.url");
	private static final String REPT_VEHICLEBASICDATA = "rept_VehicleBasicData";
	private final static String SEPARATOR = "\001";

	private KafkaUtil() {
	}

	/**
	 * 生产者，注意kafka生产者不能够从代码上生成主题，只有在服务器上用命令生成
	 */
	static {
		props = new Properties();
		props.put("bootstrap.servers", BOOTSTRAP_SERVERS);// 服务器ip:端口号，集群用逗号分隔
		props.put("acks", "all");// “所有”设置将导致记录的完整提交阻塞，最慢的，但最持久的设置。
		props.put("retries", 0); // 如果请求失败，生产者也会自动重试，即使设置成０
		props.put("batch.size", 16384);// 生产者为每个分区维护未发送记录的缓冲区。
		props.put("linger.ms", 1);// 默认立即发送，这里这是延时毫秒数
		props.put("buffer.memory", 33554432);// 生产者缓冲大小，当缓冲区耗尽后，额外的发送调用将被阻塞。时间超过max.block.ms将抛出TimeoutException
		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
	}

	private static KafkaUtil _instance = null;
	private Producer<String, String> kafkaProducerSingleton = null;
	public static KafkaUtil getInstance() {
		if(_instance == null)
		synchronized (KafkaUtil.class) {
			if(_instance == null) {
				_instance = new KafkaUtil();
				// 在JVM上增加钩子程序，执行对应Kafak生产者进行关闭
				Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
					@SuppressWarnings("rawtypes")
					@Override
					public void run() {
						if(_instance.kafkaProducerSingleton != null 
							&& _instance.kafkaProducerSingleton instanceof KafkaProducer) {
							// 执行关闭生产者
							((KafkaProducer)_instance.kafkaProducerSingleton).close();
						}
					}
				}));
			}
		}
		return _instance;
	}
	
	private Producer<String, String> getOnlyOneProcedure(){
		if(kafkaProducerSingleton == null) {
			kafkaProducerSingleton = new KafkaProducerLocal<String, String>(props);
		}
		return this.kafkaProducerSingleton;
	}
	
	/**
	 * 生产消息
	 * <p>1、在同一个线程上下文中，共享同一个消息生产者</p>
	 * <p>2、本方法自动调用 <code>KafkaUtil.producerData(topicName, key, value, false)</code></p>
	 * <p>3、在能够确定是在同一个线程的最后一次发送消息是地，需要 关闭消息生产者，
	 * 通过调用 <code>com.tusvn.ccinfra.API.util.KafkaUtil.producerData(topicName, key, value, true)</code> 完成生产消息完成后自动关闭生产者 
	 * 或者通过调用 <code>com.tusvn.ccinfra.API.util.KafkaUtil.producerData(topicName, key, value)</code> 后再 调用 <code>com.tusvn.ccinfra.API.util.KafkaUtil.closeProducer()</code> 手动完善关闭生产者 </p>
	 * @param topicName
	 *            主题名称
	 * @param key
	 *            消息的key
	 * @param value
	 *            消息的value
	 * @see com.tusvn.ccinfra.API.util.KafkaUtil.producerData
	 */
	public static void producerData(String topicName, String key, String value) {
		producerData(topicName, key, value, false);
	}
	/**
	 * 生产消息
	 * 
	 * @param topicName
	 *            主题名称
	 * @param key
	 *            消息的key
	 * @param value
	 *            消息的value
	 * @param autoClose
	 *            消息发送完毕后，是否立即执行 关闭 KafkaProcedure
	 */
	public static void producerData(String topicName, String key, String value, boolean autoClose) {
		if (null == topicName || "".equals(topicName) || null == value 
				|| "".equals(value)) {
			return;
		}
		Producer<String, String> producer = getProducer();
		if(producer instanceof KafkaProducerLocal) {
			if(((KafkaProducerLocal<String, String>)producer).isClosed()) {
				producer = getNewProducer();
			}
		}
		producer.send(new ProducerRecord<String, String>(topicName, key, value));
		producer.flush();
		LOGGER.debug("producerData->send data:{} success",value);
	}
	
	/**
	 * 返回一个消费者对象
	 * 
	 * @param topicName
	 *            主题名称,多个用逗号分隔
	 * @param groupId
	 *            组id
	 */
	public static Consumer<String, String> getConsumer(String topicName, String groupId) {
		if (null == topicName || "".equals(topicName) || null == groupId || "".equals(groupId)) {
			return null;
		}
		Properties props = new Properties();
		props.put("bootstrap.servers", BOOTSTRAP_SERVERS);// 服务器ip:端口号，集群用逗号分隔
		props.put("group.id", groupId);
		props.put("enable.auto.commit", "true");
		props.put("auto.commit.interval.ms", "1000");
		props.put("session.timeout.ms", "30000");
		props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		Consumer<String, String> consumer = new KafkaConsumer<>(props);
		consumer.subscribe(Arrays.asList(topicName));
		return consumer;
	}

	/**
	 * 关闭Producer
	 */
	public static void closeProducer() {
		// update by xchao 2018.06.08
		// 由于在同一JVM内Kafak的生产者是同一个实例，所以生产数据后不能关闭
//		Producer<String, String> producer = getProducer();
//		if(producer != null) {
//			producer.close();
//		}
	}

	private static Producer<String, String> getProducer(){
		Producer<String, String> producer = threadLocal.get();
		if (null == producer) {
			producer = getNewProducer();
			resetProducerToThreadLocal(producer);
		}
		return producer;
	}
	private static Producer<String, String> getNewProducer(){
		return KafkaUtil.getInstance().getOnlyOneProcedure();
	}
	private static void resetProducerToThreadLocal(Producer<String, String> producer) {
		threadLocal.set(producer);
	}
	private static class KafkaProducerLocal<K,V> extends KafkaProducer<K, V>{
		private boolean closed = false;
		
		public KafkaProducerLocal(Map<String, Object> configs, Serializer<K> keySerializer,
				Serializer<V> valueSerializer) {
			super(configs, keySerializer, valueSerializer);
			// TODO Auto-generated constructor stub
		}
		public KafkaProducerLocal(Map<String, Object> configs) {
			super(configs);
			// TODO Auto-generated constructor stub
		}
		public KafkaProducerLocal(Properties properties, Serializer<K> keySerializer, Serializer<V> valueSerializer) {
			super(properties, keySerializer, valueSerializer);
			// TODO Auto-generated constructor stub
		}
		public KafkaProducerLocal(Properties properties) {
			super(properties);
			// TODO Auto-generated constructor stub
		}
		@Override
	    public void close() {
			super.close();
			setClosed(true);
		}
		/**
		 * @return the closed
		 */
		public boolean isClosed() {
			return closed;
		}
		/**
		 * @param closed the closed to set
		 */
		public void setClosed(boolean closed) {
			this.closed = closed;
		}
	}
	public static void main(String[] args) {
		new Thread(new Runnable() {
			public void run() {
				int seq = 1;
				//生产数据
				while(true) {
					KafkaUtil.producerData("test", "44", seq++ + ":timestam:" + System.currentTimeMillis());
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				Consumer<String, String> consumer = KafkaUtil.getConsumer("test", "testGroup");
				while(true) {
					//消费数据
					ConsumerRecords<String, String> records = consumer.poll(100);
					for (ConsumerRecord<String, String> consumerRecord : records) {
						System.out.println(consumerRecord.value());
					}
				}
			}
		}).start();
		
	}

	/**
	 * 发送车辆基本信息
	 * 
	 * @param timestamp
	 *            时间戳
	 * @param vid
	 *            车辆标识
	 * @param value
	 *            车辆基本信息数据
	 */
	public static void sendCarData(String timestamp, String vid, byte[] value) {
		if (null == timestamp || "".equals(timestamp) || null == vid || "".equals(vid) || null == value
				|| value.length == 0) {
			return;
		}
		String data = new StringBuffer(bytesToHexString(value)).append(SEPARATOR).append(timestamp).append(SEPARATOR)
				.append(vid).toString();
		producerData(REPT_VEHICLEBASICDATA, null, data, false);
	}

	/**
	 * 数组转换成十六进制字符串
	 * 
	 * @param byte[]
	 * @return HexString
	 */
	public static final String bytesToHexString(byte[] bArray) {
		StringBuffer sb = new StringBuffer(bArray.length);
		String sTemp;
		for (int i = 0; i < bArray.length; i++) {
			sTemp = Integer.toHexString(0xFF & bArray[i]);
			if (sTemp.length() < 2)
				sb.append(0);
			sb.append(sTemp.toUpperCase());
		}
		return sb.toString();
	}

}
