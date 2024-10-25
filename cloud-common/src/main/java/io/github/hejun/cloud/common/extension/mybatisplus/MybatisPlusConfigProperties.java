package io.github.hejun.cloud.common.extension.mybatisplus;

import com.baomidou.mybatisplus.annotation.DbType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * MybatisPlus 配置类
 *
 * @author HeJun
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "cloud.common.mybatis-plus")
public class MybatisPlusConfigProperties {

	private DbType dbType;

}
