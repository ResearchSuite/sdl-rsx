package edu.cornell.tech.foundry.sdl_rsx.task;

import org.researchstack.backbone.step.Step;
import org.researchstack.backbone.task.OrderedTask;

import java.util.Arrays;
import java.util.List;

/**
 * Created by jk on 5/31/16.
 */
public class RSXSingleImageClassificationSurveyTask extends OrderedTask {

    public RSXSingleImageClassificationSurveyTask(String identifier, List<Step> steps) {
        super(identifier, steps);
    }

    public RSXSingleImageClassificationSurveyTask(String identifier, Step... steps)
    {
        this(identifier, Arrays.asList(steps));
    }
}
