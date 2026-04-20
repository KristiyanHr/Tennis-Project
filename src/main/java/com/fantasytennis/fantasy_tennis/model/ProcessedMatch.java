package com.fantasytennis.fantasy_tennis.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProcessedMatch {
    
    @Id
    private Long Id;

    private LocalDateTime processedAt;
}
