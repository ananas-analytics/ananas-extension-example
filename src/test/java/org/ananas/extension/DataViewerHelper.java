package org.ananas.extension;

import org.ananas.runner.core.common.JsonUtil;
import org.ananas.runner.core.model.Engine;
import org.ananas.runner.steprunner.DefaultDataViewer;
import org.ananas.server.ApiResponseBuilder;

import java.util.HashMap;

public class DataViewerHelper {
    public static String getViewerJobData(String sql, String jobId, String stepId, Engine engine) {
        DefaultDataViewer.DataViewRepository repository = new DefaultDataViewer.DataViewRepository();
        String output =
                JsonUtil.toJson(
                        ApiResponseBuilder.Of().OK(repository.query(sql, jobId, stepId, engine)).build());

        return output;
    }

    public static String getViewerJobDataWithDefaultDB(String sql, String jobId, String stepId) {
        DefaultDataViewer.DataViewRepository repository = new DefaultDataViewer.DataViewRepository();
        Engine engine = new Engine();
        engine.name = "Local Engine";
        engine.type = "flink";
        engine.properties = new HashMap<>();
        engine.properties.put("database_type", "derby");
        String output =
                JsonUtil.toJson(
                        ApiResponseBuilder.Of().OK(repository.query(sql, jobId, stepId, engine)).build());

        return output;
    }

    public static String getViewerJobDataWithEngine(
            String sql, String jobId, String stepId, Engine engine) {
        DefaultDataViewer.DataViewRepository repository = new DefaultDataViewer.DataViewRepository();
        String output =
                JsonUtil.toJson(
                        ApiResponseBuilder.Of().OK(repository.query(sql, jobId, stepId, engine)).build());
        return output;
    }
}

