package com.cold.tutorial.concurrent.ex6;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * 使用Future 渲染页面
 * Created by faker on 2015/9/4.
 */
public class FutureRenderer {
    private final ExecutorService executor = Executors.newCachedThreadPool();

    void renderPage(CharSequence source) {
        final List<ImageInfo> imageInfoList = scanForImageInfo(source);
        Callable<List<ImageData>> task = new Callable<List<ImageData>>() {
            @Override
            public List<ImageData> call() throws Exception {
                List<ImageData> result = new ArrayList<>();
                for (ImageInfo imageInfo : imageInfoList) {
                    result.add(imageInfo.downloadImage());
                }
                return result;
            }
        };

        Future<List<ImageData>> future = executor.submit(task);
        renderText(source);
        try {
            List<ImageData> imageDatas = future.get();
            for (ImageData imageData : imageDatas) {
                renderImage(imageData);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            future.cancel(true);
        } catch (ExecutionException e) {
            System.out.println(e.getCause());
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
