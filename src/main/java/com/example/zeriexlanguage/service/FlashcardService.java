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
    public Flashcard addCard(Flashcard card) {
        // 處理字串
        if (card.getWord() != null) card.setWord(card.getWord().trim());
        if (card.getTranslation() != null) card.setTranslation(card.getTranslation().trim());

        // 處理語言預設值與大小寫
        if (card.getLanguage() == null || card.getLanguage().isEmpty()) {
            card.setLanguage("EN");
        } else {
            card.setLanguage(card.getTranslation().toUpperCase());
        }

        card.setLevel(1);

        return repository.save(card);
    }

    public Map<String, Long> getStatistics() {
        long total = repository.count();
        long mastered = repository.countByLevelGreaterThanEqual(10);
        return Map.of(
                "total", total,
                "mastered", mastered,
                "learning", total - mastered
        );
    }
}