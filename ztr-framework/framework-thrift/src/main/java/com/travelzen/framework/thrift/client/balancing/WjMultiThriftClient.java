package com.travelzen.framework.thrift.client.balancing;

import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Powered by IntelliJ IDEA.
 * Author: luchen.
 * Date: 15-1-8.
 */
public class WjMultiThriftClient <I, C extends I> extends PooledClient<I> {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private boolean framed = false;
    private String host = null;
    private int port = -1;
    private long soTimeout;
    private String name;

    private int maxIdleConnections = 100;

    public WjMultiThriftClient(String host, int port, boolean framed,
                          long soTimeout, String name, Class<C> clazz) {
        super(clazz);
        this.host = host;
        this.port = port;
        this.framed = framed;
        this.soTimeout = soTimeout;
        this.name = name;
    }

    public WjMultiThriftClient(String host, int port, boolean framed, Class<C> clazz) {
        this(host, port, framed, 5000, clazz.getName(), clazz);
    }

    public WjMultiThriftClient(String host, int port, Class<C> clazz) {
        this(host, port, false, 5000, clazz.getName(), clazz);
    }

    public TTransport genTransport(boolean framed,TSocket socket) {
        return null;
    }
    public TProtocol genProtocol(TTransport transport) {
        return null;
    }

    @Override
    public ThriftConnection<I> createConnection() {
        try {
            // log.debug("create connection for host:{} on port:{}", host,
            // port);
            final WjMultiThriftClient ths=this;
            return new ThriftConnection<I>(host, port, framed, type, name) {
                public TTransport genTransport(boolean framed,TSocket socket) {
                    TTransport t=ths.genTransport(framed, socket);
                    if(t==null) {
                        t=super.genTransport(framed,socket);
                    }
                    return t;
                }
                public TProtocol genProtocol(TTransport transport) {
                    TProtocol p=ths.genProtocol(transport);
                    if(p==null) {
                        p=super.genProtocol(transport);
                    }
                    return p;
                }
            };
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        return null;
    }

    @Override
    public String toString() {
        return this.getClass().getName() + " (host = " + host + ", port = "
                + port + ")";
    }
}
