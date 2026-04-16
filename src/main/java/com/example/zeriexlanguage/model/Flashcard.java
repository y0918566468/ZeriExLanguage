package com.example.zeriexlanguage.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity // 告訴 JPA：這是一個資料庫表格
@Data   // Lombok 魔法：自動幫你寫 Getter, Setter, toString
@NoArgsConstructor // 自動生成無參數建構子
public class Flashcard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 自動跳號的 ID
    private Long id;

    @Column(unique = true, nullable = false)
    private String word;        // 單字

    private String translation; // 翻譯
    private String language;    // 語言 (JP, KR, IT)
    private int level;          // 難度等級 (預設 1)
}