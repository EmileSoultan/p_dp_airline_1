package app.entities;

import app.enums.CategoryType;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "category")
@Data
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_category")
    @SequenceGenerator(name = "seq_category", initialValue = 1000, allocationSize = 1)
    Long id;

    @Column(name = "category_type")
    @Enumerated(EnumType.STRING)
    private CategoryType categoryType;
}
