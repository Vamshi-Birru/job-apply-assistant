package com.vamshi.jobapply.worker.platform;

import com.microsoft.playwright.*;
import com.vamshi.jobapply.worker.resolver.AnswerResolver;
import com.vamshi.jobapply.worker.classifier.QuestionClassifier;
import com.vamshi.jobapply.worker.dto.AnswerResolution;
import com.vamshi.jobapply.worker.dto.ApplyRequest;
import com.vamshi.jobapply.worker.dto.ApplyResponse;
import com.vamshi.jobapply.worker.dto.ResolvedAnswer;
import com.vamshi.jobapply.worker.enums.ApplyStatus;
import com.vamshi.jobapply.worker.enums.QuestionCategory;
import com.vamshi.jobapply.worker.model.QuestionRegistry;
import com.vamshi.jobapply.worker.repository.QuestionRegistryRepository;
import com.vamshi.jobapply.worker.util.HashUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class GreenHouseApplyPlatform {

    private final QuestionRegistryRepository registry;
    private final QuestionClassifier classifier;
    private final AnswerResolver resolver;

    public GreenHouseApplyPlatform(QuestionRegistryRepository registry,
                                   QuestionClassifier classifier,
                                   AnswerResolver resolver){
        this.registry = registry;
        this.classifier = classifier;
        this.resolver = resolver;
    }

    public ApplyResponse apply(ApplyRequest request){


        try(Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(
                    new BrowserType.LaunchOptions().setHeadless(true)
            );
            Page page = browser.newPage();
            page.navigate(request.getJobUrl());

            List<Locator> inputs = page.locator("input:not([type=hidden]),textarea,select").all();

            for(Locator input: inputs){
                // pick label → placeholder → name
                String label = extractLabel(page, input);
                String placeholder = safe(input.getAttribute("placeholder"));
                String name = safe(input.getAttribute("name"));

                String rawText = pickBest(label,placeholder,name);
                String normalised = rawText.toLowerCase().trim();

                String hash = HashUtil.sha256(normalised);

                 var category = classifier.classify(normalised);

                 // 1) persist question in registry
                 registry.upsert(

                         hash,
                         normalised,
                         request.getPlatform(),
                         category

                 );

                // 2. resolve candidate answer
                var resolution = resolver.resolve(
                        request.getCandidateId(),
                        request.getCountry(),
                        hash
                );
                //3. Stop early if it is missing
                if(resolution.isMissing()){
                    browser.close();
                    return ApplyResponse.missing(
                            request.getJobUrl(),
                            request.getPlatform(),
                            hash,
                            normalised
                    );
                }

                // fill based on type
                fillInput(page, input, resolution.getAnswer());

            }
            //click submit
            clickSubmit(page);

            String screenShot = takeScreenShot(page, "submitted");

            browser.close();

            return new ApplyResponse(
                    request.getJobUrl(),
                    request.getPlatform(),
                    ApplyStatus.APPLIED,
                    screenShot,
                    null,
                    null
            );
        }
        catch(Exception e){
            return new ApplyResponse(
                    request.getJobUrl(),
                    request.getPlatform(),
                    ApplyStatus.FAILED,
                    e.getMessage(),
                    null,
                    null
            );
        }
    }

    public String extractLabel(Page page, Locator input){
        Locator lbl = input.locator("xpath=./ancestor::div[1]//label");
        if(lbl.count()>0) return safe(lbl.first().innerText());
        return "";
    }

    public String safe(String v){
        return v==null?"":v.trim();
    }

    public String pickBest(String ... values){
        for(String v: values){
            if(v!=null&&!v.isBlank())return v;
        }
        return "unknown_question";
    }
    private void fillInput(Page page, Locator input, String answer){
        String tag = input.evaluate("el => el.tagName").toString().toLowerCase();

        switch (tag){
            case "input"->{
                String type = safe(input.getAttribute("type")).toLowerCase();
                if(type.equals("checkbox") || type.equals("radio")){

                    page.locator("input[value=\""+answer+"\"]").first().click();
                    return;
                }
                input.fill(answer);
            }
            case "textarea" -> input.fill(answer);
            case "select" -> input.selectOption(answer);
        }
    }

    private void clickSubmit(Page page){
        Locator submit = page.locator("button[type=submit], button:not([disabled])");

        if(submit.count()>0) submit.first().click();
    }

    private String takeScreenShot(Page page, String suffix) throws IOException {
        Path dir = Path.of("screenshot");
        if(!Files.exists(dir)) Files.createDirectories(dir);

        Path path = dir.resolve("greenhouse_"+ suffix+".png");
        page.screenshot(new Page.ScreenshotOptions().setPath(path));

        return path.toAbsolutePath().toString();
    }

}
