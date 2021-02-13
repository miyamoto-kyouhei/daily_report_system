package models.validators;

import java.util.ArrayList;
import java.util.List;

import models.Client;

public class ClientValidator {
    public static List<String> validate(Client c) {
        List<String> errors = new ArrayList<String>();

        String name_error = validateName(c.getName());
        if(!name_error.equals("")) {
            errors.add(name_error);
        }


        return errors;
    }


    // 取引先名の必須入力チェック
    private static String validateName(String name) {
        if(name == null || name.equals("")) {
            return "取引先名を入力してください。";
        }

        return "";
    }
}
