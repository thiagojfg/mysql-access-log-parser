package com.ef.model;

public class AccessPerIP {

    private final String ip;

    private final Long numberOfAccess;

    public AccessPerIP(String ip, Long numberOfAccess) {
        this.ip = ip;
        this.numberOfAccess = numberOfAccess;
    }

    public String getIp() {
        return ip;
    }

    public Long getNumberOfAccess() {
        return numberOfAccess;
    }

    @Override
    public String toString() {
        return "AccessPerIP{" + "ip=" + ip + ", numberOfAccess=" + numberOfAccess + '}';
    }
}
