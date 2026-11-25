package tn.esprit.autoloc.web.dto;

import java.time.Instant;
import java.util.Map;

public record ApiErrorResponse(int status, String error, String message,
                               Instant timestamp, Map<String, String> fieldErrors) {
}
