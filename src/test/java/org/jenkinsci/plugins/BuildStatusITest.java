package org.jenkinsci.plugins;

import org.jenkinsci.plugins.api.BitbucketApiService;
import org.jenkinsci.plugins.api.BuildStatus;
import org.junit.Test;

/**
 *
 */
public class BuildStatusITest {

    @Test
    public void testSendBuildStatus() {
        BitbucketApiService service = new BitbucketApiService("bwR9aWzdnbRdnrcPGA", "fGmTbmv6arNRxv6TkVUxeFjpbNWa6LU2");

        BuildStatus status = new BuildStatus();
        status.setKey("21");
        status.setUrl("http://jenkins.e3learning.com.au:8080/");
        status.setName("ci-learnforce-commons-develop");
        status.setState(BuildStatus.State.SUCCESSFUL);
        status.setDescription("10 of 10 tests passed.");

        service.postBuildStatus(status, "e3learningdev", "learnforce-commons", "0ba3027747d8a9a6e0eb89e68c7faefcfd8a299c");
    }
}
