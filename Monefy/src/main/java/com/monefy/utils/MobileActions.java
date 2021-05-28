package com.monefy.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.interactions.Actions;

import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;
import com.github.romankh3.image.comparison.ImageComparison;
import com.github.romankh3.image.comparison.ImageComparisonUtil;
import com.github.romankh3.image.comparison.model.ImageComparisonResult;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;


public class MobileActions {

    private AndroidDriver<AndroidElement> androidDriver;
    private Actions actions;


    public MobileActions(AndroidDriver<AndroidElement> androidDriver) {
        this.androidDriver = androidDriver;
        this.actions = new Actions(androidDriver);
    }

    public void saveScreenShot() {
        String Base64StringOfScreenshot = "";
        
        File src = ((TakesScreenshot) androidDriver).getScreenshotAs(OutputType.FILE);

        byte[] fileContent = new byte[0];
        try {
            fileContent = FileUtils.readFileToByteArray(src);
        } catch (IOException e) {
            System.out.println(e.toString());
        }
        Base64StringOfScreenshot = "data:image/png;base64," + java.util.Base64.getEncoder().encodeToString(fileContent);

        attachScreenshotToExtent(Base64StringOfScreenshot);
    }

    public void attachScreenshotToExtent(String imagePath) {
        try {
            ExtentCucumberAdapter.addTestStepScreenCaptureFromPath(imagePath);
        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }

    public void tap(AndroidElement elementToTap) {
        actions.click(elementToTap).perform();
    }

    public boolean compareImages(String actualImagePath, String expectedImagePath) {
        boolean flag = false;
        BufferedImage expectedImage = ImageComparisonUtil.readImageFromResources(actualImagePath);
        BufferedImage actualImage = ImageComparisonUtil.readImageFromResources(expectedImagePath);

        File resultDestination = new File(IConstants.SCREENSHOT + "result.png");
        ImageComparison imageComparison = new ImageComparison(expectedImage, actualImage, resultDestination);

        //Threshold - it's the max distance between non-equal pixels. By default it's 5.
        imageComparison.setThreshold(10);
        int re = imageComparison.getThreshold();
        System.out.println(re);
        //RectangleListWidth - Width of the line that is drawn in the rectangle. By default it's 1.
        imageComparison.setRectangleLineWidth(5);
        imageComparison.getRectangleLineWidth();

        //DifferenceRectangleFilling - Fill the inside the difference rectangles with a transparent fill. By default it's false and 20.0% opacity.
        imageComparison.setDifferenceRectangleFilling(true, 30.0);
        imageComparison.isFillDifferenceRectangles();
        imageComparison.getPercentOpacityDifferenceRectangles();

        //ExcludedRectangleFilling - Fill the inside the excluded rectangles with a transparent fill. By default it's false and 20.0% opacity.
        imageComparison.setExcludedRectangleFilling(true, 30.0);
        imageComparison.isFillExcludedRectangles();
        imageComparison.getPercentOpacityExcludedRectangles();

        //Destination. Before comparing also can be added destination file for result image.
        imageComparison.setDestination(resultDestination);
        imageComparison.getDestination();

        //MaximalRectangleCount - It means that would get first x biggest rectangles for drawing.
        // by default all the rectangles would be drawn.
        imageComparison.setMaximalRectangleCount(10);
        imageComparison.getMaximalRectangleCount();

        //MinimalRectangleSize - The number of the minimal rectangle size. Count as (width x height).
        // by default it's 1.
        imageComparison.setMinimalRectangleSize(100);
        imageComparison.getMinimalRectangleSize();

        //Change the level of the pixel tolerance:
        imageComparison.setPixelToleranceLevel(0.2);
        imageComparison.getPixelToleranceLevel();

        //After configuring the ImageComparison object, can be executed compare() method:
        ImageComparisonResult imageComparisonResult = imageComparison.compareImages();

        if (imageComparisonResult.getDifferencePercent() == 0.0) {
            flag = true;
        }

        return flag;
    }
    
}
