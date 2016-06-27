package edu.cornell.tech.foundry.sdl_rsx.choice;

import java.util.List;
import java.util.Random;

/**
 * Created by jk on 5/31/16.
 */
public class PAMImageChoice <T> extends RSXImageChoice {


//    private List<String> images;
    private String[] images;
    public PAMImageChoice(String[] images, T value) {

        super(null, null, null, value, null);
        this.images = images;

    }

    @Override
    public String getNormalStateImage() {
        int numberOfImages = this.images.length;
        Random rand = new Random();
        int index = rand.nextInt(numberOfImages);
        return this.images[index];
    }

}
