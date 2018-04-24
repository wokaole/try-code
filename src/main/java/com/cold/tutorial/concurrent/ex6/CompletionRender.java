package com.cold.tutorial.concurrent.ex6;

import java.util.List;
import java.util.concurrent.*;

/**
 * Created by faker on 2015/9/4.
 */
public class CompletionRender {
    private final ExecutorService executor;

    public CompletionRender(ExecutorService executor) {
        this.executor = executor;
    }

    void renderPage(CharSequence source) throws InterruptedException, ExecutionException {
        List<ImageInfo> imageInfoList = scanForImageInfo(source);
        ExecutorCompletionService<ImageData> completionService = new ExecutorCompletionService<ImageData>(executor);
        for(final ImageInfo imageInfo : imageInfoList) {
            completionService.submit(new Callable<ImageData>() {
                @Override
                public ImageData call() throws Exception {
                    return imageInfo.downloadImage();
                }
            });

        }
        renderText(source);

        for (int t=0, n=imageInfoList.size(); t<n; t++) {
            Future<ImageData> take = completionService.take();
            ImageData imageData = take.get();
            renderImage(imageData);
        }
    }

    private void renderImage(ImageData imageData) {

    }

    private void renderText(CharSequence source) {

    }

    private List<ImageInfo> scanForImageInfo(CharSequence source) {
        return null;
    }

    private class ImageInfo {
        public ImageData downloadImage() {
            return null;
        }
    }
    private class ImageData {
    }

}
