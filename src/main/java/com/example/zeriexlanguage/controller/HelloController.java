package com.example.zeriexlanguage.controller;

import com.example.zeriexlanguage.model.Flashcard;
import com.example.zeriexlanguage.repository.FlashcardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // 魔法標籤 1：告訴 Spring 這是處理 API 的類別
public class HelloController {

//    @GetMapping("/hello") // 魔法標籤 2：當使用者造訪 /hello 網址時執行
//    public Map<String, String> sayHello(@RequestParam(defaultValue = "Zeri") String name) {
//        Map<String, String> response = new HashMap<>();
//        response.put("message", "Welcome back, " + name + "!");
//        response.put("status", "Learning Java in 2026");
//        return response; // Spring 會自動把 Map 轉成 JSON 格式
//    }

    @Autowired
    private FlashcardRepository repository;

    @GetMapping("/test-add")
    public Flashcard addTestCard() {
        Flashcard card = new Flashcard();
        card.setWord("こんにちは");
        card.setTranslation("你好");
        card.setLanguage("JP");

        return repository.save(card);
    }

}