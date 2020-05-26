package edu.greenriver.it.booklendingspring.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.core.io.ClassPathResource;

import javax.persistence.*;

/**
 * @author Jason Engelbrecht
 * @version 1.0
 * Book entity
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String isbn;
    private String title;
    private String author;
    private int pages;

    @Lob
    private String synopsis;
    @Lob
    private Byte[] coverImage;

    /**
     * Get the image from the appropriate place
     * @return image location
     */
    public String getImage() {
        String location = "/images/book_covers/" + title + ".jpg";
        boolean imageExists = new ClassPathResource("/static" + location).isFile();
        if(imageExists) {
            return location;
        }
        return "/books/image/" + isbn;
    }
}
