package com.smile.webchat.cache;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import redis.clients.jedis.BinaryClient.LIST_POSITION;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;
/**
 * 
 * ������ RedisManager
 * ������
 * ���ߣ� ���
 * ����ʱ�䣺2016��12��24��-����10:54:41
 *
 */
public class RedisManager {
	/**
	 * redis����������
	 */
	private String host;
	/**
	 * �˿�
	 */
	private int port;
	/**
	 * ����
	 */
	private String password;
	/**
	 * ����ʱ��
	 */
	private int expire = 0;
	/**
	 * ��ʱʱ��
	 */
	private int timeout = 10000;
	/**
	 * ���ӳ�����
	 */
	private JedisPoolConfig poolConfig;
	
	private Jedis jedis;//����Ƭ�ͻ�������
	private JedisPool jedisPool = null; //����Ƭ���ӳ�
	private ShardedJedis shardedJedis;//��Ƭ�ͻ�������
	private ShardedJedisPool shardedJedisPool;//��Ƭ���ӳ�




	public void initalPool(){
		if (null == host || 0 == port) {
            System.out.println("���ʼ��redis�����ļ�");
            throw new NullPointerException("�Ҳ���redis����");
        }
		if(null==jedisPool){
			jedisPool = new JedisPool(poolConfig, host, port, timeout, password);
		//	jedisPool = new JedisPool(poolConfig, host, port, timeout);
			jedis = jedisPool.getResource();
		}
	}
	
	//��ʼ����Ƭ��(��Ⱥ)
	private void initalShardedPool() {
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxIdle(5);
		config.setMaxWaitMillis(10001);
		config.setTestOnBorrow(false);
		//slave ����
		List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
		shards.add(new JedisShardInfo("127.0.0.1", 6379, "master"));
		shards.add(new JedisShardInfo("192.168.41.217", 6379, "master"));
		
		shardedJedisPool = new ShardedJedisPool(config, shards);
		shardedJedis = shardedJedisPool.getResource();
	}
	
	
	//--------------------------------------------------------------------------------------KEY ����--------------------------------------------------------------------------------------------------------------------------
	
	/**
	 * 
	 * @Description: �ж��Ƿ����ĳ�� key
	 * @author yanghui 
	 * @Created 2016��5��31��
	 * @param key
	 * @return true  ����  false  ������
	 */
	public boolean exists(String key){
		return jedis.exists(key);
	}
	
	public boolean exists(byte[] key){
		return jedis.exists(key);
	}
	
	/**
	 * 
	 * @Description: ��ȡ��ǰ key �� value
	 * @author yanghui 
	 * @Created 2016��6��15��
	 * @param key
	 * @return ���� key ��Ӧ�� value ,������ key ʱ���� null
	 */
	public String get(String key){
		String value = null;
		try {
			value = jedis.get(key);
		} catch (Exception e) {
			e.printStackTrace();
			redisClose();
		}finally {
			redisClose();
		}
		return value;
	}
	
	/**
	 * 
	 * @Description:��ȡ��� key �� value ֵ
	 * @author yanghui 
	 * @Created 2016��6��15��
	 * @param keys
	 * @return ���� key ���� value,������ key ���� null
	 */
	public List<String> mget(String...keys){
		List<String> list = new ArrayList<String>();
		try {
			list = jedis.mget(keys);
		} catch (Exception e) {
			e.printStackTrace();
			redisClose();
		}finally {
			redisClose();
		}
		return list;
	}
	
	/**
	 * 
	 * @Description:��ȡ��� key �� value ֵ
	 * @author yanghui 
	 * @Created 2016��6��15��
	 * @param keys
	 * @return ���� key ���� value,������ key ���� null
	 */
	public List<byte[]> mget(byte[]...keys){
		List<byte[]> list = new ArrayList<byte[]>();
		try {
			list = jedis.mget(keys);
		} catch (Exception e) {
			e.printStackTrace();
			redisClose();
		}finally {
			redisClose();
		}
		return list;
	}
	
	/**
	 * 
	 * @Description: ��ȡ��ǰ key �� value
	 * @author yanghui 
	 * @Created 2016��6��15��
	 * @param key
	 * @return ���� key ��Ӧ�� value ,������ key ʱ���� null
	 */
	public byte[] get(byte[] key){
		byte[] value = null;
		try {
			value = jedis.get(key);
		} catch (Exception e) {
			e.printStackTrace();
			redisClose();
		}finally {
			redisClose();
		}
		return value;
	}
	
	/**
	 * 
	 * @Description: ɾ��ָ����key:������ɾ���������������
	 * @author yanghui 
	 * @Created 2016��6��1��
	 * @param key
	 * @return ����key����1�����򷵻�0
	 */
	public Long del(String key){
		long value = 0l;
		try {
			value = jedis.del(key);
		} catch (Exception e) {
			e.printStackTrace();
			redisClose();
		}finally {
			redisClose();
		}
		return value;
	}
	
	public Long del(byte[] key){
		long value = 0l;
		try {
			value = jedis.del(key);
		} catch (Exception e) {
			e.printStackTrace();
			redisClose();
		}finally {
			redisClose();
		}
		return value;
	}
	
	/**
	 * 
	 * @Description: ɾ�����key��ֵ
	 * @author yanghui 
	 * @Created 2016��6��15��
	 * @param keys
	 * @return ����key����1�����򷵻�0
	 */
	public Long del(String[] keys){
		long value = 0l;
		try {
			value = jedis.del(keys);
		} catch (Exception e) {
			e.printStackTrace();
			redisClose();
		}finally {
			redisClose();
		}
		return value;
	}
	
	/**
	 * 
	 * @Description:���л����� key �������ر����л���ֵ��key�����ڷ���null
	 * @author yanghui 
	 * @Created 2016��6��16��
	 * @param key
	 * @return 
	 */
	public byte[] dump(String key){
		byte[] result = new  byte[10];
		try {
			result = jedis.dump(key);
		} catch (Exception e) {
			e.printStackTrace();
			redisClose();
		}finally {
			redisClose();
		}
		return result;
	}
	/**
	 * 
	 * @Description:���л����� key �������ر����л���ֵ��key�����ڷ���null
	 * @author yanghui 
	 * @Created 2016��6��16��
	 * @param key
	 * @return
	 */
	public byte[] dump(byte[] key){
		byte[] result = new  byte[10];
		try {
			result = jedis.dump(key);
		} catch (Exception e) {
			e.printStackTrace();
			redisClose();
		}finally {
			redisClose();
		}
		return result;
	}
	
	/**
	 * �Ƴ����� key �Ĺ���ʱ�䣬ʹ�� key �������ڡ�
	 * @Description:
	 * @author yanghui 
	 * @Created 2016��6��16��
	 * @param key
	 * @return 1��ʾkey�Ĺ���ʱ�䱻�Ƴ�  0 ��ʾkey�����ڻ�û�й���ʱ��
	 */
	public Long persist(String key){
		long result = 0l;
		try {
			result = jedis.persist(key);
		} catch (Exception e) {
			e.printStackTrace();
			redisClose();
		}finally {
			redisClose();
		}
		return result;
	}
	/**
	 * �Ƴ����� key �Ĺ���ʱ�䣬ʹ�� key �������ڡ�
	 * @Description:
	 * @author yanghui 
	 * @Created 2016��6��16��
	 * @param key
	 * @return
	 */
	public Long persist(byte[] key){
		long result = 0l;
		try {
			result = jedis.persist(key);
		} catch (Exception e) {
			e.printStackTrace();
			redisClose();
		}finally {
			redisClose();
		}
		return result;
	}
	
	/**
	 * 
	 * @Description:�Ժ���Ϊ��λ���� key ��ʣ�����ʱ��:
	 * 				�� key �����ڣ����� key û������ʣ������ʱ��ʱ��������� -1, 
	 * 				�����Է��� key ��ʣ������ʱ��
	 * @author yanghui 
	 * @Created 2016��6��16��
	 * @param key
	 * @return
	 */
	public Long pttl(byte[] key){
		long result = 0l;
		try {
			result = jedis.pttl(key);
		} catch (Exception e) {
		}finally {
			redisClose();
		}
		return result;
	}
	/**
	 * 
	 * @Description:�Ժ���Ϊ��λ���� key ��ʣ�����ʱ��:
	 * 				�� key �����ڣ����� key û������ʣ������ʱ��ʱ��������� -1, 
	 * 				�����Է��� key ��ʣ������ʱ��
	 * @author yanghui 
	 * @Created 2016��6��16��
	 * @param key
	 * @return
	 */
	public Long pttl(String key){
		long result = 0l;
		try {
			result = jedis.pttl(key);
		} catch (Exception e) {
		}finally {
			redisClose();
		}
		return result;
	}
	
	
	/**
	 * 
	 * @Description:����Ϊ��λ���� key ��ʣ�����ʱ��:
	 * 				�� key �����ڣ����� key û������ʣ������ʱ��ʱ��������� -1, 
	 * 				�����Է��� key ��ʣ������ʱ��
	 * @author yanghui 
	 * @Created 2016��6��16��
	 * @param key
	 * @return
	 */
	public Long ttl(byte[] key){
		long result = 0l;
		try {
			result = jedis.ttl(key);
		} catch (Exception e) {
		}finally {
			redisClose();
		}
		return result;
	}
	/**
	 * 
	 * @Description:����Ϊ��λ���� key ��ʣ�����ʱ��:�� key �����ڣ����� key û������ʣ������ʱ��ʱ��������� -1, �����Է��� key ��ʣ������ʱ�Ժ���Ϊ��λ���� key ��ʣ�����ʱ��:
	 * 				�� key �����ڣ����� key û������ʣ������ʱ��ʱ��������� -1, 
	 * 				�����Է��� key ��ʣ������ʱ��
	 * @author yanghui 
	 * @Created 2016��6��16��
	 * @param key
	 * @return
	 */
	public Long ttl(String key){
		long result = 0l;
		try {
			result = jedis.ttl(key);
		} catch (Exception e) {
		}finally {
			redisClose();
		}
		return result;
	}
	
	/**
	 * 
	 * @Description: ����ӵ�ǰ���ݿ����������һ�� key : �����ݿⲻΪ��ʱ������һ�� key ��
	 *  �����ݿ�Ϊ��ʱ������ nil
	 * @author yanghui 
	 * @Created 2016��6��16��
	 * @return
	 */
	public String randomKey(){
		String result = "";
		try {
			result = jedis.randomKey();
		} catch (Exception e) {
		}finally {
			redisClose();
		}
		return result;
	}
	
	/**
	 * 
	 * @Description: ����ӵ�ǰ���ݿ����������һ�� key : �����ݿⲻΪ��ʱ������һ�� key �� �����ݿ�Ϊ��ʱ������ nil
	 * @author yanghui 
	 * @Created 2016��6��16��
	 * @return
	 */
	public byte[] randomBinaryKey(){
		byte[] result = new byte[1];
		try {
			result = jedis.randomBinaryKey();
		} catch (Exception e) {
		}finally {
			redisClose();
		}
		return result;
	}
	
	/**
	 * 
	 * @Description:�޸� key ������ �� 
	 * 	�����ɹ�ʱ��ʾ OK ��ʧ��ʱ�򷵻�һ������
	 *	�� OLD_KEY_NAME �� NEW_KEY_NAME ��ͬ������ OLD_KEY_NAME ������ʱ������һ������ 
	 *	�� NEW_KEY_NAME �Ѿ�����ʱ�� RENAME ������Ǿ�ֵ��
	 * @author yanghui 
	 * @Created 2016��6��16��
	 * @param oldkey
	 * @param newkey
	 * @return �޸ĳɹ����� OK���޸�ʧ�ܷ��� ERROR
	 */
	public String rename(String oldkey,String newkey){
		String result = "";
		try {
			result = jedis.rename(oldkey, newkey);
		} catch (Exception e) {
			result = "ERROR";
			e.printStackTrace();
		}finally {
			redisClose();
		}
		return result;
	}
	
	/**
	 * 
	 * @Description:���µ� key ������ʱ�޸� key ������ : 
	 * �޸ĳɹ�ʱ������ 1 �� ��� NEW_KEY_NAME �Ѿ����ڣ����� 0 ��
	 * ��oldKey������ʱ���׳��쳣��������0
	 * @author yanghui 
	 * @Created 2016��6��16��
	 * @param oldkey
	 * @param newkey
	 * @return �޸ĳɹ�����1  �޸�ʧ�ܷ���2
	 */
	public Long renamenx(String oldkey,String newkey){
		long result = 0l;
		try {
			result = jedis.renamenx(oldkey, newkey);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			redisClose();
		}
		return result;
	}
	
	/**
	 * 
	 * @Description: ���� key ���������ͣ����������У�
	 * none (key������)string (�ַ���)list (�б�)set (����)zset (����)hash (��ϣ��)
	 * @author yanghui 
	 * @Created 2016��6��16��
	 * @param key
	 * @return
	 */
	public String keyType(String key){
		String result = "";
		try {
			result = jedis.type(key);
		} catch (Exception e) {
		}finally {
			redisClose();
		}
		return result;
	}
	/**
	 * 
	 * @Description: ���� key ���������ͣ����������У�none (key������)string (�ַ���)list (�б�)set (����)zset (����)hash (��ϣ��)
	 * @author yanghui 
	 * @Created 2016��6��16��
	 * @param key
	 * @return 
	 */
	public String rename(byte[] key){
		String result = "";
		try {
			result = jedis.type(key);
		} catch (Exception e) {
		}finally {
			redisClose();
		}
		return result;
	}
	
	/**
	 * 
	 * @Description:����key�Ĺ���ʱ��
	 * @author yang_hui 
	 * @Created 2016��7��19��
	 * @param key
	 * @param time ����Ϊ��λ
	 * @return 1 ��ʾ��ʱ������  0��ʾkey�����ڻ��ܱ�����
	 */
	public long exprie(String key,int time){
		long result = 0l;
		try {
			result = jedis.expire(key, time);
		} catch (Exception e) {
		}finally {
			redisClose();
		}
		return result;
	}
	
	public long exprie(byte[] key,int time){
		long result = 0l;
		try {
			result = jedis.expire(key, time);
		} catch (Exception e) {
		}finally {
			redisClose();
		}
		return result;
	}
	
	/**
	 * 
	 * @Description:��������ƥ���key
	 * @author yang_hui 
	 * @Created 2016��7��19��
	 * @param pattern ƥ�����
	 * @return
	 */
	public Set<String> keys(String pattern){
		Set<String> set = new HashSet<String>();
		try {
			set = jedis.keys(pattern);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			redisClose();
		}
		return set;
	}
	
	//--------------------------------------------------------------------------------------�ַ�������--------------------------------------------------------------------------------------------------------------------------
	
	/**
	 * 
	 * @Description: ����Ӧ��key�м�������[String]��value���÷�����ֱ�Ӹ���keyԭ����ֵ
	 * @author yanghui 
	 * @Created 2016��5��31��
	 * @param key
	 * @param value
	 * @param expire  ��Ч��
	 * @return OK �ɹ�
	 */
	public String set(String key,String value,int expire){
		String result = "";
		try {
			result = jedis.set(key, value);
			if(0!=expire){
				jedis.expire(key, expire);
			}
		}finally {
			redisClose();
		}
		return result;
	}
	
	/**
	 * 
	 * @Description:����Ӧ��key�м�������[byte] �� value���÷�����ֱ�Ӹ���keyԭ����ֵ
	 * @author yanghui 
	 * @Created 2016��6��15��
	 * @param key
	 * @param value
	 * @param expire ��Ч��
	 * @return
	 */
	public String set(byte[] key,byte[] value,int expire){
		String result = "";
		try {
			result = jedis.set(key, value);
			if(0!=expire){
				jedis.expire(key, expire);
			}
		}finally {
			redisClose();
		}
		return result;
	}
	
	/**
	 * 
	 * @Description: ������ֵ��ʱ��ֹ����ԭֵ
	 * @author yanghui 
	 * @Created 2016��6��15��
	 * @param key
	 * @param value
	 * @param expire
	 * @return key �������������ɹ����� 1��key����������������0
	 */
	public Long setnx(String key,String value,int expire){
		Long result = 0l;
		try {
			result = jedis.setnx(key, value);
			if(0!=expire){
				jedis.expire(key, expire);
			}
		} catch (Exception e) {
		}finally {
			redisClose();
		}
		return result;
	}

	
	/**
	 * 
	 * @Description: ������ֵ��ʱ��ֹ����ԭֵ
	 * @author yanghui 
	 * @Created 2016��6��15��
	 * @param key
	 * @param value
	 * @param expire
	 * @return key �������������ɹ����� 1��key����������������0
	 */
	public Long setnx(byte[] key,byte[] value,int expire){
		Long result = 0l;
		try {
			result = jedis.setnx(key, value);
			if(0!=expire){
				jedis.expire(key, expire);
			}
		} catch (Exception e) {
		}finally {
			redisClose();
		}
		return result;
	}
	
	/**
	 * 
	 * @Description: ͬʱ�������޸Ķ����ֵ��
	 * @author yanghui 
	 * @Created 2016��6��15��
	 * @param keysvalues ��ʽ��("key201","value201","key202","value202")
	 * @return OK �ɹ�
	 */
	public String mset(String... keysvalues){
		String result = "";
		try {
			result = jedis.mset(keysvalues);
		}finally {
			redisClose();
		}
		return result;
	}
	
	/**
	 * 
	 * @Description:ͬʱ���������ֵ�Բ��ҷ�ֹ����ԭֵ��������һ��key����������ʧ�ܣ�û�е�keyҲ���ᱻ��ӣ����� 0��ֻ�е�����key��������ʱ���������ɹ������� 1
	 * @author yanghui 
	 * @Created 2016��6��15��
	 * @param keysvalues
	 * @return
	 */
	public Long msetnx(String...keysvalues){
		Long result = 0l;
		try {
			result = jedis.msetnx(keysvalues);
		}finally {
			redisClose();
		}
		return result;
	}

	
	/**
	 * 
	 * @Description: ��ǰ Key �� value ��׷�����ݡ����keyԭ����valueΪhello ,׷�ӵ�ֵΪworld,׷�Ӻ������Ϊhelloworld
	 * @author yanghui 
	 * @Created 2016��6��1��
	 * @param key
	 * @param val
	 * @return ���ظ� key �ĳ���
	 */
	public Long append(String key,String val){
		long value = 0l;
		try {
			value = jedis.append(key, val);
		} catch (Exception e) {
		}finally {
			redisClose();
		}
		return value;
	}
	
	/**
	 * 
	 * @Description:��ȡ�洢��ָ�� key ��Ӧֵ�ַ��������ַ������ַ����Ľ�ȡ��Χ�� start �� end ����ƫ��������(���� start �� end ����)��
	 * @author yanghui 
	 * @Created 2016��6��16��
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public String getRange(String key,long start,long end){
		String s = "";
		try {
			s = jedis.getrange(key, start, end);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			redisClose();
		}
		return s;
	}
	
	/**
	 * 
	 * @Description:��ȡ�洢��ָ�� key ��Ӧֵ�ַ��������ַ������ַ����Ľ�ȡ��Χ�� start �� end ����ƫ��������(���� start �� end ����)��
	 * @author yanghui 
	 * @Created 2016��6��16��
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public byte[] getRange(byte[] key,long start,long end){
		byte[] s = new byte[10];
		try {
			s = jedis.getrange(key, start, end);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			redisClose();
		}
		return s;
	}
	
	/**
	 * 
	 * @Description:����ָ�� key ��ֵ�������� key �ɵ�ֵ��
	 * @author yanghui 
	 * @Created 2016��6��16��
	 * @param key
	 * @param value
	 * @return
	 */
	public byte[] getSet(byte[] key,byte[] value){
		byte[] s = new byte[10];
		try {
			s = jedis.getSet(key, value);
		} catch (Exception e) {
		}finally {
			redisClose();
		}
		return s;
	}
	/**
	 * 
	 * @Description:����ָ�� key ��ֵ�������� key �ɵ�ֵ��
	 * @author yanghui 
	 * @Created 2016��6��16��
	 * @param key
	 * @param value
	 * @return
	 */
	public String getSet(String key,String value){
		String s = "";
		try {
			s = jedis.getSet(key, value);
		} catch (Exception e) {
		}finally {
			redisClose();
		}
		return s;
	}
	
	/**
	 * 
	 * @Description:Ϊָ���� key ����ֵ�������ʱ�䡣��� key �Ѿ����ڣ� SETEX ������滻�ɵ�ֵ��
	 * @author yanghui 
	 * @Created 2016��6��16��
	 * @param key
	 * @param seconds
	 * @param value
	 * @return
	 */
	public String setex(String key,int seconds,String value){
		String s = "";
		try {
			s = jedis.setex(key, seconds, value);
		} catch (Exception e) {
		}finally {
			redisClose();
		}
		return s;
	}
	
	/**
	 * 
	 * @Description:��ָ�����ַ������Ǹ��� key ��������ַ���ֵ�����ǵ�λ�ô�ƫ���� offset ��ʼ��
	 * @author yanghui 
	 * @Created 2016��6��16��
	 * @param key
	 * @param offset
	 * @param value
	 * @return
	 */
	public Long setrange(String key,int offset,String value){
		long s = 0l;
		try {
			s = jedis.setrange(key, offset, value);
		} catch (Exception e) {
		}finally {
			redisClose();
		}
		return s;
	}
	
	/**
	 * 
	 * @Description:��ȡָ�� key ��������ַ���ֵ�ĳ��ȡ��� key ����Ĳ����ַ���ֵʱ������һ������ �ַ���ֵ�ĳ��ȡ� �� key ������ʱ������ 0��
	 * @author yanghui 
	 * @Created 2016��6��16��
	 * @param key
	 * @return
	 */
	public Long strlen(String key){
		long s = 0l;
		try {
			s = jedis.strlen(key);
		} catch (Exception e) {
		}finally {
			redisClose();
		}
		return s;
	}
	
	/**
	 * 
	 * @Description: �� key �д�������ּ���ָ��������ֵ����� key �����ڣ���ô key ��ֵ���ȱ���ʼ��Ϊ 0 ��Ȼ����ִ�� INCRBY ���
					���ֵ������������ͣ����ַ������͵�ֵ���ܱ�ʾΪ���֣���ô����һ������	��������ֵ������ 64 λ(bit)�з������ֱ�ʾ֮�ڡ�
	 * @author yanghui 
	 * @Created 2016��6��16��
	 * @param key
	 * @param inc
	 * @return
	 */
	public Long incrBy(String key,long inc){
		long s = 0l;
		try {
			s = jedis.incrBy(key, inc);
		} catch (Exception e) {
		}finally {
			redisClose();
		}
		return s;
	}
	
	/**
	 * 
	 * @Description: �� key �д�������ּ�ȥָ��������ֵ����� key �����ڣ���ô key ��ֵ���ȱ���ʼ��Ϊ 0 ��Ȼ����ִ�� INCRBY ���
					���ֵ������������ͣ����ַ������͵�ֵ���ܱ�ʾΪ���֣���ô����һ������	��������ֵ������ 64 λ(bit)�з������ֱ�ʾ֮�ڡ�
	 * @author yanghui 
	 * @Created 2016��6��16��
	 * @param key
	 * @param inc
	 * @return
	 */
	public Long decrBy(String key,long inc){
		long s = 0l;
		try {
			s = jedis.decrBy(key, inc);
		} catch (Exception e) {
		}finally {
			redisClose();
		}
		return s;
	}
	
	/**
	 * 
	 * @Description:Ϊ key ���������ֵ����ָ���ĸ���������ֵ����� key �����ڣ���ô INCRBYFLOAT ���Ƚ� key ��ֵ��Ϊ 0 ����ִ�мӷ�������
	 * @author yanghui 
	 * @Created 2016��6��16��
	 * @param key
	 * @param inc
	 * @return
	 */
	public Double incrBy(String key,double inc){
		double s = 0d;
		try {
			s = jedis.incrByFloat(key, inc);
		} catch (Exception e) {
		}finally {
			redisClose();
		}
		return s;
	}
	
	//----------------------------------------------------------------------------------------Set(���򼯺ϲ���)------------------------------------------------------------------------------------------------------------------
	
	/**
	 * 
	 * @Description:�� set��������ӵ���Ԫ�أ�Ԫ���Ѵ��ڷ���0�����򷵻�1
	 * @author yanghui 
	 * @Created 2016��6��15��
	 * @param key
	 * @param members
	 * @return
	 */
	public Long sadd(String key,String members){
		Long result = 0l;
		try {
			result = jedis.sadd(key, members);
		} catch (Exception e) {
		}finally {
			redisClose();
		}
		return result;
	}
	
	/**
	 * 
	 * @Description:�� set��������ӵ�������Ԫ�أ�����һ��Ԫ�ز����ڣ�����ӳɹ�����1��Ԫ�ض��Ѵ����򷵻�0
	 * @author yanghui 
	 * @Created 2016��6��15��
	 * @param key
	 * @param members
	 * @return
	 */
	public Long sadd(String key,String...members){
		Long result = 0l;
		try {
			result = jedis.sadd(key, members);
		} catch (Exception e) {
		}finally {
			redisClose();
		}
		return result;
	}
	/**
	 * 
	 * @Description:�� set��������ӵ���Ԫ��:��ӳɹ�����1
	 * @author yanghui 
	 * @Created 2016��6��15��
	 * @param key
	 * @param members
	 * @return
	 */
	public Long sadd(byte[] key,byte[] members){
		Long result = 0l;
		try {
			result = jedis.sadd(key, members);
		} catch (Exception e) {
		}finally {
			redisClose();
		}
		return result;
	}
	
	/**
	 * 
	 * @Description:�� set��������ӵ�������Ԫ��
	 * @author yanghui 
	 * @Created 2016��6��15��
	 * @param key
	 * @param members
	 * @return
	 */
	public Long sadd(byte[] key,byte[]...members){
		Long result = 0l;
		try {
			result = jedis.sadd(key, members);
		} catch (Exception e) {
		}finally {
			redisClose();
		}
		return result;
	}
	
	/**
	 * 
	 * @Description:���� key ��Ӧ�ļ����е�Ԫ��
	 * @author yanghui 
	 * @Created 2016��6��15��
	 * @param key
	 * @return
	 */
	public Set<String> smembers(String key){
		Set<String> set = new HashSet<String>();
		try {
			set = jedis.smembers(key);
		} catch (Exception e) {
		}finally {
			redisClose();
		}
		return set;
	}
	/**
	 * 
	 * @Description:���� key ��Ӧ�ļ����е�Ԫ��
	 * @author yanghui 
	 * @Created 2016��6��15��
	 * @param key
	 * @return
	 */
	public Set<byte[]> smembers(byte[] key){
		Set<byte[]> set = new HashSet<byte[]>();
		try {
			set = jedis.smembers(key);
		} catch (Exception e) {
		}finally {
			redisClose();
		}
		return set;
	}
	
	/**
	 * 
	 * @Description:ɾ����������Ԫ�أ���Ԫ�ض��������ڼ����з���0������һ��Ԫ�ش����ڼ����з���1
	 * @author yanghui 
	 * @Created 2016��6��15��
	 * @param key
	 * @param members
	 * @return
	 */
	public Long srem(String key,String...members){
		Long result = 0l;
		try {
			result = jedis.srem(key, members);
		} catch (Exception e) {
		}finally {
			redisClose();
		}
		return result;
	}
	/**
	 * 
	 * @Description:ɾ����������Ԫ�أ���Ԫ�ض��������ڼ����з���0������һ��Ԫ�ش����ڼ����з���1
	 * @author yanghui 
	 * @Created 2016��6��15��
	 * @param key
	 * @param members
	 * @return
	 */
	public Long srem(byte[] key,byte[]...members){
		Long result = 0l;
		try {
			result = jedis.srem(key, members);
		} catch (Exception e) {
		}finally {
			redisClose();
		}
		return result;
	}
	
	/**
	 * 
	 * @Description:�ж�Ԫ���Ƿ�����ڼ�����
	 * @author yanghui 
	 * @Created 2016��6��15��
	 * @param key
	 * @param member
	 * @return
	 */
	public boolean sismember(String key,String member){
		boolean result = false;
		try {
			result = jedis.sismember(key, member);
		} catch (Exception e) {
		}finally {
			redisClose();
		}
		return result;
	}
	
	
	/**
	 * 
	 * @Description:�ж�Ԫ���Ƿ�����ڼ�����
	 * @author yanghui 
	 * @Created 2016��6��15��
	 * @param key
	 * @param member
	 * @return
	 */
	public boolean sismember(byte[] key,byte[] member){
		boolean result = false;
		try {
			result = jedis.sismember(key, member);
		} catch (Exception e) {
		}finally {
			redisClose();
		}
		return result;
	}
	
	/**
	 * 
	 * @Description:���������Ԫ�صĽ���
	 * @author yanghui 
	 * @Created 2016��6��15��
	 * @param keys
	 * @return
	 */
	public Set<String> sinter(String...keys){
		Set<String> set = new HashSet<String>();
		try {
			set = jedis.sinter(keys);
		} catch (Exception e) {
		}finally {
			redisClose();
		}
		return set;
	}
	
	
	/**
	 * 
	 * @Description:���������Ԫ�صĲ���
	 * @author yanghui 
	 * @Created 2016��6��15��
	 * @param keys
	 * @return
	 */
	public Set<String> sunion(String...keys){
		Set<String> set = new HashSet<String>();
		try {
			set = jedis.sunion(keys);
		} catch (Exception e) {
		}finally {
			redisClose();
		}
		return set;
	}
	
	
	
	/**
	 * 
	 * @Description:���������Ԫ�صĲ
	 * @author yanghui 
	 * @Created 2016��6��15��
	 * @param keys
	 * @return
	 */
	public Set<String> sdiff(String...keys){
		Set<String> set = new HashSet<String>();
		try {
			set = jedis.sdiff(keys);
		} catch (Exception e) {
		}finally {
			redisClose();
		}
		return set;
	}
	
	
	/**
	 * 
	 * @Description:���������Ԫ�صĽ���
	 * @author yanghui 
	 * @Created 2016��6��15��
	 * @param keys
	 * @return
	 */
	public Set<byte[]> sinter(byte[]...keys){
		Set<byte[]> set = new HashSet<byte[]>();
		try {
			set = jedis.sinter(keys);
		} catch (Exception e) {
		}finally {
			redisClose();
		}
		return set;
	}
	
	
	/**
	 * 
	 * @Description:���������Ԫ�صĲ���
	 * @author yanghui 
	 * @Created 2016��6��15��
	 * @param keys
	 * @return
	 */
	public Set<byte[]> sunion(byte[]...keys){
		Set<byte[]> set = new HashSet<byte[]>();
		try {
			set = jedis.sunion(keys);
		} catch (Exception e) {
		}finally {
			redisClose();
		}
		return set;
	}
	
	
	
	/**
	 * 
	 * @Description:���������Ԫ�صĲ
	 * @author yanghui 
	 * @Created 2016��6��15��
	 * @param keys
	 * @return
	 */
	public Set<byte[]> sdiff(byte[]...keys){
		Set<byte[]> set = new HashSet<byte[]>();
		try {
			set = jedis.sdiff(keys);
		} catch (Exception e) {
		}finally {
			redisClose();
		}
		return set;
	}
	
	
	/**
	 * 
	 * @Description: ͳ�Ƽ�����Ԫ�ص�����
	 * @author yanghui 
	 * @Created 2016��6��15��
	 * @param key
	 * @return
	 */
	public Long scard(String key){
		Long result = 0l;
		try {
			jedis.scard(key);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			redisClose();
		}
		return result;
	}
	
	//----------------------------------------------------------------------------------------SortedSet(���򼯺ϲ���)------------------------------------------------------------------------------------------------------
	/**
	 * 
	 * @Description:
	 * 				���ڽ�һ���������ֵ���뵽���򼯵��С�
					���ĳ����Ա�Ѿ������򼯵ĳ�Ա����ô���������Ա�ķ���ֵ����ͨ�����²��������ԱԪ�أ�����֤�ó�Ա����ȷ��λ���ϡ�
					����ֵ����������ֵ��˫���ȸ�������
					������򼯺� key �����ڣ��򴴽�һ���յ����򼯲�ִ�� ZADD ������
					�� key ���ڵ�������������ʱ������һ������
	 * @author yanghui 
	 * @Created 2016��6��15��
	 * @param key
	 * @param score
	 * @param member
	 * @return
	 */
	public Long zadd(String key,double score,String member){
		Long result = 0l;
		try {
			result = jedis.zadd(key, score, member);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			redisClose();
		}
		return result;
	}
	
	
	/**
	 * 
	 * @Description:
	 * 				���ڽ�һ��������ԱԪ�ؼ������ֵ���뵽���򼯵��С�
					���ĳ����Ա�Ѿ������򼯵ĳ�Ա����ô���������Ա�ķ���ֵ����ͨ�����²��������ԱԪ�أ�����֤�ó�Ա����ȷ��λ���ϡ�
					����ֵ����������ֵ��˫���ȸ�������
					������򼯺� key �����ڣ��򴴽�һ���յ����򼯲�ִ�� ZADD ������
					�� key ���ڵ�������������ʱ������һ������
	 * @author yanghui 
	 * @Created 2016��6��15��
	 * @param key
	 * @param score
	 * @param member
	 * @return
	 */
	public Long zadd(String key,Map<String,Double> scoreMembers){
		Long result = 0l;
		try {
			jedis.zadd(key, scoreMembers);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			redisClose();
		}
		return result;
	}
	
	public Long zadd(byte[] key,double score,byte[] member){
		Long result = 0l;
		try {
			jedis.zadd(key, score, member);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			redisClose();
		}
		return result;
	}
	
	public Long zadd(byte[] key,Map<byte[],Double> scoreMembers){
		Long result = 0l;
		try {
			jedis.zadd(key, scoreMembers);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			redisClose();
		}
		return result;
	}
	
	/**
	 * 
	 * @Description:
	 *  �������򼯺���ָ�������ڵĳ�Ա��
	 *	���г�Ա��λ�ð�����ֵ����(��С����)������
	 *	������ͬ����ֵ�ĳ�Ա���ֵ���(lexicographical order )�����С�
	 *	�������Ҫ��Ա��ֵ�ݼ�(�Ӵ�С)�����У���ʹ�� ZREVRANGE ���
	 *	�±���� start �� stop ���� 0 Ϊ�ף�Ҳ����˵���� 0 ��ʾ���򼯵�һ����Ա���� 1 ��ʾ���򼯵ڶ�����Ա���Դ����ơ�
	 *	��Ҳ����ʹ�ø����±꣬�� -1 ��ʾ���һ����Ա�� -2 ��ʾ�����ڶ�����Ա���Դ����ơ�
	 * @author yanghui 
	 * @Created 2016��6��15��
	 * @param key
	 * @param start ��ʼ�±�
	 * @param end �����±�
	 * @return
	 */
	public Set<String> zrange(String key,long start,long end){
		Set<String> set = new HashSet<String>();
		try {
			set = jedis.zrange(key, start, end);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			redisClose();
		}
		return set;
	}
	
	/**
	 * 
	 * @Description:
	 *  �������򼯺���ָ�������ڵĳ�Ա��
	 *	���г�Ա��λ�ð�����ֵ����(�Ӵ�С)������
	 *	������ͬ����ֵ�ĳ�Ա���ֵ���(lexicographical order )�����С�
	 *	�±���� start �� stop ���� 0 Ϊ�ף�Ҳ����˵���� 0 ��ʾ���򼯵�һ����Ա���� 1 ��ʾ���򼯵ڶ�����Ա���Դ����ơ�
	 *	��Ҳ����ʹ�ø����±꣬�� -1 ��ʾ���һ����Ա�� -2 ��ʾ�����ڶ�����Ա���Դ����ơ�
	 * @author yanghui 
	 * @Created 2016��6��15��
	 * @param key
	 * @param start ��ʼ�±�
	 * @param end �����±�
	 * @return
	 */
	public Set<String> zrevrange(String key,long start,long end){
		Set<String> set = new HashSet<String>();
		try {
			set = jedis.zrevrange(key, start, end);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			redisClose();
		}
		return set;
	}
	
	/**
	 * 
	 * @Description:ɾ��һ������Ԫ��
	 * @author yanghui 
	 * @Created 2016��6��15��
	 * @param key
	 * @param members
	 * @return
	 */
	public Long zrem(String key,String...members){
		Long result = 0l;
		try {
			result = jedis.zrem(key, members);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			redisClose();
		}
		return result;
	}
	
	/**
	 * 
	 * @Description: �鿴Ԫ�ص�Ȩ��
	 * @author yanghui 
	 * @Created 2016��6��15��
	 * @param key
	 * @param member
	 * @return
	 */
	public Double zscore(String key,String member){
		Double result = 0d;
		try {
			result = jedis.zscore(key, member);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			redisClose();
		}
		return result;
	}
	/**
	 * 
	 * @Description: �鿴Ԫ�ص�Ȩ��
	 * @author yanghui 
	 * @Created 2016��6��15��
	 * @param key
	 * @param member
	 * @return
	 */
	public Double zscore(byte[] key,byte[] member){
		Double result = 0d;
		try {
			result = jedis.zscore(key, member);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			redisClose();
		}
		return result;
	}
	
	/**
	 * 
	 * @Description: ͳ�Ƽ�����Ԫ�ص�����
	 * @author yanghui 
	 * @Created 2016��6��15��
	 * @param key
	 * @return
	 */
	public Long zcard(String key){
		Long result = 0l;
		try {
			result = jedis.zcard(key);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			redisClose();
		}
		return result;
	}
	/**
	 * 
	 * @Description: ͳ�Ƽ�����Ԫ�ص�����
	 * @author yanghui 
	 * @Created 2016��6��15��
	 * @param key
	 * @return
	 */
	public Long zcard(byte[] key){
		Long result = 0l;
		try {
			result = jedis.zcard(key);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			redisClose();
		}
		return result;
	}
	
	
	/**
	 * 
	 * @Description: ͳ�Ƽ�����Ȩ����min(����) �� max(����) ֮��Ԫ�ص�����
	 * @author yanghui 
	 * @Created 2016��6��15��
	 * @param key
	 * @return
	 */
	public Long zcount(String key,double min,double max){
		Long result = 0l;
		try {
			result = jedis.zcount(key,min,max);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			redisClose();
		}
		return result;
	}
	
	
	/**
	 * 
	 * @Description: ͳ�Ƽ�����Ȩ����min �� max ֮��Ԫ�ص�����
	 * @author yanghui 
	 * @Created 2016��6��15��
	 * @param key
	 * @return
	 */
	public Long zcount(byte[] key,double min,double max){
		Long result = 0l;
		try {
			result = jedis.zcount(key,min,max);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			redisClose();
		}
		return result;
	}
	
	/**
	 * 
	 * @Description:�����򼯺���ָ����Ա�ķ�����������
	 * 				����ͨ������һ������ֵ increment ���÷�����ȥ��Ӧ��ֵ������ ZINCRBY key -5 member �������� member �� score ֵ��ȥ 5 
	 * 				�� key �����ڣ���������� key �ĳ�Աʱ  ��ͬ�� ZADD key 
	 * @author yang_hui 
	 * @Created 2016��7��15��
	 * @param key
	 * @param score
	 * @param member
	 * @return
	 */
	public Double zincrby(String key ,double score,String member){
		Double result = 0D;
		try {
			jedis.zincrby(key, score, member);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			redisClose();
		}
		return result;
	}
	
	
	public Double zincrby(byte[] key ,double score,byte[] member){
		Double result = 0D;
		try {
			jedis.zincrby(key, score, member);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			redisClose();
		}
		return result;
	}
	
	
	
	//----------------------------------------------------------------------------------------List����------------------------------------------------------------------------------------------------------------------
	/**
	 * 
	 * @Description:��һ������ֵ���뵽�б�ͷ���� 
	 * 				��� key �����ڣ�һ�����б�ᱻ������ִ�� LPUSH ������ 
	 * 				�� key ���ڵ������б�����ʱ������һ������
	 * @author yang_hui 
	 * @Created 2016��7��18��
	 * @param key
	 * @param values
	 * @return �����б�ĳ���
	 */
	public long lpush(String key,String...values){
		Long result = 0l;
		try {
			result = jedis.lpush(key, values);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			redisClose();
		}
		return result;
	}
	
	/**
	 * 
	 * @Description:��һ������ֵ���뵽�б�ͷ���� 
	 * 				��� key �����ڣ�һ�����б�ᱻ������ִ�� LPUSH ������ 
	 * 				�� key ���ڵ������б�����ʱ������һ������
	 * @author yang_hui 
	 * @Created 2016��7��18��
	 * @param key
	 * @param values
	 * @return ���� key ��Ӧ���б�ĳ���
	 */
	public long lpush(byte[] key,byte[]...values){
		Long result = 0l;
		try {
			result = jedis.lpush(key, values);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			redisClose();
		}
		return result;
	}
	
	/**
	 * 
	 * @Description:��һ������ֵ���뵽�Ѵ��ڵ��б�ͷ�����б�����ʱ������Ч��
	 * @author yang_hui 
	 * @Created 2016��7��18��
	 * @param key
	 * @param values
	 * @return
	 */
	public long lpushx(String key,String...values){
		Long result = 0l;
		try {
			result = jedis.lpushx(key, values);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			redisClose();
		}
		return result;
	}
	
	/**
	 * 
	 * @Description:��һ������ֵ���뵽�Ѵ��ڵ��б�ͷ�����б�����ʱ������Ч��
	 * @author yang_hui 
	 * @Created 2016��7��18��
	 * @param key
	 * @param values
	 * @return
	 */
	public long lpushx(byte[] key,byte[]...values){
		Long result = 0l;
		try {
			result = jedis.lpushx(key, values);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			redisClose();
		}
		return result;
	}
	/**
	 * 
	 * @Description:��һ������ֵ���뵽�б�β���� 
	 * 				��� key �����ڣ�һ�����б�ᱻ������ִ�� RPUSH ������ 
	 * 				�� key ���ڵ������б�����ʱ������һ������
	 * @author yang_hui 
	 * @Created 2016��7��18��
	 * @param key
	 * @param values
	 * @return �����б�ĳ���
	 */
	public long rpush(String key,String...values){
		Long result = 0l;
		try {
			result = jedis.rpush(key, values);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			redisClose();
		}
		return result;
	}
	
	/**
	 * 
	 * @Description:��һ������ֵ���뵽�б�β���� 
	 * 				��� key �����ڣ�һ�����б�ᱻ������ִ�� RPUSH ������ 
	 * 				�� key ���ڵ������б�����ʱ������һ������
	 * @author yang_hui 
	 * @Created 2016��7��18��
	 * @param key
	 * @param values
	 * @return
	 */
	public long rpush(byte[] key,byte[]...values){
		Long result = 0l;
		try {
			result = jedis.rpush(key, values);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			redisClose();
		}
		return result;
	}
	
	/**
	 * 
	 * @Description:��һ������ֵ���뵽�Ѵ��ڵ��б�β�����б�����ʱ������Ч��
	 * @author yang_hui 
	 * @Created 2016��7��18��
	 * @param key
	 * @param values
	 * @return
	 */
	public long rpushx(String key,String...values){
		Long result = 0l;
		try {
			result = jedis.rpushx(key, values);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			redisClose();
		}
		return result;
	}
	
	/**
	 * 
	 * @Description:��һ������ֵ���뵽�Ѵ��ڵ��б�β�����б�����ʱ������Ч��
	 * @author yang_hui 
	 * @Created 2016��7��18��
	 * @param key
	 * @param values
	 * @return
	 */
	public long rpushx(byte[] key,byte[]...values){
		Long result = 0l;
		try {
			result = jedis.rpushx(key, values);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			redisClose();
		}
		return result;
	}
	
	/**
	 * 
	 * @Description:�����б���ָ�������ڵ�Ԫ�أ�������ƫ���� START �� END ָ���� 
	 * ���� 0 ��ʾ�б�ĵ�һ��Ԫ�أ� 1 ��ʾ�б�ĵڶ���Ԫ�أ��Դ����ơ� 
	 * ��Ҳ����ʹ�ø����±꣬�� -1 ��ʾ�б�����һ��Ԫ�أ� -2 ��ʾ�б�ĵ����ڶ���Ԫ�أ��Դ����ơ�
	 * @author yang_hui 
	 * @Created 2016��7��18��
	 * @param key
	 * @param start
	 * @param end
	 * @return ������ѡ�����ڵ��б�
	 */
	public List<String> lrang(String key,long start,long end){
		List<String> list = null;
		try {
			list = jedis.lrange(key, start, end);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			redisClose();
		}
		return list;
	}
	
	/**
	 * 
	 * @Description:�����б���ָ�������ڵ�Ԫ�أ�������ƫ���� START �� END ָ���� 
	 * ���� 0 ��ʾ�б�ĵ�һ��Ԫ�أ� 1 ��ʾ�б�ĵڶ���Ԫ�أ��Դ����ơ� 
	 * ��Ҳ����ʹ�ø����±꣬�� -1 ��ʾ�б�����һ��Ԫ�أ� -2 ��ʾ�б�ĵ����ڶ���Ԫ�أ��Դ����ơ�
	 * @author yang_hui 
	 * @Created 2016��7��18��
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public List<byte[]> lrang(byte[] key,long start,long end){
		List<byte[]> list = null;
		try {
			list = jedis.lrange(key, start, end);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			redisClose();
		}
		return list;
	}
	
	/**
	 * 
	 * @Description:��ȡ�б���
	 * @author yang_hui 
	 * @Created 2016��7��18��
	 * @param key
	 * @return �����б�ĳ���
	 */
	public long llen(String key){
		long result = 0l;
		try {
			result = jedis.llen(key);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			redisClose();
		}
		return result;
	}
	
	/**
	 * 
	 * @Description:��ȡ�б���
	 * @author yang_hui 
	 * @Created 2016��7��18��
	 * @param key
	 * @return
	 */
	public long llen(byte[] key){
		long result = 0l;
		try {
			result = jedis.llen(key);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			redisClose();
		}
		return result;
	}
	
	/**
	 * 
	 * @Description:ͨ��������ȡ�б��е�Ԫ�ء�
	 * ��Ҳ����ʹ�ø����±꣬�� -1 ��ʾ�б�����һ��Ԫ�أ� -2 ��ʾ�б�ĵ����ڶ���Ԫ�أ��Դ����ơ�
	 * �������б�Χ�ڷ��ظ��������ڵ�ֵ�����򷵻�null
	 * @author yang_hui 
	 * @Created 2016��7��18��
	 * @param key
	 * @param index
	 * @return
	 */
	public String lindex(String key,long index){
		String str = "";
		try {
			str = jedis.lindex(key, index);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			redisClose();
		}
		return str;
	}
	
	/**
	 * 
	 * @Description:ͨ��������ȡ�б��е�Ԫ�ء�
	 * ��Ҳ����ʹ�ø����±꣬�� -1 ��ʾ�б�����һ��Ԫ�أ� -2 ��ʾ�б�ĵ����ڶ���Ԫ�أ��Դ����ơ�
	 * @author yang_hui 
	 * @Created 2016��7��18��
	 * @param key
	 * @param index
	 * @return
	 */
	public byte[] lindex(byte[] key,long index){
		byte[] str = new byte[10];
		try {
			str = jedis.lindex(key, index);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			redisClose();
		}
		return str;
	}
	
	/**
	 * 
	 * @Description:ͨ������������Ԫ�ص�ֵ������������������Χ��
	 * 				���һ�����б���� LSET ʱ������һ������
	 * @author yang_hui 
	 * @Created 2016��7��18��
	 * @param key
	 * @param index ����
	 * @param value Ԫ�ص�ֵ
	 * @return �������б�Χ�ڣ����� OK,���򣬲����쳣�������ַ���ERROR��
	 */
	public String lset(String key,long index,String value){
		String str = "";
		try {
			str = jedis.lset(key, index, value);
		} catch (Exception e) {
			str = "ERROR";
			e.printStackTrace();
		}finally {
			redisClose();
		}
		return str;
	}
	/**
	 * 
	 * @Description:ͨ������������Ԫ�ص�ֵ�����������б�Χ�ڷ���OK,
	 * 				����������������Χ�����һ�����б���� LSET ʱ������һ������
	 * 				
	 * @author yang_hui 
	 * @Created 2016��7��18��
	 * @param key
	 * @param index
	 * @param value
	 * @return �������б�Χ�ڣ����� OK,���򣬲����쳣�������ַ���ERROR��
	 */
	public String lset(byte[] key,long index,byte[] value){
		String str = "";
		try {
			str = jedis.lset(key, index, value);
		} catch (Exception e) {
			str = "ERROR";
			e.printStackTrace();
		}finally {
			redisClose();
		}
		return str;
	}
	
	/**
	 * 
	 * @Description:���б�ֻ����ָ�������ڵ�Ԫ�أ�����ָ������֮�ڵ�Ԫ�ض�����ɾ����
					�±� 0 ��ʾ�б�ĵ�һ��Ԫ�أ��� 1 ��ʾ�б�ĵڶ���Ԫ�أ��Դ����ơ� 
					��Ҳ����ʹ�ø����±꣬�� -1 ��ʾ�б�����һ��Ԫ�أ� -2 ��ʾ�б�ĵ����ڶ���Ԫ�أ��Դ����ơ�
	 * @author yang_hui 
	 * @Created 2016��7��18��
	 * @param key
	 * @param start
	 * @param end
	 * @return �ɹ�����OK
	 */
	public String ltrim(String key,long start,long end){
		String str = null;
		try {
			str =  jedis.ltrim(key, start, end);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			redisClose();
		}
		return str;
	}
	/**
	 * 
	 * @Description:���б�ֻ����ָ�������ڵ�Ԫ�أ�����ָ������֮�ڵ�Ԫ�ض�����ɾ����
					�±� 0 ��ʾ�б�ĵ�һ��Ԫ�أ��� 1 ��ʾ�б�ĵڶ���Ԫ�أ��Դ����ơ� 
					��Ҳ����ʹ�ø����±꣬�� -1 ��ʾ�б�����һ��Ԫ�أ� -2 ��ʾ�б�ĵ����ڶ���Ԫ�أ��Դ����ơ�
	 * @author yang_hui 
	 * @Created 2016��7��18��
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public String ltrim(byte[] key,long start,long end){
		String str = null;
		try {
			str =  jedis.ltrim(key, start, end);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			redisClose();
		}
		return str;
	}
	
	/**
	 * 
	 * @Description:���б��Ԫ��ǰ�����Ԫ�ء� ��ָ��Ԫ�ز��������б���ʱ����ִ���κβ����� 
	 * 				���б�����ʱ������Ϊ���б���ִ���κβ����� ��� key �����б����ͣ�����һ������
	 * @author yang_hui 
	 * @Created 2016��7��18��
	 * @param key
	 * @param pivot
	 * @param value 
	 * @return �������ִ�гɹ������ز���������֮���б�ĳ��ȡ� ���û���ҵ�ָ��Ԫ�� ������ -1 �� ��� key �����ڻ�Ϊ���б����� 0 
	 */
	public long linsert_before(String key,String pivot,String value){
		Long result = 0l;
		try {
			result = jedis.linsert(key, LIST_POSITION.BEFORE, pivot, value);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			redisClose();
		}
		return result;
	}
	/**
	 * 
	 * @Description:���б��Ԫ�غ������Ԫ�ء� ��ָ��Ԫ�ز��������б���ʱ����ִ���κβ����� 
	 * 				���б�����ʱ������Ϊ���б���ִ���κβ����� ��� key �����б����ͣ�����һ������
	 * @author yang_hui 
	 * @Created 2016��7��18��
	 * @param key
	 * @param pivot
	 * @param value
	 * @return �������ִ�гɹ������ز���������֮���б�ĳ��ȡ� ���û���ҵ�ָ��Ԫ�� ������ -1 �� ��� key �����ڻ�Ϊ���б����� 0 
	 */
	public long linsert_after(String key,String pivot,String value){
		Long result = 0l;
		try {
			result = jedis.linsert(key, LIST_POSITION.AFTER, pivot, value);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			redisClose();
		}
		return result;
	}
	
	/**
	 * 
	 * @Description:�Ƴ��б�����һ��Ԫ�أ�������Ԫ����ӵ���һ���б�����
	 * @author yang_hui 
	 * @Created 2016��7��18��
	 * @param srckey
	 * @param dstkey
	 * @return ���������Ǹ�Ԫ��
	 */
	public String rpopLpush(String srckey,String dstkey){
		String str = "";
		try {
			str = jedis.rpoplpush(srckey, dstkey);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			redisClose();
		}
		return str;
	} 
	/**
	 * 
	 * @Description:�Ƴ��б�����һ��Ԫ�أ�������Ԫ����ӵ���һ���б�����
	 * @author yang_hui 
	 * @Created 2016��7��18��
	 * @param srckey
	 * @param dstkey
	 * @return ���������Ǹ�Ԫ��
	 */
	public byte[] rpopLpush(byte[] srckey,byte[] dstkey){
		byte[] str = new byte[10];
		try {
			str = jedis.rpoplpush(srckey, dstkey);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			redisClose();
		}
		return str;
	} 
	
	
	/**
	 * 
	 * @Description:����������Ϊ��������� key ��������һ���ǿ��б���ô���������ĵ�һ���ǿ��б��ͷԪ�أ�
	 * 				���ͱ�����Ԫ���������б������ key һ����ɽ������.
	 * 				�����ڶ������ key ʱ�� BLPOP ������ key �������е��Ⱥ�˳�����μ������б�
	 * 				(������Ϊ)������и��� key �������ڻ�������б���ô BLPOP ����������ӣ� 
	 * 				ֱ������һ���ͻ��˶Ը�������Щ key ������һ��ִ�� LPUSH ��RPUSH ����Ϊֹ��
	 * 				һ�����µ����ݳ���������һ���б����ô��������������״̬�����ҷ��� key �͵�����Ԫ��ֵ��
	 * 				�� BLPOP ��������ͻ�����������������һ������ĳ�ʱ���� timeout ��ʱ�� 
	 * 				��������ָ���� timeout ��û�г���һ�����ĳһ�ض� key �� push ������
	 * 				��ͻ��˻�������״̬���ҷ���һ�� null.
	 *				timeout ������ʾ����һ��ָ���������������������ֵ���� timeout Ϊ 0 �Ǳ�ʾ����ʱ�������ơ�
	 * @author yang_hui 
	 * @Created 2016��7��18��
	 * @param timeout ��ʱʱ��
	 * @param keys
	 * @return ���� key �������ڻ�������б�����һ�� null �� ���򣬷���һ����������Ԫ�ص��б�
	 * 		     ��һ��Ԫ���Ǳ�����Ԫ�������� key ���ڶ���Ԫ���Ǳ�����Ԫ�ص�ֵ��
	 */
	public List<String> blpop(int timeout,String...keys){
		List<String> list = null;
		try {
			list = jedis.blpop(timeout, keys);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			redisClose();
		}
		return list;
	}
	
	/**
	 * 
	 * @Description:����������Ϊ��������� key ��������һ���ǿ��б���ô���������ĵ�һ���ǿ��б��ͷԪ�أ�
	 * 				���ͱ�����Ԫ���������б������ key һ����ɽ������.
	 * 				�����ڶ������ key ʱ�� BLPOP ������ key �������е��Ⱥ�˳�����μ������б�
	 * 				(������Ϊ)������и��� key �������ڻ�������б���ô BLPOP ����������ӣ� 
	 * 				ֱ������һ���ͻ��˶Ը�������Щ key ������һ��ִ�� LPUSH ��RPUSH ����Ϊֹ��
	 * 				һ�����µ����ݳ���������һ���б����ô��������������״̬�����ҷ��� key �͵�����Ԫ��ֵ��
	 * 				�� BLPOP ��������ͻ�����������������һ������ĳ�ʱ���� timeout ��ʱ�� 
	 * 				��������ָ���� timeout ��û�г���һ�����ĳһ�ض� key �� push ������
	 * 				��ͻ��˻�������״̬���ҷ���һ�� null.
	 *				timeout ������ʾ����һ��ָ���������������������ֵ���� timeout Ϊ 0 �Ǳ�ʾ����ʱ�������ơ�
	 * @author yang_hui 
	 * @Created 2016��7��18��
	 * @param timeout ��ʱʱ��
	 * @param keys
	 * @return ���� key �������ڻ�������б�����һ�� null �� ���򣬷���һ����������Ԫ�ص��б�
	 * 		     ��һ��Ԫ���Ǳ�����Ԫ�������� key ���ڶ���Ԫ���Ǳ�����Ԫ�ص�ֵ��
	 */
	public List<byte[]> blpop(int timeout,byte[]...keys){
		List<byte[]> list = null;
		try {
			list = jedis.blpop(timeout, keys);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			redisClose();
		}
		return list;
	}
	/**
	 * 
	 * @Description:�Ƴ�����ȡ�б�����һ��Ԫ�أ� ����б�û��Ԫ�ػ������б�ֱ���ȴ���ʱ���ֿɵ���Ԫ��Ϊֹ
	 * @author yang_hui 
	 * @Created 2016��7��18��
	 * @param timeout
	 * @param keys
	 * @return ��ָ��ʱ����û���κ�Ԫ�ر��������򷵻�һ�� nil �͵ȴ�ʱ���� 
	 * 			��֮������һ����������Ԫ�ص��б���һ��Ԫ���Ǳ�����Ԫ�������� key ��
	 * 			�ڶ���Ԫ���Ǳ�����Ԫ�ص�ֵ
	 */
	public List<String> brpop(int timeout,String...keys){
		List<String> list = null;
		try {
			list = jedis.brpop(timeout, keys);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			redisClose();
		}
		return list;
	}
	
	/**
	 * 
	 * @Description:�Ƴ�����ȡ�б�����һ��Ԫ�أ� ����б�û��Ԫ�ػ������б�ֱ���ȴ���ʱ���ֿɵ���Ԫ��Ϊֹ
	 * @author yang_hui 
	 * @Created 2016��7��18��
	 * @param timeout
	 * @param keys
	 * @return ��ָ��ʱ����û���κ�Ԫ�ر��������򷵻�һ�� nil �͵ȴ�ʱ���� 
	 * 			��֮������һ����������Ԫ�ص��б���һ��Ԫ���Ǳ�����Ԫ�������� key ��
	 * 			�ڶ���Ԫ���Ǳ�����Ԫ�ص�ֵ
	 */
	public List<byte[]> brpop(int timeout,byte[]...keys){
		List<byte[]> list = null;
		try {
			list = jedis.brpop(timeout, keys);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			redisClose();
		}
		return list;
	}
	
	/**
	 * 
	 * @Description:���б��е���һ��ֵ����������Ԫ�ز��뵽����һ���б��в��������� 
	 * 				����б�û��Ԫ�ػ������б�ֱ���ȴ���ʱ���ֿɵ���Ԫ��Ϊֹ
	 * @author yang_hui 
	 * @Created 2016��7��18��
	 * @param key1
	 * @param key2
	 * @param timeout
	 * @return ������ָ��ʱ����û���κ�Ԫ�ر��������򷵻�һ��null��
	 * 		 ��֮�����ر�����Ԫ�ص�ֵ
	 */
	public String brpopLpush(String key1,String key2,int timeout){
		String str = "";
		try {
			str = jedis.brpoplpush(key1, key2, timeout);
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			redisClose();
		}
		return str;
	}
	/**
	 * 
	 * @Description:���б��е���һ��ֵ����������Ԫ�ز��뵽����һ���б��в��������� 
	 * 				����б�û��Ԫ�ػ������б�ֱ���ȴ���ʱ���ֿɵ���Ԫ��Ϊֹ
	 * @author yang_hui 
	 * @Created 2016��7��18��
	 * @param key1
	 * @param key2
	 * @param timeout
	 * @return ������ָ��ʱ����û���κ�Ԫ�ر��������򷵻�һ��null��
	 * 		 ��֮�����ر�����Ԫ�ص�ֵ
	 */
	public byte[] brpopLpush(byte[] source,byte[] destination,int timeout){
		byte[] str = new byte[10];
		try {
			str = jedis.brpoplpush(source, destination, timeout);
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			redisClose();
		}
		return str;
	}
	
	/**
	 * 
	 * @Description:�Ƴ��������б�ĵ�һ��Ԫ�ء�
	 * @author yang_hui 
	 * @Created 2016��7��18��
	 * @param key
	 * @return �����б�ĵ�һ��Ԫ��
	 */
	public String lpop(String key){
		String str = "";
		try {
			str = jedis.lpop(key);
		}catch (Exception e) {
			e.printStackTrace();
			redisClose();
		}finally {
			redisClose();
		}
		return str;
	}
	
	/**
	 * 
	 * @Description:�Ƴ��������б�ĵ�һ��Ԫ�ء�
	 * @author yang_hui 
	 * @Created 2016��7��18��
	 * @param key
	 * @return �����б�ĵ�һ��Ԫ��
	 */
	public byte[] lpop(byte[] key){
		byte[] str = new byte[10];
		try {
			str = jedis.lpop(key);
		}catch (Exception e) {
			e.printStackTrace();
			redisClose();
		}finally {
			redisClose();
		}
		return str;
	}
	/**
	 * 
	 * @Description:�Ƴ��������б�����һ��Ԫ�ء�
	 * @author yang_hui 
	 * @Created 2016��7��18��
	 * @param key
	 * @return �����б�����һ��Ԫ��
	 */
	public String rpop(String key){
		String str = "";
		try {
			str = jedis.rpop(key);
		}catch (Exception e) {
			e.printStackTrace();
			redisClose();
		}finally {
			redisClose();
		}
		return str;
	}
	
	/**
	 * 
	 * @Description:�Ƴ��������б�����һ����Ԫ�ء�
	 * @author yang_hui 
	 * @Created 2016��7��18��
	 * @param key
	 * @return �����б�����һ��Ԫ��
	 */
	public byte[] rpop(byte[] key){
		byte[] str = new byte[10];
		try {
			str = jedis.rpop(key);
		}catch (Exception e) {
			e.printStackTrace();
			redisClose();
		}finally {
			redisClose();
		}
		return str;
	}
	
	/**
	 * 
	 * @Description: ���ݲ��� COUNT ��ֵ���Ƴ��б�������� VALUE ��ȵ�Ԫ�ء�
	 * 				count > 0 : �ӱ�ͷ��ʼ���β�������Ƴ��� VALUE ��ȵ�Ԫ�أ�����Ϊ COUNT ��
					count < 0 : �ӱ�β��ʼ���ͷ�������Ƴ��� VALUE ��ȵ�Ԫ�أ�����Ϊ COUNT �ľ���ֵ��
					count = 0 : �Ƴ����������� VALUE ��ȵ�ֵ��
	 * @author yang_hui 
	 * @Created 2016��7��18��
	 * @param key
	 * @param count
	 * @param value
	 * @return ���ر��Ƴ������������ͣ�
	 */
	public long lrem(String key,long count,String value){
		long result = 0l;
		try {
			result = jedis.lrem(key, count, value);
		}catch (Exception e) {
			e.printStackTrace();
			redisClose();
		}finally {
			redisClose();
		}
		return result;
	}
	/**
	 * 
	 * @Description: ���ݲ��� COUNT ��ֵ���Ƴ��б�������� VALUE ��ȵ�Ԫ�ء�
	 * 				count > 0 : �ӱ�ͷ��ʼ���β�������Ƴ��� VALUE ��ȵ�Ԫ�أ�����Ϊ COUNT ��
					count < 0 : �ӱ�β��ʼ���ͷ�������Ƴ��� VALUE ��ȵ�Ԫ�أ�����Ϊ COUNT �ľ���ֵ��
					count = 0 : �Ƴ����������� VALUE ��ȵ�ֵ��
	 * @author yang_hui 
	 * @Created 2016��7��18��
	 * @param key
	 * @param count
	 * @param value
	 * @return
	 */
	public long lrem(byte[] key,long count,byte[] value){
		long result = 0l;
		try {
			result = jedis.lrem(key, count, value);
		}catch (Exception e) {
			e.printStackTrace();
			redisClose();
		}finally {
			redisClose();
		}
		return result;
	}
	
	//----------------------------------------------------------------------------------------Hash����------------------------------------------------------------------------------------------------------------------
	
	/**
	 * 
	 * @Description: ˢ�����ݿ�
	 * @author yanghui 
	 * @Created 2016��5��31��
	 */
	public void flushDB(){
		jedis.flushDB();
	}
	
	
	/**
	 * 
	 * @Description: �ͷ�����
	 * @author yanghui 
	 * @Created 2016��6��1��
	 */
	public void redisClose(){
	//	jedis.close();
	//	jedisPool.close();
		if(null!=jedisPool){
			jedisPool.close();
		}
	}
	

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
	

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getExpire() {
		return expire;
	}

	public void setExpire(int expire) {
		this.expire = expire;
	}
	

	public JedisPoolConfig getPoolConfig() {
		return poolConfig;
	}

	public void setPoolConfig(JedisPoolConfig poolConfig) {
		this.poolConfig = poolConfig;
	}

}
