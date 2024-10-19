package com.rev.aoc;

import org.apache.commons.cli.ParseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;

public class AocCoordinateTest
{

    @ParameterizedTest
    @MethodSource("getFailureCases")
    public void testFailureCases(String invalid) {
        Assertions.assertNull(AocCoordinate.parse(invalid));
    }

    @ParameterizedTest
    @MethodSource("getSuccessCases")
    public void testSuccessCases(AocCoordinateSuccessTestCase successTestCase) {
        AocCoordinate coord = AocCoordinate.parse(successTestCase.str);
        Assertions.assertEquals(successTestCase.year, coord.year);
        Assertions.assertEquals(successTestCase.day, coord.day);
    }

    public static List<String> getFailureCases() {
        return List.of(
                "2022:ab",
                "202:01",
                "2024:012",
                "cd:12",
                "20:a0"
        );
    }

    public static List<AocCoordinateSuccessTestCase> getSuccessCases() {
        return List.of(
                new AocCoordinateSuccessTestCase("2022:12", 2022, 12),
                new AocCoordinateSuccessTestCase("2022:3", 2022, 3),
                new AocCoordinateSuccessTestCase("2022:03", 2022, 3),
                new AocCoordinateSuccessTestCase("19:12", 2019, 12),
                new AocCoordinateSuccessTestCase("19:09", 2019, 9),
                new AocCoordinateSuccessTestCase("19:9", 2019, 9)
        );
    }

    private static class AocCoordinateSuccessTestCase {
        private final String str;
        private final int year;
        private final int day;

        private AocCoordinateSuccessTestCase(String str, int year, int day)
        {
            this.str = str;
            this.year = year;
            this.day = day;
        }
    }
}
