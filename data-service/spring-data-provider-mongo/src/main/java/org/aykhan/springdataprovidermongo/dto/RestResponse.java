package org.aykhan.springdataprovidermongo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RestResponse<T> {
    private T data;
    private String message;
}
