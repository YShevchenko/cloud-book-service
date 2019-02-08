package com.oydipi.bookservice.leadership;

import org.springframework.http.HttpMethod;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
class Hook {

    @Id
    @GeneratedValue
    private Long id;

    @SuppressWarnings("unused")
    private Hook() {
    }

    public Hook(HttpMethod method, String uri, String cron) {
        this.method = method;
        this.uri = uri;
        this.cron = cron;
    }

    private String cron;

    private String uri;

    private HttpMethod method = HttpMethod.POST;

    private long version = 0L;

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public Long getId() {
        return id;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public void setMethod(HttpMethod method) {
        this.method = method;
    }

    @Override
    public String toString() {
        return method + " [id=" + id + ", uri=" + uri + "]";
    }

}
