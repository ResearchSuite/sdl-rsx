package edu.cornell.tech.foundry.sdl_rsx.choice;

import org.json.JSONObject;

import java.util.List;
import java.util.Random;

/**
 * Created by jk on 5/31/16.
 */
public class PAMImageChoice <T> extends RSXImageChoice {


//    private List<String> images;
    private String[] images;
    private String currentImageName;
    public PAMImageChoice(String[] images, T value) {

        super(null, null, null, value, null);
        this.images = images;

    }

    @Override
    public String getNormalStateImage() {
        int numberOfImages = this.images.length;
        Random rand = new Random();
        int index = rand.nextInt(numberOfImages);
        this.currentImageName = this.images[index];
        return this.images[index];
    }

    public String getCurrentImageName() {
        return currentImageName;
    }

    //    @Override
//    public T getValue() {
//        T value = (T)super.getValue();
//        if (value instanceof JSONObject) {
//            JSONObject object = (JSONObject)value;
//            object.put
//        }
//        else {
//            return value;
//        }
//    }

}
