package org.jenkinsci.plugins;

import hudson.Extension;
import hudson.ExtensionPoint;
import hudson.Launcher;
import hudson.Util;
import hudson.model.*;
import hudson.scm.ChangeLogSet;
import hudson.tasks.*;
import hudson.util.FormValidation;
import net.sf.json.JSONObject;
import org.jenkinsci.plugins.api.BitbucketApiService;
import org.jenkinsci.plugins.api.BuildStatus;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.QueryParameter;
import org.kohsuke.stapler.StaplerRequest;

import java.net.URL;

/**
 *
 */

public class BitbucketBuildStatus extends Notifier {

    private String clientID;
    private String clientSecret;

    @DataBoundConstructor
    public BitbucketBuildStatus(String clientID, String clientSecret) {
        super();
        this.clientID = Util.fixEmptyAndTrim(clientID);
        this.clientSecret = Util.fixEmptyAndTrim(clientSecret);
    }

    @Override
    public boolean perform(AbstractBuild build, Launcher launcher, BuildListener listener) {
        try {
            String gitUrl = build.getEnvironment(listener).get("GIT_URL");
            if (gitUrl == null) {
                return true;
            }
            gitUrl = gitUrl.replaceAll("git@bitbucket.org/", "");
            String[] repoDetails = gitUrl.split("[/]");



            Object[] changes = build.getChangeSet().getItems();
            if (changes.length > 0) {
                BuildStatus status = new BuildStatus();
                status.setKey(build.getId());
                status.setUrl(build.getUrl());
                status.setName(build.getDisplayName());
                status.setState(build.getResult());
                status.setDescription(build.getTestResultAction().getBuildHealth().getDescription());

                if (status.getState() != null) {
                    new BitbucketApiService(clientID, clientSecret).postBuildStatus(status, repoDetails[0], repoDetails[1], (String) changes[0]);
                }
            }
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


    @Override
    public BuildStepMonitor getRequiredMonitorService() {
        return null;
    }

    @Extension
    public static final class Descriptor extends BuildStepDescriptor<Publisher> {


        @Override
        public boolean isApplicable(Class<? extends AbstractProject> jobType) {
            return true;
        }

        @Override
        public String getDisplayName() {
            return "Publish build status to Bitbucket";
        }

        @Override
        public boolean configure(StaplerRequest req, JSONObject json) throws FormException {
            req.bindJSON(this, json.getJSONObject("build-status"));
            save();

            return true;
        }


    }
}
