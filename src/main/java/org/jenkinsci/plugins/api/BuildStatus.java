package org.jenkinsci.plugins.api;

import hudson.model.Describable;
import hudson.model.Descriptor;
import hudson.model.Result;
import org.omg.PortableInterceptor.SUCCESSFUL;

/**
 *
 */
public class BuildStatus {


    private State state;
    private String key;
    private String name;
    private String url;
    private String description;

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void setState(Result result) {
        if (result == Result.SUCCESS) {
            this.state = State.SUCCESSFUL;
        } else if (result == Result.FAILURE || result == Result.UNSTABLE) {
            this.state = State.FAILED;
        }

    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public enum State {
        SUCCESSFUL, INPROGRESS, FAILED;
    }
}
