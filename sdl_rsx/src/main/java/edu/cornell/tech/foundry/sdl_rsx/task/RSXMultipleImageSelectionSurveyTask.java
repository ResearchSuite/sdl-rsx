package edu.cornell.tech.foundry.sdl_rsx.task;

import org.researchstack.backbone.step.Step;
import org.researchstack.backbone.task.OrderedTask;

import java.util.Arrays;
import java.util.List;

import edu.cornell.tech.foundry.sdl_rsx.model.RSXMultipleImageSelectionSurveyOptions;

/**
 * Created by jk on 6/1/16.
 */
public class RSXMultipleImageSelectionSurveyTask extends OrderedTask {

    private RSXMultipleImageSelectionSurveyOptions options;
    public RSXMultipleImageSelectionSurveyTask(String identifier, RSXMultipleImageSelectionSurveyOptions options, List<Step> steps) {
        super(identifier, steps);
        this.options = options;
    }

    public RSXMultipleImageSelectionSurveyTask(String identifier, RSXMultipleImageSelectionSurveyOptions options, Step... steps)
    {
        this(identifier, options, Arrays.asList(steps));
    }
}
