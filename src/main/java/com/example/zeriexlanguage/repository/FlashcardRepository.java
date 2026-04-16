package com.example.zeriexlanguage.repository;

import com.example.zeriexlanguage.model.Flashcard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// 繼承 JpaRepository 後，你就自動擁有 save(), findAll(), delete() 等功能了！
public interface FlashcardRepository extends JpaRepository<Flashcard, Long> {
    // JPA 魔法：只要方法名叫做 findBy...Containing，它會自動生成 LIKE %keyword% 的 SQL
    List<Flashcard> findByWordContaining(String keyword);

    // 統計總單字量
    long count();

    // 增加熟練度大於等於特定等級的單字
    long countByLevelGreaterThanEqual(int level);

    // 你也可以增加「依語言篩選」
    List<Flashcard> findByLanguage(String language);
}