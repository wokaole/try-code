package com.cold.tutorial.concurrent.ex6;

import java.util.ArrayList;
import java.util.List;

/**
 * 串行地渲染页面元素
 * Created by faker on 2015/9/4.
 */
public class SingleThreadRenderer {

    void renderPage(CharSequence source) {
        renderText(source);
        List<ImageData> list = new ArrayList<>();
        for (ImageInfo imageInfo : scanForImageInfo(source)) {
            list.add(imageInfo.downloadImage());
        }

        for (ImageData imageData : list) {
            renderImage(imageData);
        }
    }

    private void renderImage(ImageData imageData) {

    }

    private ImageInfo[] scanForImageInfo(CharSequence source) {
        return new ImageInfo[0];
    }

    private void renderText(CharSequence source) {
        
    }

    private class ImageInfo {
        public ImageData downloadImage() {
            return null;
        }
    }

    private class ImageData {

    }
}

