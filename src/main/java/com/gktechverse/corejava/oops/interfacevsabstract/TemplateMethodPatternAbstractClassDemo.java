package com.gktechverse.corejava.oops.interfacevsabstract;

import java.time.LocalDate;
import java.util.List;

/**
 * Interview focus: template method pattern.
 * Shows why abstract classes still matter for shared workflow + customizable steps.
 */
public class TemplateMethodPatternAbstractClassDemo {

    static class DateRange {
        private final LocalDate from;
        private final LocalDate to;

        DateRange(LocalDate from, LocalDate to) {
            this.from = from;
            this.to = to;
        }

        @Override
        public String toString() {
            return from + " to " + to;
        }
    }

    static class Row {
        private final String label;
        private final Double value;

        Row(String label, Double value) {
            this.label = label;
            this.value = value;
        }

        public String getLabel() {
            return label;
        }

        public Double getValue() {
            return value;
        }

        @Override
        public String toString() {
            return label + "=" + value;
        }
    }

    static class Report {
        private final String content;
        private final DateRange range;

        Report(String content, DateRange range) {
            this.content = content;
            this.range = range;
        }

        @Override
        public String toString() {
            return "Report{range=" + range + ", content='" + content + "'}";
        }
    }

    abstract static class ReportGenerator {

        public final Report generate(DateRange range) {
            List<Row> data = fetchData(range);
            List<Row> cleaned = cleanData(data);
            String content = formatReport(cleaned);
            return new Report(content, range);
        }

        protected abstract List<Row> fetchData(DateRange range);

        protected abstract String formatReport(List<Row> data);

        protected List<Row> cleanData(List<Row> data) {
            return data.stream()
                    .filter(row -> row.getValue() != null)
                    .toList();
        }
    }

    static class SalesReportGenerator extends ReportGenerator {

        @Override
        protected List<Row> fetchData(DateRange range) {
            return List.of(
                    new Row("NorthRegion", 120_000.0),
                    new Row("WestRegion", null),
                    new Row("SouthRegion", 98_500.0)
            );
        }

        @Override
        protected String formatReport(List<Row> data) {
            return "PDF(Sales Report): " + data;
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Template Method Pattern Demo ===");

        DateRange aprilRange = new DateRange(LocalDate.of(2026, 4, 1), LocalDate.of(2026, 4, 30));
        ReportGenerator generator = new SalesReportGenerator();

        Report report = generator.generate(aprilRange);
        System.out.println(report);
    }
}
