package edu.cornell.tech.foundry.sdl_rsx_rstbsupport;

import android.graphics.Color;

import com.google.gson.JsonObject;

import org.apache.commons.lang3.StringUtils;
import org.researchstack.backbone.answerformat.AnswerFormat;
import org.researchstack.backbone.answerformat.ChoiceAnswerFormat;
import org.researchstack.backbone.step.Step;
import org.researchstack.backbone.utils.TextUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.cornell.tech.foundry.researchsuitetaskbuilder.DefaultStepGenerators.RSTBBaseStepGenerator;
import edu.cornell.tech.foundry.researchsuitetaskbuilder.RSTBStateHelper;
import edu.cornell.tech.foundry.researchsuitetaskbuilder.RSTBTaskBuilderHelper;
import edu.cornell.tech.foundry.sdl_rsx.answerformat.RSXImageChoiceAnswerFormat;
import edu.cornell.tech.foundry.sdl_rsx.choice.RSXImageChoice;
import edu.cornell.tech.foundry.sdl_rsx.choice.RSXTextChoiceWithColor;
import edu.cornell.tech.foundry.sdl_rsx.model.RSXActivityItem;
import edu.cornell.tech.foundry.sdl_rsx.model.RSXMultipleImageSelectionSurveyOptions;
import edu.cornell.tech.foundry.sdl_rsx.model.YADLSpotAssessment;
import edu.cornell.tech.foundry.sdl_rsx.step.RSXSingleImageClassificationSurveyStep;
import edu.cornell.tech.foundry.sdl_rsx.step.YADLSpotAssessmentStep;

/**
 * Created by jameskizer on 4/14/17.
 */

public class YADLSpotStepGenerator extends RSTBBaseStepGenerator {
    public YADLSpotStepGenerator()
    {
        super();
        this.supportedTypes = Arrays.asList(
                "YADLSpotAssessment"
        );
    }

    protected String generateImagePath(YADLSpotStepDescriptor yadlSpotStepDescriptor, YADLItemDescriptor itemDescriptor) {
        StringBuilder imageTitleStringBuilder = new StringBuilder();

        if (!TextUtils.isEmpty(yadlSpotStepDescriptor.imagePath)) {
            imageTitleStringBuilder.append(yadlSpotStepDescriptor.imagePath);
            imageTitleStringBuilder.append("/");
        }

        imageTitleStringBuilder.append(itemDescriptor.imageTitle);

        if (!TextUtils.isEmpty(yadlSpotStepDescriptor.imageExtension)) {
            imageTitleStringBuilder.append(".");
            imageTitleStringBuilder.append(yadlSpotStepDescriptor.imageExtension);
        }

        return imageTitleStringBuilder.toString();
    }

    protected String[] excludedIdentifiers(YADLSpotStepDescriptor yadlSpotStepDescriptor, List<YADLItemDescriptor> items, RSTBTaskBuilderHelper helper) {
        String[] emptyArray = new String[0];

        String filterKey = yadlSpotStepDescriptor.filterKey;
        if (StringUtils.isEmpty(filterKey)) {
            return emptyArray;
        }

        RSTBStateHelper stateHelper = helper.getStateHelper();
        if (stateHelper == null){
            return emptyArray;
        }

        byte[] filteredItemsBytes = stateHelper.valueInState(helper.getContext(), filterKey);
        if (filteredItemsBytes == null) {
            return emptyArray;
        }

        String joinedItems = new String(filteredItemsBytes);
        List<String> excludedIdentifiers = Arrays.asList((android.text.TextUtils.split(joinedItems, ",")));
        if (excludedIdentifiers.size() > 0) {
            String[] excludedIdentifierArray = new String[excludedIdentifiers.size()];
            for (int i=0; i<excludedIdentifiers.size(); i++) {
                excludedIdentifierArray[i] = excludedIdentifiers.get(i);
            }
            return excludedIdentifierArray;
        }
        else {
            return emptyArray;
        }
    }

    protected RSXImageChoice[] generateChoices(YADLSpotStepDescriptor yadlSpotStepDescriptor, List<YADLItemDescriptor> items) {
        RSXImageChoice[] choices = new RSXImageChoice[items.size()];

        for(int i=0; i<items.size(); i++) {
            YADLItemDescriptor item = items.get(i);
            String imagePath = this.generateImagePath(yadlSpotStepDescriptor, item);
            RSXActivityItem activity = new RSXActivityItem(
                    item.identifier,
                    item.description,
                    imagePath
            );
            choices[i] = activity.getImageChoice();
        }


        return choices;
    }

    @Override
    public List<Step> generateSteps(RSTBTaskBuilderHelper helper, String type, JsonObject jsonObject) {
        YADLSpotStepDescriptor yadlSpotStepDescriptor = helper.getGson().fromJson(jsonObject, YADLSpotStepDescriptor.class);

        if (yadlSpotStepDescriptor == null) {
            return null;
        }

        RSXImageChoice[] choices = this.generateChoices(yadlSpotStepDescriptor, yadlSpotStepDescriptor.items);
        AnswerFormat answerFormat = new RSXImageChoiceAnswerFormat(AnswerFormat.ChoiceAnswerStyle.MultipleChoice, choices);
//        String[] excludedIdentifiers = this.excludedIdentifiers(yadlSpotStepDescriptor, yadlSpotStepDescriptor.items, helper);
        String[] excludedIdentifiers = {"BedToChair", "Dressing"};
        RSXMultipleImageSelectionSurveyOptions options = new RSXMultipleImageSelectionSurveyOptions(jsonObject, helper.getContext());

        Step step = new YADLSpotAssessmentStep(
                yadlSpotStepDescriptor.identifier,
                yadlSpotStepDescriptor.title,
                answerFormat,
                options,
                excludedIdentifiers
        );

        step.setOptional(yadlSpotStepDescriptor.optional);
        return Arrays.asList(step);
    }
}
