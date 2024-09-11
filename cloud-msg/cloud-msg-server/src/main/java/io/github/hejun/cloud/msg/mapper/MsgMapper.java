package io.github.hejun.cloud.msg.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.hejun.cloud.msg.entity.Msg;
import org.apache.ibatis.annotations.Mapper;

/**
 * 消息Mapper
 *
 * @author HeJun
 */
@Mapper
public interface MsgMapper extends BaseMapper<Msg> {
}
