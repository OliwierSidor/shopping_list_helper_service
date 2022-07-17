package pl.sda.arppl4.shopping_list_helper_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.sda.arppl4.shopping_list_helper_service.model.dto.CategoryDTO;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    public CategoryDTO mapToDTO() {
        return new CategoryDTO(id, name);
    }

    public Category(String name) {
        this.name = name;
    }
}
