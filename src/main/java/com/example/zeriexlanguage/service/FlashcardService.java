package com.example.zeriexlanguage.service;

import com.example.zeriexlanguage.model.Flashcard;
import com.example.zeriexlanguage.repository.FlashcardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class FlashcardService {

    @Autowired
    private FlashcardRepository repository;

    public Optional<Flashcard> findById(Long id) {
        return repository.findById(id);
    }

    public Flashcard save(Flashcard card) {
        return repository.save(card);
    }

    // 把原本在 Controller 裡的複雜邏輯搬過來
    public Flashcard addCard(Map<String, String> request) {
        Flashcard card = new Flashcard();

        // 邏輯封裝在此
        String word = request.getOrDefault("word", "Unknown").trim();
        String translation = request.getOrDefault("translation", "未翻譯").trim();
        String lang = request.get("language");

        card.setWord(word);
        card.setTranslation(translation);
        card.setLanguage((lang == null || lang.isEmpty()) ? "EN" : lang.toUpperCase());
        card.setLevel(1);

        return repository.save(card);
    }
}