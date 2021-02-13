package utils;

public class NewLine {
    /**
     * 改行コードを<br />タグに変換した情報を返却する。<br>
     * @param s 入力文字列
     * @return 変換後の文字列を返却します。
     */
    public static String nl2br(String s) {
        return nl2br(s, true);
    }

    /**
     * 改行コードを<br />、または、<br>タグに変換した情報を返却する。<br>
     * @param s 入力文字列
     * @param is_xhtml XHTML準拠の改行タグの使用する場合はtrueを指定します。
     * @return 変換後の文字列を返却します。
     */
    public static String nl2br(String s, boolean is_xhtml) {
        if (s == null || "".equals(s)) {
            return "";
        }
        String tag = is_xhtml ? "<br />" : "<br>";
        return s.replaceAll("\\r\\n|\\n\\r|\\n|\r", tag);
    }
}
