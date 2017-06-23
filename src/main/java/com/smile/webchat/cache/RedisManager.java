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
 * 类名： RedisManager
 * 描述：
 * 作者： 杨辉
 * 创建时间：2016年12月24日-上午10:54:41
 *
 */
public class RedisManager {
	/**
	 * redis服务器主机
	 */
	private String host;
	/**
	 * 端口
	 */
	private int port;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 过期时间
	 */
	private int expire = 0;
	/**
	 * 超时时间
	 */
	private int timeout = 10000;
	/**
	 * 连接池配置
	 */
	private JedisPoolConfig poolConfig;
	
	private Jedis jedis;//非切片客户端连接
	private JedisPool jedisPool = null; //非切片连接池
	private ShardedJedis shardedJedis;//切片客户端连接
	private ShardedJedisPool shardedJedisPool;//切片连接池




	public void initalPool(){
		if (null == host || 0 == port) {
            System.out.println("请初始化redis配置文件");
            throw new NullPointerException("找不到redis配置");
        }
		if(null==jedisPool){
			jedisPool = new JedisPool(poolConfig, host, port, timeout, password);
		//	jedisPool = new JedisPool(poolConfig, host, port, timeout);
			jedis = jedisPool.getResource();
		}
	}
	
	//初始化切片池(集群)
	private void initalShardedPool() {
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxIdle(5);
		config.setMaxWaitMillis(10001);
		config.setTestOnBorrow(false);
		//slave 连接
		List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
		shards.add(new JedisShardInfo("127.0.0.1", 6379, "master"));
		shards.add(new JedisShardInfo("192.168.41.217", 6379, "master"));
		
		shardedJedisPool = new ShardedJedisPool(config, shards);
		shardedJedis = shardedJedisPool.getResource();
	}
	
	
	//--------------------------------------------------------------------------------------KEY 操作--------------------------------------------------------------------------------------------------------------------------
	
	/**
	 * 
	 * @Description: 判断是否存在某个 key
	 * @author yanghui 
	 * @Created 2016年5月31日
	 * @param key
	 * @return true  存在  false  不存在
	 */
	public boolean exists(String key){
		return jedis.exists(key);
	}
	
	public boolean exists(byte[] key){
		return jedis.exists(key);
	}
	
	/**
	 * 
	 * @Description: 获取当前 key 的 value
	 * @author yanghui 
	 * @Created 2016年6月15日
	 * @param key
	 * @return 返回 key 对应的 value ,不存在 key 时返回 null
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
	 * @Description:获取多个 key 的 value 值
	 * @author yanghui 
	 * @Created 2016年6月15日
	 * @param keys
	 * @return 存在 key 返回 value,不存在 key 返回 null
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
	 * @Description:获取多个 key 的 value 值
	 * @author yanghui 
	 * @Created 2016年6月15日
	 * @param keys
	 * @return 存在 key 返回 value,不存在 key 返回 null
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
	 * @Description: 获取当前 key 的 value
	 * @author yanghui 
	 * @Created 2016年6月15日
	 * @param key
	 * @return 返回 key 对应的 value ,不存在 key 时返回 null
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
	 * @Description: 删除指定的key:存在则删除，不存在则忽略
	 * @author yanghui 
	 * @Created 2016年6月1日
	 * @param key
	 * @return 存在key返回1，否则返回0
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
	 * @Description: 删除多个key的值
	 * @author yanghui 
	 * @Created 2016年6月15日
	 * @param keys
	 * @return 存在key返回1，否则返回0
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
	 * @Description:序列化给定 key ，并返回被序列化的值。key不存在返回null
	 * @author yanghui 
	 * @Created 2016年6月16日
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
	 * @Description:序列化给定 key ，并返回被序列化的值。key不存在返回null
	 * @author yanghui 
	 * @Created 2016年6月16日
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
	 * 移除给定 key 的过期时间，使得 key 永不过期。
	 * @Description:
	 * @author yanghui 
	 * @Created 2016年6月16日
	 * @param key
	 * @return 1表示key的过期时间被移除  0 表示key不存在或没有过期时间
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
	 * 移除给定 key 的过期时间，使得 key 永不过期。
	 * @Description:
	 * @author yanghui 
	 * @Created 2016年6月16日
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
	 * @Description:以毫秒为单位返回 key 的剩余过期时间:
	 * 				当 key 不存在，或者 key 没有设置剩余生存时间时，命令都返回 -1, 
	 * 				否则，以返回 key 的剩余生存时间
	 * @author yanghui 
	 * @Created 2016年6月16日
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
	 * @Description:以毫秒为单位返回 key 的剩余过期时间:
	 * 				当 key 不存在，或者 key 没有设置剩余生存时间时，命令都返回 -1, 
	 * 				否则，以返回 key 的剩余生存时间
	 * @author yanghui 
	 * @Created 2016年6月16日
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
	 * @Description:以秒为单位返回 key 的剩余过期时间:
	 * 				当 key 不存在，或者 key 没有设置剩余生存时间时，命令都返回 -1, 
	 * 				否则，以返回 key 的剩余生存时间
	 * @author yanghui 
	 * @Created 2016年6月16日
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
	 * @Description:以秒为单位返回 key 的剩余过期时间:当 key 不存在，或者 key 没有设置剩余生存时间时，命令都返回 -1, 否则，以返回 key 的剩余生存时以毫秒为单位返回 key 的剩余过期时间:
	 * 				当 key 不存在，或者 key 没有设置剩余生存时间时，命令都返回 -1, 
	 * 				否则，以返回 key 的剩余生存时间
	 * @author yanghui 
	 * @Created 2016年6月16日
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
	 * @Description: 命令从当前数据库中随机返回一个 key : 当数据库不为空时，返回一个 key 。
	 *  当数据库为空时，返回 nil
	 * @author yanghui 
	 * @Created 2016年6月16日
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
	 * @Description: 命令从当前数据库中随机返回一个 key : 当数据库不为空时，返回一个 key 。 当数据库为空时，返回 nil
	 * @author yanghui 
	 * @Created 2016年6月16日
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
	 * @Description:修改 key 的名称 ： 
	 * 	改名成功时提示 OK ，失败时候返回一个错误。
	 *	当 OLD_KEY_NAME 和 NEW_KEY_NAME 相同，或者 OLD_KEY_NAME 不存在时，返回一个错误。 
	 *	当 NEW_KEY_NAME 已经存在时， RENAME 命令将覆盖旧值。
	 * @author yanghui 
	 * @Created 2016年6月16日
	 * @param oldkey
	 * @param newkey
	 * @return 修改成功返回 OK，修改失败返回 ERROR
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
	 * @Description:在新的 key 不存在时修改 key 的名称 : 
	 * 修改成功时，返回 1 。 如果 NEW_KEY_NAME 已经存在，返回 0 。
	 * 当oldKey不存在时，抛出异常，并返回0
	 * @author yanghui 
	 * @Created 2016年6月16日
	 * @param oldkey
	 * @param newkey
	 * @return 修改成功返回1  修改失败返回2
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
	 * @Description: 返回 key 的数据类型，数据类型有：
	 * none (key不存在)string (字符串)list (列表)set (集合)zset (有序集)hash (哈希表)
	 * @author yanghui 
	 * @Created 2016年6月16日
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
	 * @Description: 返回 key 的数据类型，数据类型有：none (key不存在)string (字符串)list (列表)set (集合)zset (有序集)hash (哈希表)
	 * @author yanghui 
	 * @Created 2016年6月16日
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
	 * @Description:设置key的过期时间
	 * @author yang_hui 
	 * @Created 2016年7月19日
	 * @param key
	 * @param time 以秒为单位
	 * @return 1 表示超时被设置  0表示key不存在或不能被设置
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
	 * @Description:返回所有匹配从key
	 * @author yang_hui 
	 * @Created 2016年7月19日
	 * @param pattern 匹配规则
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
	
	//--------------------------------------------------------------------------------------字符串操作--------------------------------------------------------------------------------------------------------------------------
	
	/**
	 * 
	 * @Description: 往相应的key中加入类型[String]的value，该方法会直接覆盖key原来的值
	 * @author yanghui 
	 * @Created 2016年5月31日
	 * @param key
	 * @param value
	 * @param expire  有效期
	 * @return OK 成功
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
	 * @Description:往相应的key中加入类型[byte] 的 value，该方法会直接覆盖key原来的值
	 * @author yanghui 
	 * @Created 2016年6月15日
	 * @param key
	 * @param value
	 * @param expire 有效期
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
	 * @Description: 新增键值对时防止覆盖原值
	 * @author yanghui 
	 * @Created 2016年6月15日
	 * @param key
	 * @param value
	 * @param expire
	 * @return key 不存在则新增成功返回 1，key存在则不新增，返回0
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
	 * @Description: 新增键值对时防止覆盖原值
	 * @author yanghui 
	 * @Created 2016年6月15日
	 * @param key
	 * @param value
	 * @param expire
	 * @return key 不存在则新增成功返回 1，key存在则不新增，返回0
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
	 * @Description: 同时新增、修改多个键值对
	 * @author yanghui 
	 * @Created 2016年6月15日
	 * @param keysvalues 格式：("key201","value201","key202","value202")
	 * @return OK 成功
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
	 * @Description:同时新增多个键值对并且防止覆盖原值：当任意一个key存在则新增失败（没有的key也不会被添加）返回 0，只有当所有key都不存在时，则新增成功，返回 1
	 * @author yanghui 
	 * @Created 2016年6月15日
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
	 * @Description: 向当前 Key 的 value 中追加内容。如果key原来的value为hello ,追加的值为world,追加后的内容为helloworld
	 * @author yanghui 
	 * @Created 2016年6月1日
	 * @param key
	 * @param val
	 * @return 返回该 key 的长度
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
	 * @Description:获取存储在指定 key 对应值字符串的子字符串。字符串的截取范围由 start 和 end 两个偏移量决定(包括 start 和 end 在内)。
	 * @author yanghui 
	 * @Created 2016年6月16日
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
	 * @Description:获取存储在指定 key 对应值字符串的子字符串。字符串的截取范围由 start 和 end 两个偏移量决定(包括 start 和 end 在内)。
	 * @author yanghui 
	 * @Created 2016年6月16日
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
	 * @Description:设置指定 key 的值，并返回 key 旧的值。
	 * @author yanghui 
	 * @Created 2016年6月16日
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
	 * @Description:设置指定 key 的值，并返回 key 旧的值。
	 * @author yanghui 
	 * @Created 2016年6月16日
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
	 * @Description:为指定的 key 设置值及其过期时间。如果 key 已经存在， SETEX 命令将会替换旧的值。
	 * @author yanghui 
	 * @Created 2016年6月16日
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
	 * @Description:用指定的字符串覆盖给定 key 所储存的字符串值，覆盖的位置从偏移量 offset 开始。
	 * @author yanghui 
	 * @Created 2016年6月16日
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
	 * @Description:获取指定 key 所储存的字符串值的长度。当 key 储存的不是字符串值时，返回一个错误。 字符串值的长度。 当 key 不存在时，返回 0。
	 * @author yanghui 
	 * @Created 2016年6月16日
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
	 * @Description: 将 key 中储存的数字加上指定的增量值。如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 INCRBY 命令。
					如果值包含错误的类型，或字符串类型的值不能表示为数字，那么返回一个错误。	本操作的值限制在 64 位(bit)有符号数字表示之内。
	 * @author yanghui 
	 * @Created 2016年6月16日
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
	 * @Description: 将 key 中储存的数字减去指定的增量值。如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 INCRBY 命令。
					如果值包含错误的类型，或字符串类型的值不能表示为数字，那么返回一个错误。	本操作的值限制在 64 位(bit)有符号数字表示之内。
	 * @author yanghui 
	 * @Created 2016年6月16日
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
	 * @Description:为 key 中所储存的值加上指定的浮点数增量值。如果 key 不存在，那么 INCRBYFLOAT 会先将 key 的值设为 0 ，再执行加法操作。
	 * @author yanghui 
	 * @Created 2016年6月16日
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
	
	//----------------------------------------------------------------------------------------Set(无序集合操作)------------------------------------------------------------------------------------------------------------------
	
	/**
	 * 
	 * @Description:向 set集合中添加单个元素：元素已存在返回0，否则返回1
	 * @author yanghui 
	 * @Created 2016年6月15日
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
	 * @Description:向 set集合中添加单个或多个元素：任意一个元素不存在，则添加成功返回1，元素都已存在则返回0
	 * @author yanghui 
	 * @Created 2016年6月15日
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
	 * @Description:向 set集合中添加单个元素:添加成功返回1
	 * @author yanghui 
	 * @Created 2016年6月15日
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
	 * @Description:向 set集合中添加单个或多个元素
	 * @author yanghui 
	 * @Created 2016年6月15日
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
	 * @Description:返回 key 对应的集合中的元素
	 * @author yanghui 
	 * @Created 2016年6月15日
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
	 * @Description:返回 key 对应的集合中的元素
	 * @author yanghui 
	 * @Created 2016年6月15日
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
	 * @Description:删除单个或多个元素：当元素都不存在于集合中返回0，任意一个元素存在于集合中返回1
	 * @author yanghui 
	 * @Created 2016年6月15日
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
	 * @Description:删除单个或多个元素：当元素都不存在于集合中返回0，任意一个元素存在于集合中返回1
	 * @author yanghui 
	 * @Created 2016年6月15日
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
	 * @Description:判断元素是否存在于集合中
	 * @author yanghui 
	 * @Created 2016年6月15日
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
	 * @Description:判断元素是否存在于集合中
	 * @author yanghui 
	 * @Created 2016年6月15日
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
	 * @Description:多个集合中元素的交集
	 * @author yanghui 
	 * @Created 2016年6月15日
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
	 * @Description:多个集合中元素的并集
	 * @author yanghui 
	 * @Created 2016年6月15日
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
	 * @Description:多个集合中元素的差集
	 * @author yanghui 
	 * @Created 2016年6月15日
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
	 * @Description:多个集合中元素的交集
	 * @author yanghui 
	 * @Created 2016年6月15日
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
	 * @Description:多个集合中元素的并集
	 * @author yanghui 
	 * @Created 2016年6月15日
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
	 * @Description:多个集合中元素的差集
	 * @author yanghui 
	 * @Created 2016年6月15日
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
	 * @Description: 统计集合中元素的数量
	 * @author yanghui 
	 * @Created 2016年6月15日
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
	
	//----------------------------------------------------------------------------------------SortedSet(有序集合操作)------------------------------------------------------------------------------------------------------
	/**
	 * 
	 * @Description:
	 * 				用于将一个及其分数值加入到有序集当中。
					如果某个成员已经是有序集的成员，那么更新这个成员的分数值，并通过重新插入这个成员元素，来保证该成员在正确的位置上。
					分数值可以是整数值或双精度浮点数。
					如果有序集合 key 不存在，则创建一个空的有序集并执行 ZADD 操作。
					当 key 存在但不是有序集类型时，返回一个错误。
	 * @author yanghui 
	 * @Created 2016年6月15日
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
	 * 				用于将一个或多个成员元素及其分数值加入到有序集当中。
					如果某个成员已经是有序集的成员，那么更新这个成员的分数值，并通过重新插入这个成员元素，来保证该成员在正确的位置上。
					分数值可以是整数值或双精度浮点数。
					如果有序集合 key 不存在，则创建一个空的有序集并执行 ZADD 操作。
					当 key 存在但不是有序集类型时，返回一个错误。
	 * @author yanghui 
	 * @Created 2016年6月15日
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
	 *  返回有序集合中指定区间内的成员。
	 *	其中成员的位置按分数值递增(从小到大)来排序。
	 *	具有相同分数值的成员按字典序(lexicographical order )来排列。
	 *	如果你需要成员按值递减(从大到小)来排列，请使用 ZREVRANGE 命令。
	 *	下标参数 start 和 stop 都以 0 为底，也就是说，以 0 表示有序集第一个成员，以 1 表示有序集第二个成员，以此类推。
	 *	你也可以使用负数下标，以 -1 表示最后一个成员， -2 表示倒数第二个成员，以此类推。
	 * @author yanghui 
	 * @Created 2016年6月15日
	 * @param key
	 * @param start 开始下标
	 * @param end 结束下标
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
	 *  返回有序集合中指定区间内的成员。
	 *	其中成员的位置按分数值递增(从大到小)来排序。
	 *	具有相同分数值的成员按字典序(lexicographical order )来排列。
	 *	下标参数 start 和 stop 都以 0 为底，也就是说，以 0 表示有序集第一个成员，以 1 表示有序集第二个成员，以此类推。
	 *	你也可以使用负数下标，以 -1 表示最后一个成员， -2 表示倒数第二个成员，以此类推。
	 * @author yanghui 
	 * @Created 2016年6月15日
	 * @param key
	 * @param start 开始下标
	 * @param end 结束下标
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
	 * @Description:删除一个或多个元素
	 * @author yanghui 
	 * @Created 2016年6月15日
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
	 * @Description: 查看元素的权重
	 * @author yanghui 
	 * @Created 2016年6月15日
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
	 * @Description: 查看元素的权重
	 * @author yanghui 
	 * @Created 2016年6月15日
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
	 * @Description: 统计集合中元素的数量
	 * @author yanghui 
	 * @Created 2016年6月15日
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
	 * @Description: 统计集合中元素的数量
	 * @author yanghui 
	 * @Created 2016年6月15日
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
	 * @Description: 统计集合中权重在min(包含) 和 max(包含) 之间元素的数量
	 * @author yanghui 
	 * @Created 2016年6月15日
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
	 * @Description: 统计集合中权重在min 和 max 之间元素的数量
	 * @author yanghui 
	 * @Created 2016年6月15日
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
	 * @Description:对有序集合中指定成员的分数加上增量
	 * 				可以通过传递一个负数值 increment ，让分数减去相应的值，比如 ZINCRBY key -5 member ，就是让 member 的 score 值减去 5 
	 * 				当 key 不存在，或分数不是 key 的成员时  等同于 ZADD key 
	 * @author yang_hui 
	 * @Created 2016年7月15日
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
	
	
	
	//----------------------------------------------------------------------------------------List操作------------------------------------------------------------------------------------------------------------------
	/**
	 * 
	 * @Description:将一个或多个值插入到列表头部。 
	 * 				如果 key 不存在，一个空列表会被创建并执行 LPUSH 操作。 
	 * 				当 key 存在但不是列表类型时，返回一个错误
	 * @author yang_hui 
	 * @Created 2016年7月18日
	 * @param key
	 * @param values
	 * @return 返回列表的长度
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
	 * @Description:将一个或多个值插入到列表头部。 
	 * 				如果 key 不存在，一个空列表会被创建并执行 LPUSH 操作。 
	 * 				当 key 存在但不是列表类型时，返回一个错误
	 * @author yang_hui 
	 * @Created 2016年7月18日
	 * @param key
	 * @param values
	 * @return 返回 key 对应的列表的长度
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
	 * @Description:将一个或多个值插入到已存在的列表头部，列表不存在时操作无效。
	 * @author yang_hui 
	 * @Created 2016年7月18日
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
	 * @Description:将一个或多个值插入到已存在的列表头部，列表不存在时操作无效。
	 * @author yang_hui 
	 * @Created 2016年7月18日
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
	 * @Description:将一个或多个值插入到列表尾部。 
	 * 				如果 key 不存在，一个空列表会被创建并执行 RPUSH 操作。 
	 * 				当 key 存在但不是列表类型时，返回一个错误
	 * @author yang_hui 
	 * @Created 2016年7月18日
	 * @param key
	 * @param values
	 * @return 返回列表的长度
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
	 * @Description:将一个或多个值插入到列表尾部。 
	 * 				如果 key 不存在，一个空列表会被创建并执行 RPUSH 操作。 
	 * 				当 key 存在但不是列表类型时，返回一个错误
	 * @author yang_hui 
	 * @Created 2016年7月18日
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
	 * @Description:将一个或多个值插入到已存在的列表尾部，列表不存在时操作无效。
	 * @author yang_hui 
	 * @Created 2016年7月18日
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
	 * @Description:将一个或多个值插入到已存在的列表尾部，列表不存在时操作无效。
	 * @author yang_hui 
	 * @Created 2016年7月18日
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
	 * @Description:返回列表中指定区间内的元素，区间以偏移量 START 和 END 指定。 
	 * 其中 0 表示列表的第一个元素， 1 表示列表的第二个元素，以此类推。 
	 * 你也可以使用负数下标，以 -1 表示列表的最后一个元素， -2 表示列表的倒数第二个元素，以此类推。
	 * @author yang_hui 
	 * @Created 2016年7月18日
	 * @param key
	 * @param start
	 * @param end
	 * @return 返回所选区间内的列表
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
	 * @Description:返回列表中指定区间内的元素，区间以偏移量 START 和 END 指定。 
	 * 其中 0 表示列表的第一个元素， 1 表示列表的第二个元素，以此类推。 
	 * 你也可以使用负数下标，以 -1 表示列表的最后一个元素， -2 表示列表的倒数第二个元素，以此类推。
	 * @author yang_hui 
	 * @Created 2016年7月18日
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
	 * @Description:获取列表长度
	 * @author yang_hui 
	 * @Created 2016年7月18日
	 * @param key
	 * @return 返回列表的长度
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
	 * @Description:获取列表长度
	 * @author yang_hui 
	 * @Created 2016年7月18日
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
	 * @Description:通过索引获取列表中的元素。
	 * 你也可以使用负数下标，以 -1 表示列表的最后一个元素， -2 表示列表的倒数第二个元素，以此类推。
	 * 索引在列表范围内返回该索引对于的值，否则返回null
	 * @author yang_hui 
	 * @Created 2016年7月18日
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
	 * @Description:通过索引获取列表中的元素。
	 * 你也可以使用负数下标，以 -1 表示列表的最后一个元素， -2 表示列表的倒数第二个元素，以此类推。
	 * @author yang_hui 
	 * @Created 2016年7月18日
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
	 * @Description:通过索引来设置元素的值。当索引参数超出范围，
	 * 				或对一个空列表进行 LSET 时，返回一个错误。
	 * @author yang_hui 
	 * @Created 2016年7月18日
	 * @param key
	 * @param index 索引
	 * @param value 元素的值
	 * @return 索引在列表范围内，返回 OK,否则，捕获异常并返回字符串ERROR。
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
	 * @Description:通过索引来设置元素的值。当索引在列表范围内返回OK,
	 * 				当索引参数超出范围，或对一个空列表进行 LSET 时，返回一个错误。
	 * 				
	 * @author yang_hui 
	 * @Created 2016年7月18日
	 * @param key
	 * @param index
	 * @param value
	 * @return 索引在列表范围内，返回 OK,否则，捕获异常并返回字符串ERROR。
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
	 * @Description:让列表只保留指定区间内的元素，不在指定区间之内的元素都将被删除。
					下标 0 表示列表的第一个元素，以 1 表示列表的第二个元素，以此类推。 
					你也可以使用负数下标，以 -1 表示列表的最后一个元素， -2 表示列表的倒数第二个元素，以此类推。
	 * @author yang_hui 
	 * @Created 2016年7月18日
	 * @param key
	 * @param start
	 * @param end
	 * @return 成功返回OK
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
	 * @Description:让列表只保留指定区间内的元素，不在指定区间之内的元素都将被删除。
					下标 0 表示列表的第一个元素，以 1 表示列表的第二个元素，以此类推。 
					你也可以使用负数下标，以 -1 表示列表的最后一个元素， -2 表示列表的倒数第二个元素，以此类推。
	 * @author yang_hui 
	 * @Created 2016年7月18日
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
	 * @Description:在列表的元素前面插入元素。 当指定元素不存在于列表中时，不执行任何操作。 
	 * 				当列表不存在时，被视为空列表，不执行任何操作。 如果 key 不是列表类型，返回一个错误。
	 * @author yang_hui 
	 * @Created 2016年7月18日
	 * @param key
	 * @param pivot
	 * @param value 
	 * @return 如果命令执行成功，返回插入操作完成之后，列表的长度。 如果没有找到指定元素 ，返回 -1 。 如果 key 不存在或为空列表，返回 0 
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
	 * @Description:在列表的元素后面插入元素。 当指定元素不存在于列表中时，不执行任何操作。 
	 * 				当列表不存在时，被视为空列表，不执行任何操作。 如果 key 不是列表类型，返回一个错误。
	 * @author yang_hui 
	 * @Created 2016年7月18日
	 * @param key
	 * @param pivot
	 * @param value
	 * @return 如果命令执行成功，返回插入操作完成之后，列表的长度。 如果没有找到指定元素 ，返回 -1 。 如果 key 不存在或为空列表，返回 0 
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
	 * @Description:移除列表的最后一个元素，并将该元素添加到另一个列表并返回
	 * @author yang_hui 
	 * @Created 2016年7月18日
	 * @param srckey
	 * @param dstkey
	 * @return 被弹出的那个元素
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
	 * @Description:移除列表的最后一个元素，并将该元素添加到另一个列表并返回
	 * @author yang_hui 
	 * @Created 2016年7月18日
	 * @param srckey
	 * @param dstkey
	 * @return 被弹出的那个元素
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
	 * @Description:（非阻塞行为）如果给定 key 内至少有一个非空列表，那么弹出遇到的第一个非空列表的头元素，
	 * 				并和被弹出元素所属的列表的名字 key 一起，组成结果返回.
	 * 				当存在多个给定 key 时， BLPOP 按给定 key 参数排列的先后顺序，依次检查各个列表。
	 * 				(阻塞行为)如果所有给定 key 都不存在或包含空列表，那么 BLPOP 命令将阻塞连接， 
	 * 				直到有另一个客户端对给定的这些 key 的任意一个执行 LPUSH 或RPUSH 命令为止。
	 * 				一旦有新的数据出现在其中一个列表里，那么这个命令会解除阻塞状态，并且返回 key 和弹出的元素值。
	 * 				当 BLPOP 命令引起客户端阻塞并且设置了一个非零的超时参数 timeout 的时候， 
	 * 				若经过了指定的 timeout 仍没有出现一个针对某一特定 key 的 push 操作，
	 * 				则客户端会解除阻塞状态并且返回一个 null.
	 *				timeout 参数表示的是一个指定阻塞的最大秒数的整型值。当 timeout 为 0 是表示阻塞时间无限制。
	 * @author yang_hui 
	 * @Created 2016年7月18日
	 * @param timeout 超时时间
	 * @param keys
	 * @return 给定 key 都不存在或包含空列表，返回一个 null 。 否则，返回一个含有两个元素的列表，
	 * 		     第一个元素是被弹出元素所属的 key ，第二个元素是被弹出元素的值。
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
	 * @Description:（非阻塞行为）如果给定 key 内至少有一个非空列表，那么弹出遇到的第一个非空列表的头元素，
	 * 				并和被弹出元素所属的列表的名字 key 一起，组成结果返回.
	 * 				当存在多个给定 key 时， BLPOP 按给定 key 参数排列的先后顺序，依次检查各个列表。
	 * 				(阻塞行为)如果所有给定 key 都不存在或包含空列表，那么 BLPOP 命令将阻塞连接， 
	 * 				直到有另一个客户端对给定的这些 key 的任意一个执行 LPUSH 或RPUSH 命令为止。
	 * 				一旦有新的数据出现在其中一个列表里，那么这个命令会解除阻塞状态，并且返回 key 和弹出的元素值。
	 * 				当 BLPOP 命令引起客户端阻塞并且设置了一个非零的超时参数 timeout 的时候， 
	 * 				若经过了指定的 timeout 仍没有出现一个针对某一特定 key 的 push 操作，
	 * 				则客户端会解除阻塞状态并且返回一个 null.
	 *				timeout 参数表示的是一个指定阻塞的最大秒数的整型值。当 timeout 为 0 是表示阻塞时间无限制。
	 * @author yang_hui 
	 * @Created 2016年7月18日
	 * @param timeout 超时时间
	 * @param keys
	 * @return 给定 key 都不存在或包含空列表，返回一个 null 。 否则，返回一个含有两个元素的列表，
	 * 		     第一个元素是被弹出元素所属的 key ，第二个元素是被弹出元素的值。
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
	 * @Description:移出并获取列表的最后一个元素， 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止
	 * @author yang_hui 
	 * @Created 2016年7月18日
	 * @param timeout
	 * @param keys
	 * @return 在指定时间内没有任何元素被弹出，则返回一个 nil 和等待时长。 
	 * 			反之，返回一个含有两个元素的列表，第一个元素是被弹出元素所属的 key ，
	 * 			第二个元素是被弹出元素的值
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
	 * @Description:移出并获取列表的最后一个元素， 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止
	 * @author yang_hui 
	 * @Created 2016年7月18日
	 * @param timeout
	 * @param keys
	 * @return 在指定时间内没有任何元素被弹出，则返回一个 nil 和等待时长。 
	 * 			反之，返回一个含有两个元素的列表，第一个元素是被弹出元素所属的 key ，
	 * 			第二个元素是被弹出元素的值
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
	 * @Description:从列表中弹出一个值，将弹出的元素插入到另外一个列表中并返回它； 
	 * 				如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止
	 * @author yang_hui 
	 * @Created 2016年7月18日
	 * @param key1
	 * @param key2
	 * @param timeout
	 * @return 假如在指定时间内没有任何元素被弹出，则返回一个null。
	 * 		 反之，返回被弹出元素的值
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
	 * @Description:从列表中弹出一个值，将弹出的元素插入到另外一个列表中并返回它； 
	 * 				如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止
	 * @author yang_hui 
	 * @Created 2016年7月18日
	 * @param key1
	 * @param key2
	 * @param timeout
	 * @return 假如在指定时间内没有任何元素被弹出，则返回一个null。
	 * 		 反之，返回被弹出元素的值
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
	 * @Description:移除并返回列表的第一个元素。
	 * @author yang_hui 
	 * @Created 2016年7月18日
	 * @param key
	 * @return 返回列表的第一个元素
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
	 * @Description:移除并返回列表的第一个元素。
	 * @author yang_hui 
	 * @Created 2016年7月18日
	 * @param key
	 * @return 返回列表的第一个元素
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
	 * @Description:移除并返回列表的最后一个元素。
	 * @author yang_hui 
	 * @Created 2016年7月18日
	 * @param key
	 * @return 返回列表的最后一个元素
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
	 * @Description:移除并返回列表的最后一个个元素。
	 * @author yang_hui 
	 * @Created 2016年7月18日
	 * @param key
	 * @return 返回列表的最后一个元素
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
	 * @Description: 根据参数 COUNT 的值，移除列表中与参数 VALUE 相等的元素。
	 * 				count > 0 : 从表头开始向表尾搜索，移除与 VALUE 相等的元素，数量为 COUNT 。
					count < 0 : 从表尾开始向表头搜索，移除与 VALUE 相等的元素，数量为 COUNT 的绝对值。
					count = 0 : 移除表中所有与 VALUE 相等的值。
	 * @author yang_hui 
	 * @Created 2016年7月18日
	 * @param key
	 * @param count
	 * @param value
	 * @return 返回被移除的数量（整型）
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
	 * @Description: 根据参数 COUNT 的值，移除列表中与参数 VALUE 相等的元素。
	 * 				count > 0 : 从表头开始向表尾搜索，移除与 VALUE 相等的元素，数量为 COUNT 。
					count < 0 : 从表尾开始向表头搜索，移除与 VALUE 相等的元素，数量为 COUNT 的绝对值。
					count = 0 : 移除表中所有与 VALUE 相等的值。
	 * @author yang_hui 
	 * @Created 2016年7月18日
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
	
	//----------------------------------------------------------------------------------------Hash操作------------------------------------------------------------------------------------------------------------------
	
	/**
	 * 
	 * @Description: 刷新数据库
	 * @author yanghui 
	 * @Created 2016年5月31日
	 */
	public void flushDB(){
		jedis.flushDB();
	}
	
	
	/**
	 * 
	 * @Description: 释放连接
	 * @author yanghui 
	 * @Created 2016年6月1日
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
