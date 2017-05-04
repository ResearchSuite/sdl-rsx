package edu.cornell.tech.foundry.sdl_rsx.model;

import org.researchstack.backbone.result.Result;
import org.researchstack.backbone.ui.ViewTaskActivity;
import org.researchstack.backbone.ui.step.layout.StepLayout;

/**
 * Created by jameskizer on 4/19/17.
 */

public class RSXMultipleImageSelectionSurveyResult extends Result {

//    open var selectedIdentifiers: [String]?
//    open var notSelectedIdentifiers: [String]?
//    open var excludedIdentifiers: [String]?
    private final String[] selectedIdentifiers;
    private final String[] notSelectedIdentifiers;
    private final String[] excludedIdentifiers;

    /**
     * Returns an initialized result using the specified identifier.
     * <p>
     * Typically, objects such as {@link ViewTaskActivity} and {@link
     * StepLayout} instantiate result (and Result
     * subclass) objects; you seldom need to instantiate a result object in your code.
     *
     * @param identifier The unique identifier of the result.
     */
    public RSXMultipleImageSelectionSurveyResult(
            String identifier,
            String[] selectedIdentifiers,
            String[] notSelectedIdentifiers,
            String[] excludedIdentifiers
    ) {

        super(identifier);
        this.selectedIdentifiers = selectedIdentifiers;
        this.notSelectedIdentifiers = notSelectedIdentifiers;
        this.excludedIdentifiers = excludedIdentifiers;
    }

    public String[] getSelectedIdentifiers() {
        return selectedIdentifiers;
    }

    public String[] getNotSelectedIdentifiers() {
        return notSelectedIdentifiers;
    }

    public String[] getExcludedIdentifiers() {
        return excludedIdentifiers;
    }
}
