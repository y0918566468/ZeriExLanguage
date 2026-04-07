package com.example.zeriexlanguage.controller;

import com.example.zeriexlanguage.model.Flashcard;
import com.example.zeriexlanguage.repository.FlashcardRepository;
import com.example.zeriexlanguage.service.FlashcardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("/api/cards")
public class FlashcardController {
    @Autowired
    private FlashcardRepository repository;

    @Autowired
    private FlashcardService flashcardService;

    //取得所有單字卡 : GET http://localhost:8000/api/cards
    @GetMapping
    public List<Flashcard> getAllCards()
    {
        return repository.findAll();
    }

    //新增單字卡 : POST http://localhost:8000/api/cards
    @PostMapping
    public Flashcard creatCard(@RequestBody Flashcard card)
    {
        return repository.save(card);
    }

    @GetMapping("/random")
    public Map<String, Object> getRandomCard()
    {
        // 1. 使用我們之前寫的高效能方式抓取單字
        // 假設你現在先用簡單的 findAll().get(0) 或是隨機邏輯
        List<Flashcard> cards = repository.findAll();

        Map<String, Object> response = new HashMap<>();

        if (cards.isEmpty())
        {
            response.put("status", "error");
            response.put("message", "No cards found");
            return response;
        }

        //隨機選一個
        Flashcard card = cards.get(new Random().nextInt(cards.size()));

        //2.核心邏輯：組合 Google TTS 連結
        // 格式: https://translate.google.com/translate_tts?ie=UTF-8&client=tw-ob&q=單字&tl=語言代碼
        String word = card.getWord();
        String encodeWord = URLEncoder.encode(word, StandardCharsets.UTF_8);
        String lang =  card.getLanguage().toLowerCase();

        String audioUrl = String.format(
                "https://translate.google.com/translate_tts?ie=UTF-8&client=tw-ob&q=%s&tl=%s",
                encodeWord, lang
        );

        // 3. 回傳封裝後的資料
        response.put("data", card);
        response.put("audioUrl", audioUrl);

        return response;
    }

    @PostMapping("/check")
    public Map<String, Object> checkAnswer(@RequestBody Map<String, String> request) {
        // 1. 從 Request 中取得 ID 和 使用者輸入的答案
        Long id = Long.parseLong(request.get("id"));
        String userAnswer = request.get("answer").trim();

        // 2. 去資料庫找這張卡
        Flashcard card = repository.findById(id).orElse(null);

        Map<String, Object> response = new HashMap<>();
        if (card == null) {
            response.put("result", "error");
            response.put("message", "Flashcard not found");
            return response;
        }

        // TODO: 比對 card.getTranslation() 跟 userAnswer 是否一致
        if (card.getTranslation().equals(userAnswer)) {
            // 1. 把 card 的 level 加 1
            // 2. 呼叫 repository.save(card) 把更新存回去
            // 3. 在 response 放入 "success"
            card.setLevel(card.getLevel() + 1);
            repository.save(card);
            response.put("result", "success");
            response.put("currentLevel", card.getLevel());
        } else {
            // 1. 在 response 放入 "wrong"
            // 2. 告訴使用者正確答案是什麼
            response.put("result", "wrong");
            response.put("message", card.getTranslation());
        }

        return response;
    }

    // 網址：GET http://localhost:8080/api/cards/search?word=こんにちは
    @GetMapping("/search")
    public List<Flashcard> searchCards(@RequestParam String word) {
        return repository.findByWordContaining(word);
    }

    // 在 FlashcardController.java 裡
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteCard(@PathVariable Long id) {
        // 你的邏輯寫在這裡
        Map<String, String> response = new HashMap<>();
        // 1. 檢查 id 是否存在
        if (!repository.existsById(id)) {
            response.put("result", "error");
            return ResponseEntity.ok(response);
        }
        // 2. 執行刪除
        repository.deleteById(id);
        response.put("result", "success");
        // 3. 回傳一個簡單的字串或 Map 告訴前端成功了
        return ResponseEntity.ok(response);
    }

    @PostMapping("/addCard")
    public Flashcard addCard(@RequestBody Map<String, String> request) {
        // 呼叫
        return flashcardService.addCard(request);
    }

}
