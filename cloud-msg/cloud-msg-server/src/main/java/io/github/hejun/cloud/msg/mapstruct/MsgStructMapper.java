package io.github.hejun.cloud.msg.mapstruct;

import io.github.hejun.cloud.msg.common.dto.MsgDto;
import io.github.hejun.cloud.msg.entity.Msg;
import org.mapstruct.Mapper;

/**
 * 消息映射
 *
 * @author HeJun
 */
@Mapper(componentModel = "spring")
public interface MsgStructMapper {

	MsgDto convert(Msg msg);

	Msg convert(MsgDto msg);

}
