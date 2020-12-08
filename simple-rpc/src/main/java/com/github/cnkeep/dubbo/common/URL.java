package com.github.cnkeep.dubbo.common;


import java.util.Map;
import java.util.Objects;

public class URL {
    private String protocol;
    private String path;
    private Map<String, Object> parameters;

    public URL() {
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, Object> parameters) {
        this.parameters = parameters;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        URL url = (URL) o;
        return Objects.equals(protocol, url.protocol) &&
                Objects.equals(path, url.path) &&
                Objects.equals(parameters, url.parameters);
    }

    @Override
    public int hashCode() {
        return Objects.hash(protocol, path, parameters);
    }
}
