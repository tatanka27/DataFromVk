package com.example.datafromvk.model.response.validation;

import java.util.List;

public record ValidationErrorResponse(List<Violation> violations) {
}