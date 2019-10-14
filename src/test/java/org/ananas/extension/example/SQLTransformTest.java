package org.ananas.extension.example;

import com.jayway.jsonpath.JsonPath;
import org.ananas.cli.Main;
import org.ananas.extension.DataViewerHelper;
import org.ananas.extension.TestHelper;
import org.ananas.extension.test.AnanasExtensionTestBase;
import org.junit.Assert;
import org.junit.Test;
import org.junit.contrib.java.lang.system.Assertion;

import java.net.URL;
import java.util.List;
import java.util.Map;

public class SQLTransformTest extends AnanasExtensionTestBase {
    @Test
    public void exploreAPISource() {
        exit.expectSystemExitWithStatus(0);

        exit.checkAssertionAfterwards(
                new Assertion() {
                    public void checkAssertion() {
                        String json = systemOutRule.getLog();
                        int code = JsonPath.read(json, "$.code");
                        Assert.assertEquals(200, code);

                        List<Map<String, String>> fields = JsonPath.read(json, "$.data.schema.fields");
                        Assert.assertTrue(fields.size() > 0);
                    }
                });


        URL project = TestHelper.getResource("test_projects/transform");
        Main.main(
                new String[] {
                        "explore",
                        "-p", project.getPath(),
                        "-x", ".",
                        "web_api_source",
                        "-n", "0", "--size", "5"
                });
    }

    @Test
    public void testSQLTransformTest() {
        exit.expectSystemExitWithStatus(0);

        exit.checkAssertionAfterwards(
                new Assertion() {
                    public void checkAssertion() {
                        String json = systemOutRule.getLog();
                        int code = JsonPath.read(json, "$.code");
                        Assert.assertEquals(200, code);

                        List<Map<String, String>> fields =
                                JsonPath.read(json, "$.data.sql_transform.schema.fields");
                        Assert.assertTrue(fields.size() > 0);

                        Assert.assertEquals("code", fields.get(0).get("name"));
                        Assert.assertEquals("VARCHAR", fields.get(0).get("type"));

                        Assert.assertEquals("rate", fields.get(1).get("name"));
                        Assert.assertEquals("DECIMAL", fields.get(1).get("type"));

                        Assert.assertEquals("time", fields.get(2).get("name"));
                        Assert.assertEquals("VARCHAR", fields.get(2).get("type"));
                    }
                });

        URL project = TestHelper.getResource("test_projects/transform");

        // test the transform step
        Main.main(
                new String[] {
                        "test",
                        "-p", project.getPath(), // the ananas project to test with
                        "-x", ".",      // add current extension
                        "sql_transform", // the step to test
                });
    }

    @Test
    public void testRun() {
        exit.expectSystemExitWithStatus(0);

        String stepId = "number_view";
        exit.checkAssertionAfterwards(
                new Assertion() {
                    public void checkAssertion() {
                        String json = systemOutRule.getLog();
                        int code = JsonPath.read(json, "$.code");
                        Assert.assertEquals(200, code);

                        String jobId = JsonPath.read(json, "$.data.jobid");
                        Assert.assertNotNull(jobId);

                        // test data in the viewer
                        String result = DataViewerHelper.getViewerJobDataWithDefaultDB(
                                "SELECT * FROM PCOLLECTION", jobId, stepId);
                        int resultCode = JsonPath.read(result, "$.code");
                        Assert.assertEquals(200, resultCode);

                        List<Map<String, String>> fields = JsonPath.read(result, "$.data.schema.fields");
                        Assert.assertTrue(fields.size() > 0);

                        List<Object> data = JsonPath.read(result, "$.data.data");
                        Assert.assertTrue(data.size() > 0);

                        System.out.println(result);
                    }
                });

        URL project = TestHelper.getResource("test_projects/transform");

        // run the pipeline
        Main.main(
                new String[] {
                        "run",
                        "-p", project.getPath(), // the ananas project
                        "-x", ".",      // add current extension
                        stepId,
                });
    }
}
