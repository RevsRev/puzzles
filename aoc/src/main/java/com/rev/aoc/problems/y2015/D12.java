package com.rev.aoc.problems.y2015;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.rev.aoc.framework.AocProblemI;
import com.rev.aoc.framework.problem.ProblemExecutionException;

import java.util.Iterator;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.rev.aoc.framework.problem.ResourceLoader;

public final class D12 {

    private static final Pattern NUMBER_REGEX = Pattern.compile("-?\\d+");

    @AocProblemI(year = 2015, day = 12, part = 1)
    public Long partOneImpl(final ResourceLoader resourceLoader) {
        final String json = resourceLoader.resources().get(0);
        Matcher matcher = NUMBER_REGEX.matcher(json);
        long sum = 0;
        List<MatchResult> matchResults = matcher.results().toList();
        for (MatchResult m : matchResults) {
            sum += Long.parseLong(m.group());
        }
        return sum;
    }

    @AocProblemI(year = 2015, day = 12, part = 1)
    @SuppressWarnings("checkstyle:MagicNumber")
    public Long partTwoImpl(final ResourceLoader resourceLoader) {
        final String json = resourceLoader.resources().get(0);
        JsonMapper jm = new JsonMapper();
        try {
            final JsonNode jsonNode = jm.readTree(json);
            return count(jsonNode);
        } catch (JsonProcessingException e) {
            throw new ProblemExecutionException(e);
        }
    }

    private long count(final JsonNode jsonNode) {
        if (jsonNode.isObject()) {
            Iterator<String> itFieldNames = jsonNode.fieldNames();
            while (itFieldNames.hasNext()) {
                String fieldName = itFieldNames.next();
                if ("red".equals(fieldName)) {
                    return 0;
                }
                JsonNode field = jsonNode.get(fieldName);
                if (field.isTextual() && "red".equals(field.textValue())) {
                    return 0;
                }
            }
        }

        if (jsonNode.isArray() || jsonNode.isObject()) {
            long sum = 0;
            for (JsonNode field : jsonNode) {
                sum += count(field);
            }
            return sum;
        }

        if (jsonNode.isInt()) {
            return jsonNode.intValue();
        }

        return 0;
    }

}
