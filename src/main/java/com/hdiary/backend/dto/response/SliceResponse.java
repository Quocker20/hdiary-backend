package com.hdiary.backend.dto.response;

import java.util.List;
import org.springframework.data.domain.Slice;

public record SliceResponse<T>(
    List<T> content,
    int number,
    int size,
    int numberOfElements,
    boolean first,
    boolean last,
    boolean hasNext,
    boolean hasPrevious
) {
    public static <T> SliceResponse<T> from(Slice<T> slice) {
        return new SliceResponse<>(
            slice.getContent(),
            slice.getNumber(),
            slice.getSize(),
            slice.getNumberOfElements(),
            slice.isFirst(),
            slice.isLast(),
            slice.hasNext(),
            slice.hasPrevious()
        );
    }
}
