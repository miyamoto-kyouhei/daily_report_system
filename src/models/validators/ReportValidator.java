package models.validators;

import java.util.ArrayList;
import java.util.List;

import models.Report;
import models.SalesCalls;

public class ReportValidator {
    public static List<String> validate(Report r) {
        List<String> errors = new ArrayList<String>();

        String title_error = _validateTitle(r.getTitle());
        if (!title_error.equals("")) {
            errors.add(title_error);
        }

        String content_error = _validateContent(r.getContent());
        if (!content_error.equals("")) {
            errors.add(content_error);
        }
        if (r.getSalesCallsList() != null) {
            List<String> salesCalls_errors = _validateSalesCalls(r.getSalesCallsList());
            if (salesCalls_errors.size() > 0) {
                errors.addAll(salesCalls_errors);
            }
        }

        return errors;
    }

    private static String _validateTitle(String title) {
        if (title == null || title.equals("")) {
            return "タイトルを入力してください。";
        }

        return "";
    }

    private static String _validateContent(String content) {
        if (content == null || content.equals("")) {
            return "内容を入力してください。";
        }

        return "";
    }

    private static List<String> _validateSalesCalls(List<SalesCalls> salesCallsList) {
        List<String> salesCalls_errors = new ArrayList<String>();
        for (SalesCalls salesCalls : salesCallsList) {
            if (salesCalls.getClient() != null) {
                if (salesCalls.getVisit_time() == null) {
                    salesCalls_errors.add("訪問時間を入力してください。");
                }
                if (salesCalls.getSales_report() == null || "".equals(salesCalls.getSales_report())) {
                    salesCalls_errors.add("商談内容を入力してください。");
                }
            }
        }
        return salesCalls_errors;
    }
}