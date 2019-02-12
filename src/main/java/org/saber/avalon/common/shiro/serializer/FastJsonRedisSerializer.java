/**   */
package org.saber.avalon.common.shiro.serializer;

import org.crazycake.shiro.exception.SerializationException;
import org.crazycake.shiro.serializer.RedisSerializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.support.config.FastJsonConfig;

/**   
 * <p>类名称: FastJsonRedisSerializer </p> 
 * <p>描述: fastjson序列化工具  </p>
 * <p>创建时间 : 2019年2月12日 下午4:05:03 </p>
 * @author lijunliang
 * @version 1.0
 * 
 */
public class FastJsonRedisSerializer<T> implements RedisSerializer<T>{

    private FastJsonConfig fastJsonConfig = new FastJsonConfig();
    private Class<T> type;
	/** (non-Javadoc)
	 * @see org.crazycake.shiro.serializer.RedisSerializer#serialize(java.lang.Object)
	 */
	@Override
	public byte[] serialize(T t) throws SerializationException {
        if (t == null) {
            return new byte[0];
        }
        try {
            return JSON.toJSONBytes(t, fastJsonConfig.getSerializeConfig(), fastJsonConfig.getSerializerFeatures());
        } catch (Exception ex) {
            throw new SerializationException("Could not serialize: " + ex.getMessage(), ex);
        }
	}

	/** (non-Javadoc)
	 * @see org.crazycake.shiro.serializer.RedisSerializer#deserialize(byte[])
	 */
	@SuppressWarnings("unchecked")
	@Override
	public T deserialize(byte[] bytes) throws SerializationException {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        try {
            return (T) JSON.parseObject(bytes, type, fastJsonConfig.getFeatures());
        } catch (Exception ex) {
            throw new SerializationException("Could not deserialize: " + ex.getMessage(), ex);
        }
	}

    public FastJsonRedisSerializer(Class<T> type) {
        this.type = type;
    }

    public FastJsonConfig getFastJsonConfig() {
        return fastJsonConfig;
    }

    public void setFastJsonConfig(FastJsonConfig fastJsonConfig) {
        this.fastJsonConfig = fastJsonConfig;
    }

}
