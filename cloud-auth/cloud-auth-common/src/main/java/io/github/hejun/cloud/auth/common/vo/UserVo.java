package io.github.hejun.cloud.auth.common.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 用户VO
 *
 * @author HeJun
 */
@Getter
@Setter
@ToString
public class UserVo implements Serializable {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

}
