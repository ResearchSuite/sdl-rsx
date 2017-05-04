package edu.cornell.tech.foundry.sdl_rsx_rstbsupport;

import android.graphics.Color;
import android.support.annotation.Nullable;

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

    @Nullable
    protected String generateOverlayImageTitle(YADLSpotStepDescriptor yadlSpotStepDescriptor) {

        if (yadlSpotStepDescriptor.itemCellSelectedOverlayImageTitle == null ) {
            return null;
        }

        StringBuilder imageTitleStringBuilder = new StringBuilder();

        if (!TextUtils.isEmpty(yadlSpotStepDescriptor.itemCellSelectedOverlayImagePath)) {
            imageTitleStringBuilder.append(yadlSpotStepDescriptor.itemCellSelectedOverlayImagePath);
            imageTitleStringBuilder.append("/");
        }

        imageTitleStringBuilder.append(yadlSpotStepDescriptor.itemCellSelectedOverlayImageTitle);

        if (!TextUtils.isEmpty(yadlSpotStepDescriptor.itemCellSelectedOverlayImageExtension)) {
            imageTitleStringBuilder.append(".");
            imageTitleStringBuilder.append(yadlSpotStepDescriptor.itemCellSelectedOverlayImageExtension);
        }

        return imageTitleStringBuilder.toString();
    }

    protected String[] excludedIdentifiers(List<YADLItemDescriptor> items, YADLSpotStepDescriptor yadlSpotStepDescriptor, RSTBTaskBuilderHelper helper) {
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
        List<String> includedIdentifiers = Arrays.asList((android.text.TextUtils.split(joinedItems, ",")));
        if (includedIdentifiers.size() > 0) {

            ArrayList<String> excludedIdentifiers = new ArrayList<>();
            for (YADLItemDescriptor item : items) {
                if (!includedIdentifiers.contains(item.identifier)) {
                    excludedIdentifiers.add(item.identifier);
                }
            }

            String[] excludedIdentifierArray = new String[excludedIdentifiers.size()];
            excludedIdentifierArray = excludedIdentifiers.toArray(excludedIdentifierArray);
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
        String[] excludedIdentifiers = this.excludedIdentifiers(yadlSpotStepDescriptor.items, yadlSpotStepDescriptor, helper);
//        String[] excludedIdentifiers = {"BedToChair", "Dressing"};
        RSXMultipleImageSelectionSurveyOptions options = new RSXMultipleImageSelectionSurveyOptions(jsonObject, helper.getContext());

        options.setItemCellSelectedOverlayImageTitle(this.generateOverlayImageTitle(yadlSpotStepDescriptor));

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
