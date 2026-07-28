package com.expensetracker.util;

import com.expensetracker.dto.response.PageResponse;
import org.springframework.data.domain.Page;

public class PaginationUtil {

    public static <T> PageResponse<T> toPageResponse(Page<T> page) {

        return PageResponse.<T>builder()
                .content(page.getContent())
                .page(page.getNumber())
                .size(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .first(page.isFirst())
                .last(page.isLast())
                .build();

    }

}
