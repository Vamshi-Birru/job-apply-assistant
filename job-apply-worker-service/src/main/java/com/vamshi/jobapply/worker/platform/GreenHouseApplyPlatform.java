package com.vamshi.jobapply.worker.platform;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.vamshi.jobapply.worker.dto.ApplyRequest;
import com.vamshi.jobapply.worker.dto.ApplyResponse;
import com.vamshi.jobapply.worker.dto.ApplyStatus;

import java.nio.file.Path;

public class GreenHouseApplyPlatform {
    public ApplyResponse detect(ApplyRequest request){
        try(Playwright playwright = Playwright.create()){
            Browser browser = playwright.chromium().launch(
                    new BrowserType.LaunchOptions().setHeadless(true)
            );
            Page page = browser.newPage();
            page.navigate(request.getJobUrl());

            boolean formDetected = page.locator("form").count()>0;

            Path screenShotPath = Path.of(
                    "screenshots/greenhouse.png"
            );

            page.screenshot(new Page.ScreenshotOptions().setPath(screenShotPath));

            browser.close();
            return new ApplyResponse(
                    request.getJobUrl(),
                    request.getPlatform(),
                    formDetected ? ApplyStatus.DETECTED : ApplyStatus.FAILED,
                    screenShotPath.toString()
            );
        }
    }
}
