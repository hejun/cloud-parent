package io.github.hejun.cloud.msg.service.impl;

import io.github.hejun.cloud.common.exception.StatefulException;
import io.github.hejun.cloud.msg.common.dto.Msg;
import io.github.hejun.cloud.msg.common.enums.MsgType;
import io.github.hejun.cloud.msg.service.MsgService;
import io.github.hejun.cloud.msg.util.WsPoolManager;
import jakarta.websocket.Session;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * WebSocket消息处理
 *
 * @author HeJun
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class WsMsgServiceImpl implements MsgService {

	@Override
	public String send(Msg msg) throws Exception {
		Session session = WsPoolManager.get(msg.getReceiver());
		if (session != null) {
			synchronized (session) {
				log.debug("WsMsg: sender [ {} ] send msg to receiver [ {} ] with content: {}", msg.getSender(), msg.getReceiver(), msg.getContent());
				session.getBasicRemote().sendText(msg.getContent());
			}
			return null;
		}
		log.debug("WsMsg: receiver [ {} ] is not exists", msg.getReceiver());
		throw new StatefulException(404, "Session not exists");
	}

	@Override
	public MsgType supportType() {
		return MsgType.WS;
	}

}
