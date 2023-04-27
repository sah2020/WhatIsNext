package me.akmaljon.WhatIsNextProject.service;

import com.google.gson.Gson;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;
import lombok.extern.slf4j.Slf4j;
import me.akmaljon.WhatIsNextProject.entity.RequestModel;
import me.akmaljon.WhatIsNextProject.entity.ResponseModel;
import me.akmaljon.WhatIsNextProject.entity.ShopTypeEnum;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class ApiServiceImpl implements ApiService {
    @Value("${openai.api-key}")
    private String apiKey;

    @Value("${openai.timeout}")
    private Long timeout;

    private final String SYSTEM_TASK_MESSAGE = """
            You are an API server that responds in a JSON format. Don't say anything else.
            Respond only with the JSON. No any other signs or marks before and after the JSON.
            The user will provide you with a shop type and a list of items. While considering those items,
            you must suggest a list of top 5 recommended and predicted products/items that the customer may be willing to purchase.
            Respond in a JSON format, including an array called 'items' and text field called 'comments'.
            If the shop type and items do not match each other, you should not respond with suggested items.
            For example, if the shop type is bakery and the items include clothes or any other products that do not belong to the specified shop type, you should make 'items' null and tell the message about the mismatch or error in the 'comments' field.
            Each item of the 'items' array is another JSON object that includes 'itemName' as a text and 'reasoning' as a text.
            The 'reasoning' field should include justification for your item. If you can't prove or justify your point, no need to include similar text on all of these fields. They can be left empty.
            If you want to add additional comments to your main response (list of items) you should include them in 'comments' field.
            If there is anything wrong with the request or an error occurs, make items array as null object and tell the error description in the 'comments' field.
            Don't add anything else after you respond with the JSON.""";

    @Override
    public ResponseModel getAnswer(RequestModel requestModel) {

        if (!StringUtils.hasText(requestModel.getShopType())) {
            throw new RuntimeException("ShopType is empty");
        }

        if (Arrays.stream(ShopTypeEnum.values())
                .map(ShopTypeEnum::toString)
                .noneMatch(type -> type.equals(requestModel.getShopType()))) {
            throw new RuntimeException("Shop type is not found");
        }

        OpenAiService openAiService = new OpenAiService(apiKey, Duration.ofSeconds(timeout));

        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest
                .builder()
                .model("gpt-3.5-turbo")
                .temperature(0.8)
                .messages(
                        List.of(
                                new ChatMessage("system", SYSTEM_TASK_MESSAGE),
                                new ChatMessage("user", String.format("Imagine customer in %s bought %s",
                                        requestModel.getShopType(), requestModel.getItems()))))
                .build();

        StringBuilder builder = new StringBuilder();

        openAiService.createChatCompletion(chatCompletionRequest)
                .getChoices().forEach(choice -> builder.append(choice.getMessage().getContent()));

        String jsonResponse = builder.toString();
        log.info(jsonResponse);

        Gson gson = new Gson();
        return gson.fromJson(jsonResponse, ResponseModel.class);
    }

    @Override
    public List<String> getShopTypesList() {
        return Arrays.stream(ShopTypeEnum.values()).map(ShopTypeEnum::toString).toList();
    }

}
