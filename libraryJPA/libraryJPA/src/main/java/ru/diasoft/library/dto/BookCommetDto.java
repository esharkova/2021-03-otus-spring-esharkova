package ru.diasoft.library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookCommetDto {

    private long bookId;

    private String commentText;

    @Override
    public String toString() {
        return commentText;
    }
}
