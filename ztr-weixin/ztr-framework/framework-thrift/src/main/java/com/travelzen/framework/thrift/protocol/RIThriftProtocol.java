package com.travelzen.framework.thrift.protocol;

import com.travelzen.framework.logger.core.ri.CallInfo;
import com.travelzen.framework.logger.core.ri.RequestIdentityHolder;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMessage;
import org.apache.thrift.transport.TTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.travelzen.framework.core.json.JsonUtil;
import com.travelzen.framework.core.util.TZUtil;

public class RIThriftProtocol extends TBinaryProtocol {
    private static Logger logger = LoggerFactory
            .getLogger(RIThriftProtocol.class);
    protected static final char DELIMETER = '|';

    public RIThriftProtocol(TTransport trans) {
        super(trans);
    }

    public RIThriftProtocol(TTransport trans, boolean strictRead,
                            boolean strictWrite) {
        super(trans, strictRead, strictWrite);
    }

    @Override
    public void writeMessageBegin(TMessage message) throws TException {
        CallInfo callInfo = RequestIdentityHolder.get();
        String callInfoStr = null;
        if (callInfo != null) {
            try {
                callInfoStr = JsonUtil.toJson(callInfo, false);
            } catch (Exception e) {
                logger.warn("序列化CallInfo出现异常");
                e.printStackTrace();
            }
        } else {
            logger.warn("本地CallInfo不存在，原因可能为: 1.调用方未将CallInfo传递下来， 2.RPC第一跳没有初始化RequestIdentityHolder");
        }

        if (TZUtil.isEmpty(callInfoStr) || callInfoStr.equals("null")) {
            super.writeMessageBegin(message);
        } else {
            if (callInfo.getRpid() != null) {
                //CatRpcRecorder.remoteCall(callInfo.getRpid());
            } else {
                logger.warn("CallInfo可以向下传递，但是Rpid未能获取到，会影响Cat监控。");
            }
            String fixedName = message.name + DELIMETER + callInfoStr;
            TMessage tm = new TMessage(fixedName, message.type, message.seqid);
            super.writeMessageBegin(tm);
        }
    }

    @Override
    public void writeMessageEnd() {
        super.writeMessageEnd();
    }

    @Override
    public TMessage readMessageBegin() throws TException {
        TMessage tm = super.readMessageBegin();
        int idx = tm.name.lastIndexOf(DELIMETER);
        if (idx < 0) {
            logger.warn("调用方未将CallInfo信息传递进来，请调用方升级协议。");
            return tm;
        } else {
            String callInfoStr = tm.name.substring(idx + 1);
            CallInfo callInfo = null;
            try {
                callInfo = (CallInfo) JsonUtil.fromJson(callInfoStr, CallInfo.class);
            } catch (Exception e) {
                logger.warn("CallInfo反序列化失败，调用方没有使用新协议，请调用方升级协议。");
                logger.warn("调用方传输的内容为：" + callInfoStr);
            }
            if (callInfo != null) {
                RequestIdentityHolder.setOnRead(callInfo);
                //CatRpcRecorder.setCurrentId(callInfo.getRpid());
            }
            return new TMessage(tm.name.substring(0, idx), tm.type, tm.seqid);
        }
    }

    @Override
    public void readMessageEnd() {
        super.readMessageEnd();
    }
}
